package hr.fer.zemris.java.raytracer.model;

import java.util.OptionalDouble;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * This class models a sphere in a 3D environment to be used in
 * a scene for ray caster algorithm.
 * @author Matija
 *
 */
public class Sphere extends GraphicalObject {
	
	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr; 
	private double krg;
	private double krb; 
	private double krn;
	
	/**
	 * Creates and initialises a new sphere.
	 * @param center center of the sphere
	 * @param radius radius of the sphere
	 * @param kdr coefficient for diffuse component for red color
	 * @param kdg coefficient for diffuse component for green color
	 * @param kdb coefficient for diffuse component for blue color
	 * @param krr coefficient for reflective component for red color
	 * @param krg coefficient for reflective component for green color
	 * @param krb coefficient for reflective component for blue color
	 * @param krn coefficient n for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Calculates and returns closest intersection of the given ray with this sphere
	 * @return Returns RayIntersection object that describes the intersection
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D start = ray.start;
		Point3D direction = ray.direction;
		
		// Moving coordinate system so that sphere is in the origin
		Point3D centerToStart = start.sub(center);
		
		// Ray : rHat = p + dt, 
		// where p is starting point (vector from origin to the point), 
		// d is direction (vector length 1), t is scalar
		// ||rHat|| = r -> sphere equation
		// => ||p+dt|| = r 
		// => (p+dt)*(p+dt) = r^2, where * is dot product
		// => t^2*||d||^2 + 2t(p*d) + ||p||^2 - r^2 = 0
		
		double a = Math.pow(direction.norm(), 2);
		double b = 2 * centerToStart.scalarProduct(direction);
		double c = Math.pow(centerToStart.norm(), 2) - radius*radius;
		
		OptionalDouble solution = quadraticEqnSmallerSolution(a, b, c);
		
		if(solution.isEmpty()) return null;
		
		double distance = solution.getAsDouble();
		Point3D point = start.add(direction.scalarMultiply(distance));
		
		return new RayIntersection(point, distance, point.sub(start).norm() > radius) {
			
			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
		
	}
	
	// this method is for internal use only
	// returns the smaller solution to the a*x^2+b*x+c=0 equation
	private static OptionalDouble quadraticEqnSmallerSolution(double a, double b, double c) {		
		double discriminant = b*b - 4*a*c;
		
		if(discriminant<0) return OptionalDouble.empty();
		
		double s1 = (-b+Math.sqrt(discriminant))/(2*a);
		double s2 = (-b-Math.sqrt(discriminant))/(2*a);
		return s1<s2 ? OptionalDouble.of(s1) : OptionalDouble.of(s2);
		
	}
}
