package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * Singleton class which knows what to return as a service provider to access the data subsystem. 
 * Note that although the decision here is hardcoded, the class name that we generate could be 
 * dynamically read from the configuration file and dynamically loaded - so we could change the
 * implementations without any code compilation.
 * 
 * @author Matija
 *
 */
public class DAOProvider {

	private static DAO dao = new SQLDAO();
	
	/**
	 * Get a copy
	 * 
	 * @return an object that encapsulates access to a data persistence layer.
	 */
	public static DAO getDao() {
		return dao;
	}
	
}