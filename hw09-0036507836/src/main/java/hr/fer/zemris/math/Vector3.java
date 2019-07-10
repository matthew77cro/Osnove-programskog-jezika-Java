package hr.fer.zemris.math;

/**
 * This class models a vector in a 3D space
 * @author Matija
 *
 */
public class Vector3 {
	
	private final double x;
	private final double y;
	private final double z;

	/**
	 * Creates a vector with x y and z components
	 * @param x x component of the vector
	 * @param y y component of the vector
	 * @param z z component of the vector
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the norm of the vector
	 * @return  the norm of the vector
	 */
	public double norm() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	/**
	 * Returns normalised vector so that its norm is 1
	 * @return new vector that has the same direction but norm of 1
	 */
	public Vector3 normalized() {
		double length = norm();
		return new Vector3(this.x/length, this.y/length, this.z/length);
	}
	
	/**
	 * Returns new vector that is the result of this vector plus other vector
	 * @param other vector to be added to this one
	 * @return new vector that is the result of this vector plus other vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x+other.x, this.y+other.y, this.z+other.z);
	}
	
	/**
	 * Returns new vector that is the result of this vector minus other vector
	 * @param other vector to be subtracted to this one
	 * @return new vector that is the result of this vector minus other vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x-other.x, this.y-other.y, this.z-other.z);
	}
	
	/**
	 * Returns new vector that is the result of dot product between two vectors (this vector and other)
	 * @param other multiplier
	 * @return new vector that is the result of dot product between two vectors (this vector and other)
	 */
	public double dot(Vector3 other) {
		return this.x*other.x + this.y*other.y + this.z*other.z;
	}
	
	/**
	 * Returns new vector that is the result of cross product between two vectors (this vector and other)
	 * @param other multiplier
	 * @return new vector that is the result of cross product between two vectors (this vector and other)
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(this.y*other.z-this.z*other.y, 
						   this.x*other.z-this.z*other.x, 
						   this.x*other.y-this.y*other.x);
	}
	
	/**
	 * Scales the norm of the vector
	 * @param s scalar with which to sale the norm
	 * @return new vector that has the same direction as this one but norm scaled by s
	 */
	public Vector3 scale(double s) {
		return new Vector3(this.x*s, this.y*s, this.z*s);
	}
	
	/**
	 * Returns cosine of the angle between this vector and other
	 * @param other other vector
	 * @return cosine of the angle between this vector and other
	 */
	public double cosAngle(Vector3 other) {
		return dot(other)/(this.norm()*other.norm());
	}
	
	/**
	 * Returns the x component of the vector
	 * @return the x component of the vector
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the y component of the vector
	 * @return the y component of the vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the z component of the vector
	 * @return the z component of the vector
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns components of the vector in a double array with length 3
	 * ( double[] {xcomponent, ycomponent, zcomponent} ).
	 * @return components of the vector as a double array
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

}
