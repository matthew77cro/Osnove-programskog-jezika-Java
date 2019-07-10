package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This program is a showcase of the raycaster algorithm
 * @author Matija
 *
 */
public class RayCaster {

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Creates and returns a raytracer producer
	 * @return a raytracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
						double horizontal, double vertical, int width, int height,
						long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				long t1 = System.currentTimeMillis();
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D viewUpNormalized = viewUp.normalize();
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUpNormalized.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUpNormalized))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				
				Point3D screenCorner = view
						.sub(xAxis.scalarMultiply(horizontal/2))
						.add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
								.add(xAxis.scalarMultiply(horizontal*x/(width-1)))
								.sub(yAxis.scalarMultiply(vertical*y/(height-1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
				long t2 = System.currentTimeMillis();
				System.out.println("Time: " + (t2-t1));
			}
		};
	}
	
	/**
	 * Finds the closest intersection of the ray with an object in the scene and 
	 * calculates color of the pixel that the given ray goes through
	 * @param scene scene with object that needs to be displayed
	 * @param ray ray that goes from subject through the wanted pixel to the wanted object thet needs coloring
	 * @param rgb short array of rgb values for a pixel
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if(closest==null) {
			return;
		}
		
		determineColorFor(scene, ray, closest, rgb);
	}
	
	/**
	 * Calculates color of the pixel that the given ray goes through
	 * @param scene scene with object that needs to be displayed
	 * @param ray ray that goes from subject through the wanted pixel to the wanted object thet needs coloring
	 * @param closest closest intersection of given ray with some object in the scene
	 * @param rgb short array of rgb values for a pixel
	 */
	private static void determineColorFor(Scene scene, Ray ray, RayIntersection closest, short[] rgb) {
		List<LightSource> lightSources = scene.getLights();
		
		for(LightSource source : lightSources) {
			Ray lightBeam = Ray.fromPoints(source.getPoint(), closest.getPoint());
			RayIntersection intersection = findClosestIntersection(scene, lightBeam);
			if(intersection == null) continue;
			
			double distanceLightClosest = source.getPoint().sub(closest.getPoint()).norm();
			double distanceLightIntersection = source.getPoint().sub(intersection.getPoint()).norm();
			if(distanceLightIntersection + 1e-7 <distanceLightClosest) continue;
			
			reflect(source, intersection, rgb, ray);
			diffuse(source, intersection, rgb);
		}
	}
	
	/**
	 * Calculates reflect value of the color of the pixel that the given ray goes through
	 * @param scene scene with object that needs to be displayed
	 * @param ray ray that goes from subject through the wanted pixel to the wanted object thet needs coloring
	 * @param closest closest intersection of given ray with some object in the scene
	 * @param rgb short array of rgb values for a pixel
	 */
	private static void reflect(LightSource source, RayIntersection intersection, short[] rgb, Ray ray) {
		//reflect
		Point3D lightVector = source.getPoint().sub(intersection.getPoint()).normalize();
		Point3D reflectVector = intersection.getNormal().normalize()
				.scalarMultiply(2 * lightVector.scalarProduct(intersection.getNormal()) / intersection.getNormal().norm())
				.sub(lightVector)
				.normalize();
		double reflectValue = Math.pow(reflectVector.scalarProduct(ray.start.sub(intersection.getPoint()).normalize()), intersection.getKrn());
		if(reflectValue<0) return;
		
		if(reflectVector.scalarProduct(ray.start.sub(intersection.getPoint().normalize())) >= 0) {
			rgb[0] += (short) (reflectValue * source.getR() * intersection.getKrr());
			rgb[1] += (short) (reflectValue * source.getG() * intersection.getKrg());
			rgb[2] += (short) (reflectValue * source.getB() * intersection.getKrb());
		}
	}
	
	/**
	 * Calculates diffuse value of the color of the pixel that the given ray goes through
	 * @param scene scene with object that needs to be displayed
	 * @param ray ray that goes from subject through the wanted pixel to the wanted object thet needs coloring
	 * @param closest closest intersection of given ray with some object in the scene
	 * @param rgb short array of rgb values for a pixel
	 */
	private static void diffuse(LightSource source, RayIntersection intersection, short[] rgb) {
		//diffuse
		double diffValue = intersection.getNormal().scalarProduct(source.getPoint().sub(intersection.getPoint()).normalize());
		if(diffValue<0) return;
		
		rgb[0] += (short) (diffValue * source.getR() * intersection.getKdr());
		rgb[1] += (short) (diffValue * source.getG() * intersection.getKdg());
		rgb[2] += (short) (diffValue * source.getB() * intersection.getKdb());
	}
	
	/**
	 * Finds and returns the closest intersect between the given ray and an object
	 * in the scene.
	 * @param scene scene with object that way goes through
	 * @param ray ray for which to find the closest intersection
	 * @return closest intersection or null if there isn't any
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();
		
		double distance = Double.POSITIVE_INFINITY;
		RayIntersection closest = null;
		
		for(GraphicalObject obj : objects) {
			RayIntersection next = obj.findClosestRayIntersection(ray);
			if(next==null) continue;
			double dist = next.getDistance();
			if(dist<distance) {
				distance = dist;
				closest = next;
			}
		}
		
		return closest;
		
	}

}
