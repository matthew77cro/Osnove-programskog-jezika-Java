package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Stores the connection to the database in the ThreadLocal object. ThreadLocal is actually a map
 * whose keys are the thread identifier that operates on the map.
 * 
 * @author Matija
 *
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Setup a connection for the current thread (or delete value for the map if argument is <code>null</code>).
	 * 
	 * @param con connection to the database
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Gets a connection that the current thread may use.
	 * 
	 * @return connection to the database
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}