package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class used for initialising a connection pool to the apache derby database server. Also,
 * on server shutdown, the connection pool is closed by this class.
 * @author Matija
 *
 */
@WebListener
public class Initialisation implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties prop = new Properties();
		
		Path propFilePath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"));
		try (InputStream in = Files.newInputStream(propFilePath)) {
			prop.load(in);
		} catch (IOException ex) {
			throw new RuntimeException("error loading properties file! : " + ex.getMessage());
		}
		
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String dbName = prop.getProperty("name");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + 
								";user=" + user + ";password=" + password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		//CHECKING IF TABLES EXIST AND CREATING THEM IF THEY DO NOT
		try {
			checkTables(cpds);
		} catch (SQLException e) {
			throw new RuntimeException(e.getCause() + " : " + e.getMessage());
		}
	}

	/**
	 * This method checks if required tables (relations) exist. Creates them if
	 * they do not exist.
	 * @param cpds connection pool to the database
	 * @throws SQLException if sql exception occurs
	 */
	private void checkTables(ComboPooledDataSource cpds) throws SQLException {
		
		boolean pollsIsEmpty = false;
		
		Connection con = null;
		con = cpds.getConnection();
			
		String createPolls = "CREATE TABLE Polls" + 
				" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
				" title VARCHAR(150) NOT NULL," + 
				" message CLOB(2048) NOT NULL" + 
				")";
		String createPollOptions = "CREATE TABLE PollOptions" + 
				" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
				" optionTitle VARCHAR(100) NOT NULL," + 
				" optionLink VARCHAR(150) NOT NULL," + 
				" pollID BIGINT," + 
				" votesCount BIGINT," + 
				" FOREIGN KEY (pollID) REFERENCES Polls(id)" + 
				")";
		
		PreparedStatement pst = null;

		pst = con.prepareStatement(createPolls);
		try {
			pst.execute();
			pollsIsEmpty = true;
		} catch (Exception ex) {
			PreparedStatement prep = con.prepareStatement("SELECT * FROM Polls");
			ResultSet rs = prep.executeQuery();
			if(!rs.next()) pollsIsEmpty = true;
			rs.close();
			prep.close();
		}
		pst.close();
		
		pst = con.prepareStatement(createPollOptions);
		try {
			pst.execute();
			pollsIsEmpty = true;
		} catch (Exception ex) {
			//if table PollOptions exists, this block is executed
		}
		pst.close();

		con.close();
		
		if(pollsIsEmpty) populateDatabase(cpds);
	}
	
	/**
	 * This method populates the database tables with default initial data.
	 * @param cpds connection pool to the database
	 * @throws SQLException if sql exception occurs
	 */
	private void populateDatabase(ComboPooledDataSource cpds) throws SQLException {
		Connection con = cpds.getConnection();
		
		String populatePollsMusic = "INSERT INTO Polls (title, message)" + 
							   "VALUES ('Glasanje za omiljeni bend'," + 
							   "'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!')";
		
		String populatePollsProgramming = "INSERT INTO Polls (title, message)" + 
				   "VALUES ('Glasanje za omiljeni programski jezik'," + 
				   "'Od sljedećih programskih jezika, koji Vam je najdraži? Kliknite na link kako biste glasali!')";
		
		int musicPollID = 0, programmingPollID = 0;
		
		//POPULATING MUSIC
		PreparedStatement pst = con.prepareStatement(populatePollsMusic, PreparedStatement.RETURN_GENERATED_KEYS);
		pst.executeUpdate();
		ResultSet rs = pst.getGeneratedKeys();
		if(rs.next()) {
			musicPollID = rs.getInt(1);
		}
		rs.close();
		pst.close();
		
		//POPULATING PROGRAMMING
		pst = con.prepareStatement(populatePollsProgramming, PreparedStatement.RETURN_GENERATED_KEYS);
		pst.executeUpdate();
		rs = pst.getGeneratedKeys();
		if(rs.next()) {
			programmingPollID = rs.getInt(1);
		}
		rs.close();
		pst.close();
		
		String populatePollOptions = "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount)" + 
									 "VALUES ('The Beatles', 'https://www.youtube.com/watch?v=jo505ZyaCbA', " + musicPollID + ", 0)," + 
									 "('Lady Gaga', 'https://www.youtube.com/watch?v=bo_efYhYU2A', " + musicPollID + ", 0)," + 
									 "('Sia', 'https://www.youtube.com/watch?v=2vjPBrBU-TM', " + musicPollID + ", 0)," + 
									 "('Miley Cyrus', 'https://www.youtube.com/watch?v=8j9zMok6two', " + musicPollID + ", 0)," + 
									 "('Kodaline', 'https://www.youtube.com/watch?v=i9wBXC3aZ_I', " + musicPollID + ", 0)," + 
									 "('Two Steps From Hell', 'https://www.youtube.com/watch?v=ZuGiwDQEvDc', " + musicPollID + ", 0)," + 
									 "('Grace VanderWaal', 'https://www.youtube.com/watch?v=6NwR8OfAnys', " + musicPollID + ", 0)," + 
									 "('Marshmello', 'https://www.youtube.com/watch?v=m7Bc3pLyij0', " + musicPollID + ", 0)," + 
									 "('C#', 'https://docs.microsoft.com/en-us/dotnet/csharp/', " + programmingPollID + ", 0)," + 
									 "('C++', 'https://en.wikipedia.org/wiki/C%2B%2B', " + programmingPollID + ", 0)," + 
									 "('C', 'https://en.wikipedia.org/wiki/C_(programming_language)', " + programmingPollID + ", 0)," + 
									 "('JavaScript', 'https://www.w3schools.com/js/', " + programmingPollID + ", 0)," + 
									 "('Python', 'https://www.python.org/', " + programmingPollID + ", 0)," + 
									 "('Java', 'https://www.java.com/en/', " + programmingPollID + ", 0)";
		
		//POPULATING OPTIONS
		pst = con.prepareStatement(populatePollOptions);
		pst.executeUpdate();
		pst.close();
		
		con.close();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}