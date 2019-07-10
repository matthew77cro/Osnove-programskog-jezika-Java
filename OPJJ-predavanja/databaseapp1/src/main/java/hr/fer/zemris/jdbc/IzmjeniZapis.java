package hr.fer.zemris.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IzmjeniZapis {

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
			pst = con.prepareStatement("UPDATE Poruke set title=?, userEMail=? WHERE id=?");
			pst.setLong(3, 2); // Napravi promjenu u retku s ID=2
			pst.setString(1, "Modificirana bla bla bla x druga poruka");
			pst.setString(2, "Andela.Andelic@edu.edu");

			int numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
			System.out.println("Broj redaka koji su pogođeni ovom izmjenom: "+numberOfAffectedRows);
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