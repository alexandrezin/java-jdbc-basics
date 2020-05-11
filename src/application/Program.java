package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {
		//Used to open connection, read and write
		Connection conn = null;
		//Used to get the values
		Statement st = null;
		ResultSet rs = null;
		//Used to set new values
		PreparedStatement ps = null;
		
		try {
			//Open database
			conn = DB.openConnection();
			
			//Set auto commit to false, in order to do all the operations
			conn.setAutoCommit(false);
			
			//create a SQL command to add new data into Db
			ps = conn.prepareStatement(
					"INSERT INTO department "+
					"(Name) "+
					"VALUES "+
					"(?)");
			//Replace the '?' signs
			ps.setString(1, "New Department");
			//Execute command
			ps.executeUpdate();
			//It's necessary closing it before creating a new prepared statement
			DB.closeStatement(ps);

			//Create a SQL command to update an existing data
			ps = conn.prepareStatement(
					"UPDATE department "+
					"SET Name = ? "+
					"WHERE "+
					"(Id = ?)");
			//Replace the '?' signs
			ps.setString(1, "Changed Department");
			ps.setInt(2, 5);
			//execute command
			ps.executeUpdate();
			//Close Statement
			DB.closeStatement(ps);
			
			conn.commit();
			
			//Create a SQL Command to Delete a row
			ps = conn.prepareStatement(
					"DELETE FROM department "+
					"WHERE "+
					"ID = ?");
			//Replace the '?' signs
			ps.setInt(1, 6);
			//Execute command
			ps.executeUpdate();
			
			//Create statement to read
			st = conn.createStatement();
			//Create SQL Command
			rs = st.executeQuery("select * from department");
			//Select the next line from database
			while(rs.next()) {
				//get the values from specific Columns
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
			}

		}
		catch (SQLException exception) {
			try {
				//rollback the things between commit();
				conn.rollback();
			} catch (SQLException e2) {
				System.out.println(e2.getMessage());
			}
			System.out.println(exception.getMessage());
		}
		//Close all Statements
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeStatement(ps);
			DB.closeConnection();
		}
	}

}
