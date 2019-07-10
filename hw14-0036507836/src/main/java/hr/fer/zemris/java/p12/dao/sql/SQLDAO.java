package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
/**
 * This is the implementation of the DAO subsystem using SQL technology. 
 * This concrete implementation expects that link is available via the {@link SQLConnectionProvider} 
 * class, which means that someone, before execution gets to this point, would have to set it up there. 
 * In web applications, a typical solution is to configure a single filter that intercepts the 
 * servlet calls and before that insert a connection from the connection-pool here and after 
 * the processing finishes it is removed.
 *  
 * @author Matija
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() {
		List<Poll> toReturn = new ArrayList<DAO.Poll>();
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM Polls ORDER BY id");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String message = rs.getString(3);
				toReturn.add(new Poll(id, title, message));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return toReturn;
	}
	
	@Override
	public Poll getPoll(int pollID) {
		Poll toReturn = null;
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM Polls WHERE id=" + pollID);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String message = rs.getString(3);
				toReturn = new Poll(id, title, message);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return toReturn;
	}

	@Override
	public List<PollOption> getPollOptions(int pollID) {
		List<PollOption> toReturn = new ArrayList<DAO.PollOption>();
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PollOptions WHERE pollID= " + pollID + " ORDER BY id");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String link = rs.getString(3);
				int votesCount = rs.getInt(5);
				toReturn.add(new PollOption(id, title, link, votesCount));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return toReturn;
	}

	@Override
	public void voteFor(int optionId) {
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement("UPDATE PollOptions SET votesCount ="
					+ "(SELECT votesCount FROM PollOptions WHERE id=" + optionId + ")+1 WHERE id=" + optionId);
			int i = pst.executeUpdate();
			
			if(i!=1) {
				throw new RuntimeException("Invalid id");
			}
			
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
	}

	@Override
	public int getPollIdFromOptionId(int optionId) {
		int pollId = -1;
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement("SELECT pollID FROM PollOptions WHERE id= " + optionId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				pollId = rs.getInt(1);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		if(pollId==-1) {
			throw new RuntimeException("No such option");
		}
		
		return pollId;
	}

}