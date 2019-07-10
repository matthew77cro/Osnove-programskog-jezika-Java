package hr.fer.zemris.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class DodajZapis {

	public static void main(String[] args) {
		
		String dbName="baza1DB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName;

		// Alternativa uporabi parametara je koristiti URL koji ima na kraju ;user=perica;password=pero
		java.util.Properties dbProperties = new java.util.Properties();
		dbProperties.setProperty("user", "perica");
		dbProperties.setProperty("password", "pero");

		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionURL, dbProperties);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(
				"INSERT INTO Poruke (title, message, createdOn, userEMail) values (?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "Još jedna poruka");
			pst.setString(2, "Ovo je tekst te nove poruke.");
			pst.setTimestamp(3, new Timestamp(new Date().getTime()));
			pst.setString(4, "Jasminka.Jasnic@xyz.net");

			int numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
			System.out.println("Broj redaka koji su pogođeni ovim unosom: "+numberOfAffectedRows);
			
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					long noviID = rset.getLong(1);
					System.out.println("Unos je obavljen i podatci su pohranjeni pod ključem id="+noviID);
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