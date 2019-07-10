package hr.fer.zemris.java.hw17.trazilica.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a mathematical model of a vector. Vector components
 * are of type double.
 * @author Matija
 *
 */
public class Vector{
	
	/**
	 * Dimension of the vector
	 */
	private int n;
	
	/**
	 * Vector components represented as an array in memory
	 */
	private double[] vector;
	
	/**
	 * Creates and initializes a new vector with components given as an array of doubles.
	 * @param components components of the vector
	 * @throws NullPointerException if given components is <code>null</code>
	 */
	public Vector(double[] components) {
		if(components==null) throw new NullPointerException("Components of the vector cannot be null!");
		this.n = components.length;
		this.vector = components;
	}
	
	/**
	 * Returns the component of the vector on index k.
	 * @param k index of the component to change
	 * @return component of the vector at the index k
	 * @throws ArrayIndexOutOfBoundsException if : (k<0 || k>=n), where n is dimension of the vector
	 */
	public double getComponent(int k) {
		if(k<0 || k>=n) throw new ArrayIndexOutOfBoundsException("Index of component (k) must be within boundaries!");
		return vector[k];
	}
	
	/**
	 * Returns the dimension of the vector
	 * @return the dimension of the vector
	 */
	public int getVectorDimension() {
		return n;
	}
	
	/**
	 * Returns vector components as an array of type Object.
	 * @return vector components
	 */
	public double[] toArray() {
		return Arrays.copyOf(vector, vector.length);
	}
	
	/**
	 * Returns the Euclidean norm (Euclidean length) of the vector.
	 * @return the Euclidean norm of the vector
	 */
	public double norm() {
		double sum = 0;
		for(double d : vector) {
			sum += d*d;
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * Returns the scalar product of this vector with the given other vector.
	 * This vector does not get changed.
	 * @param other vector with which to scalar multiply this vector
	 * @return the scalar product of this vector with the given other vector.
	 * @throws NullPointerException if other is <code>null</code>
	 * @throws IllegalArgumentException if other vector is not the same dimensions as this vector
	 */
	public double scalarProduct(Vector other) {
		if(other==null) throw new NullPointerException("Given vector must not be null!");
		if(other.n!=this.n) throw new IllegalArgumentException("Vectors must be same dimensions!");
	
		double dot = 0;
		for(int i=0; i<this.n; i++) {
			dot += this.vector[i] * other.vector[i];
		}
		return dot;
	}
	
	/**
	 * Returns a cosine value of the angle between this vector and
	 * the vector given as other.
	 * @param other vector for which to calculate angle
	 * @return cosine value of the angle between this vector and other
	 * @throws NullPointerException if other is <code>null</code>
	 * @throws IllegalArgumentException if other vector is not the same dimensions as this vector
	 */
	public double cosAngle(Vector other) {
		if(other==null) throw new NullPointerException("Given vector must not be null!");
		if(other.n!=this.n) throw new IllegalArgumentException("Vectors must be same dimensions!");
		return scalarProduct(other)/(this.norm()*other.norm());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		boolean first = true;
		for(double d : vector) {
			if(first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(d);
		}
		
		sb.append(")");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(vector);
		result = prime * result + Objects.hash(n);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return n == other.n && Arrays.equals(vector, other.vector);
	}

}
