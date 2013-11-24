
//TODO DOCUMENT THIS SHIT!!!!

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

	public DataMan(String user, String pass) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			IOException {
		// get username and password

		/**
		 * BufferedReader br = new BufferedReader(new FileReader(fileName));
		 * br.readLine(); user = br.readLine(); br.readLine(); password =
		 * br.readLine(); br.readLine(); dbName = br.readLine(); br.close();
		 */
		this.user = user;
		this.password = pass;

		connectData();
	}

	public void closeData() throws SQLException {
		con.close();
	}

	public void connectData() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Define database drive
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// form connection: (type of connection, database type, server,
		// database), user and password
		con = DriverManager.getConnection(dbName, user, password);
	}

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

	public void insert(String date, String hTeam, String vTeam, String hScore,
			String vScore) throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Statement stmt;
		String query;

		if (con.isClosed()) {
			connectData();
		}
		// TODO Compleate Insert statement This should receave fields from a
		// game object.
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
