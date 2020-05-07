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
			
			//create a SQL command
			ps = conn.prepareStatement(
					"INSERT INTO department "+
					"(Name) "+
					"VALUES "+
					"(?)");
			//Replace the '?' signs
			ps.setString(1, "New Department");
			//Write the new Values
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
