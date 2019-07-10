package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This is a util class which implements all of the functions supported by the SmartScript language
 * @author Matija
 *
 */
public class SmartScriptFunctions {
	
	private static Map<String, Consumer<Stack<Object>>> functions;
	private static Map<String, BiConsumer<Stack<Object>, RequestContext>> biFunctions;

	public static Consumer<Stack<Object>> sin;
	public static Consumer<Stack<Object>> decfmt;
	public static Consumer<Stack<Object>> dup;
	public static Consumer<Stack<Object>> swap;
	public static BiConsumer<Stack<Object>, RequestContext> setMimeType;
	public static BiConsumer<Stack<Object>, RequestContext> paramGet;
	public static BiConsumer<Stack<Object>, RequestContext> pparamGet;
	public static BiConsumer<Stack<Object>, RequestContext> pparamSet;
	public static BiConsumer<Stack<Object>, RequestContext> pparamDel;
	public static BiConsumer<Stack<Object>, RequestContext> tparamGet;
	public static BiConsumer<Stack<Object>, RequestContext> tparamSet;
	public static BiConsumer<Stack<Object>, RequestContext> tparamDel;

	static {
		
		functions = new HashMap<>();
		biFunctions = new HashMap<>();
		
		sin = (stack) -> {
			double value = toDouble(stack.pop());
			value = Math.PI * value/180;
			stack.push(Math.sin(value));
		};
		decfmt = (stack) -> {
			String pattern = (String)stack.pop();
			double value = toDouble(stack.pop());
			DecimalFormat df = new DecimalFormat(pattern);
			String format = df.format(value);
			stack.push(format);
		};
		dup = (stack) -> {
			Object top = stack.pop();
			stack.push(top);
			stack.push(top);
		};
		swap = (stack) -> {
			Object top1 = stack.pop();
			Object top2 = stack.pop();
			stack.push(top1);
			stack.push(top2);
		};
		setMimeType = (stack, req) -> {
			req.setMimeType(stack.pop().toString());
		};
		paramGet = (stack, req) -> {
			Object defValue = stack.pop();
			Object name = stack.pop();
			Object value = req.getParameter(name.toString());
			stack.push(value==null ? defValue : value);
		};
		pparamGet = (stack, req) -> {
			Object defValue = stack.pop();
			Object name = stack.pop();
			Object value = req.getPersistentParameter(name.toString());
			stack.push(value==null ? defValue : value);
		};
		pparamSet = (stack, req) -> {
			Object name = stack.pop();
			Object value = stack.pop();
			req.setPersistentParameter(name.toString(), value.toString());
		};
		pparamDel = (stack, req) -> {
			Object name = stack.pop();
			req.removePersistentParameter(name.toString());
		};
		tparamGet = (stack, req) -> {
			Object defValue = stack.pop();
			Object name = stack.pop();
			Object value = req.getTemporaryParameter(name.toString());
			stack.push(value==null ? defValue : value);
		};
		tparamSet = (stack, req) -> {
			Object name = stack.pop();
			Object value = stack.pop();
			req.setTemporaryParameter(name.toString(), value.toString());
		};
		tparamDel = (stack, req) -> {
			Object name = stack.pop();
			req.removeTemporaryParameter(name.toString());
		};
		
		functions.put("sin", sin);
		functions.put("decfmt", decfmt);
		functions.put("dup", dup);
		functions.put("swap", swap);
		
		biFunctions.put("setMimeType", setMimeType);
		biFunctions.put("paramGet", paramGet);
		biFunctions.put("pparamGet", pparamGet);
		biFunctions.put("pparamSet", pparamSet);
		biFunctions.put("pparamDel", pparamDel);
		biFunctions.put("tparamGet", tparamGet);
		biFunctions.put("tparamSet", tparamSet);
		biFunctions.put("tparamDel", tparamDel);

	}
	
	/**
	 * Converts object to double if it is instance of Double, Integer or String which is parsable to double
	 * @param value
	 * @return
	 * @throws NullPointerException if value is null
	 * @throws NumberFormatException if value is NaN
	 */
	private static double toDouble(Object value) {
		if(value instanceof Double) {
			return ((Double)value).doubleValue();
		} else if(value instanceof Integer) {
			return ((Integer)value).doubleValue();
		} else if(value instanceof String) {
			return Double.parseDouble((String)value);
		} else {
			throw new NumberFormatException(value + " is NaN!");
		}
	}

	/**
	 * This function executes function whose name is functionName on the given stack and request
	 * @param functionName name of the function to be executed
	 * @param stack stack on which to be executed
	 * @param req request context on which to be executed
	 */
	public static void executeFunction(String functionName, Stack<Object> stack, RequestContext req) {

		var fun = functions.get(functionName);
		if(fun!=null) {
			fun.accept(stack);
			return;
		}
		
		var biFun = biFunctions.get(functionName);
		if(biFun==null) throw new IllegalArgumentException("No such function!");
		biFun.accept(stack, req);

	}

}
