package in.abc.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.abc.jdbcUtil.JdbcUtil;

public class ClobInsertOperation {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			//Getting the database connection using utility code
			connection = JdbcUtil.getJdbcConnection();

			String sqlInsertQuery = "insert into cities(name,history)values(?,?)";
			if (connection!=null) 				
				pstmt = connection.prepareStatement(sqlInsertQuery);
			if(pstmt!=null) {
				
				//Setting the first index to String
				pstmt.setString(1, "kolkata");
				
				//image file is reaching to java application
				File f = new File("kolkata_history.txt");
				FileReader reader = new FileReader(f);
				
				// setting the input information from java and sending the data to database
				pstmt.setCharacterStream(2, reader);
				
				System.out.println("File is Inserting from ::" + f.getAbsolutePath());
				
				//Executing the Query to get the result
				int noOfRows = pstmt.executeUpdate();
				if (noOfRows == 1) {
					System.out.println("record inserted succesfully...");
				} else {
					System.out.println("no records inserted...");
				}
			}


		} catch (SQLException e) {
			//handling logic of exception related to SQLException
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			//handling logic of exception related to FileOperation
			e.printStackTrace();
		} catch (Exception e) {
			//handling logic of exception related to common problem
			e.printStackTrace();
		} finally {
			//closing the resource
			try {
				JdbcUtil.closeConnection(null, pstmt, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
