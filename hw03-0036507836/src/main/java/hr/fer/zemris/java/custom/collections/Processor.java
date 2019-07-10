package hr.fer.zemris.java.custom.collections;

@FunctionalInterface
/**
 * 
 * The Processor is a model of an object capable of performing some operation on the passed object.
 * Class Processor here represents an conceptual contract between clients which will have objects to be processed, 
 * and each concrete Processor which knows how to perform the selected operation. Each concrete Processor must be 
 * defined as a new class which inherits from the class Processor. 
 * 
 * @author Matija
 *
 */
public interface Processor {
	
	/**
	 * Performs this operation on the given argument.
	 * @param value the input argument
	 */
	void process(Object value);
	
}
