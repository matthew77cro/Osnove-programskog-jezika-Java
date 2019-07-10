package hr.fer.zemris.jdbc;

import java.beans.PropertyVetoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class Listaj2 {

	public static void main(String[] args) {
		
		String dbName="baza1DB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName;

		// Priprema pool-a
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser("perica");
		cpds.setPassword("pero");
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		
		// Obavljanje jedne ili niza operacija nad bazom preko pool-a
		doOperation(cpds);
		
		// Unistavanje pool-a
		try {
			DataSources.destroy(cpds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void doOperation(DataSource dataSource) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, title, message, createdOn, userEMail from Poruke order by id");
			ResultSet rset = pst.executeQuery();
			try {
				while(rset.next()) {
					long id = rset.getLong(1); // ili rset.getLong("id"); 
					String title = rset.getString(2); // ili rset.getString("title");
					String message = rset.getString(3); // ili rset.getString("message");
					Date createdOn = rset.getTimestamp(4); // ili rset.getTimestamp("createdOn");
					String userEMail = rset.getString(5); // ili rset.getString("userEMail");
					System.out.println("Zapis "+id);
					System.out.println("===================================");
					System.out.println("Naziv: "+title);
					System.out.println("Poruka: "+message);
					System.out.println("Stvoreno: "+createdOn);
					System.out.println("EMail: "+userEMail);
					System.out.println("");
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		try { con.close(); } catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}