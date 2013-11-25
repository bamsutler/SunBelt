/**
 * DataMan is the class used to build a DataManager object for the SunBelt Program. It manages connections and query to the database.
 * 
 * 
 * @author Samuel Butler
 * 
 */

package sunBelt;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DataMan {

	Connection con = null;
	String dbName = "jdbc:mysql://cs.ulm.edu/butlert3";
	String user;
	String password;

	/**
	 * Main Constructor for the Class. Used in combination with a configuration
	 * file to access the database.
	 * 
	 * @param fileName
	 *            An Input stream from a configuration file containing the
	 *            username and password for the database. In the format Blank
	 *            Line \n Username \n BlankLine \n Password
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public DataMan(String fileName) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			IOException {
		// get username and password
		// Tried BufferReader First.
		InputStream input = getClass().getResourceAsStream(fileName);
		// File file = new File(fileName);
		// System.err.println(file);
		Scanner scan = new Scanner(input);
		scan.next();
		user = scan.next();
		scan.next();
		password = scan.next();
		scan.close();

		// this.user=user;
		// this.password=pass;

		connectData();

	}

	/**
	 * 
	 * Alterante Constructer used for the database in case a configuration file
	 * will not work.
	 * 
	 * @param user
	 *            A String That is the username for the database.
	 * @param pass
	 *            A String that is the password for the database.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public DataMan(String user, String pass) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			IOException {
		// get username and password
		this.user = user;
		this.password = pass;

		connectData();
	}

	/**
	 * 
	 * Method to close connection to the database.
	 * 
	 * @throws SQLException
	 */
	public void closeData() throws SQLException {
		con.close();
	}

	/**
	 * 
	 * Method to establish connection to the database.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connectData() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Define database drive
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// form connection: (type of connection, database type, server,
		// database), user and password
		con = DriverManager.getConnection(dbName, user, password);
	}

	/**
	 * 
	 * Method to Delete data from the database.
	 * 
	 * @param id
	 *            The Integer ID number of the game to be removed from the
	 *            database.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void delete(Integer id) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Statement stmt;
		String query;
		if (con.isClosed()) {
			connectData();
		}
		stmt = con.createStatement();
		query = "DELETE FROM GAME WHERE gid=" + id;
		stmt.executeUpdate(query);
	}

	/**
	 * 
	 * Method to Retrieve data from the database.
	 * 
	 * @param tablename
	 *            The Name of the Table within the database the has the data
	 *            being requested.
	 * @return A ResultSet of the data requested
	 * @throws SQLException
	 */
	public ResultSet getData(String tablename) throws SQLException {

		ResultSet result = null;
		Statement stmt;
		String query;

		if (!con.isClosed()) {
			stmt = con.createStatement();
			query = "SELECT * FROM " + tablename;
			result = stmt.executeQuery(query);
		}

		return result;
	}

	/**
	 * 
	 * Method to insert Game Data into the Database. The Data Should be
	 * Validated before sending to this method.
	 * 
	 * @param date
	 *            A String representation of the date of the game object.
	 * @param hTeam
	 *            A String representation of the Team ID for the Home Team.
	 * @param vTeam
	 *            A String representation of the Team ID for the Visiting Team.
	 * @param hScore
	 *            A String representation of the Score for the Home team.
	 * @param vScore
	 *            A String representation of the Score for the Visiting team.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public void insert(String date, String hTeam, String vTeam, String hScore,
			String vScore) throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Statement stmt;
		String query;

		if (con.isClosed()) {
			connectData();
		}
		stmt = con.createStatement();
		query = "INSERT INTO GAME (day, hteam, vteam, hscore, vscore) VALUES (\'"
				+ date
				+ "\',"
				+ hTeam
				+ ","
				+ vTeam
				+ ","
				+ hScore
				+ ","
				+ vScore + ")";
		stmt.executeUpdate(query);
	}

}
