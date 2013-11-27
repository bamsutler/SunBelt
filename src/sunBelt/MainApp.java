/**
 * This is the main Application file for the SunBelt project. most if not all of the Processing along with The main
 * method Are located in this file. 
 *      This Project was written for Dr. Smith's 4055 Theory of Database Management Class at ULM.
 *      Fall 2013 
 * 
 * @author Samuel Butler
 * 
 */ 

package sunBelt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sunBelt.model.Game;
import sunBelt.model.Team;

public class MainApp extends Application {
        
        /**
         * Main method calls the launch method that launches that stand alone JavaFX application.
         * 
         * @param an Array for Strings that are the command line arguments. These do not effect the outcome of this 
         * program. 
         */
	public static void main(String[] args) {
		launch(args);

	}

	// Master List for Game Objects
	private ObservableList<Game> gameData = FXCollections.observableArrayList();
	// list of teams and associated data
	private ObservableList<Team> teamData = FXCollections.observableArrayList();
	// The List of Game Objects that will be actualy displayed
	private ObservableList<Game> displayGames = FXCollections
			.observableArrayList();
	// list of team names to populate the left hand selector window
	protected ObservableList<String> teams = FXCollections
			.observableArrayList();
	// list of Short Team Name to Populate The Combo Box Drop Down Menus.
	protected ObservableList<String> sTeams = FXCollections
			.observableArrayList();
	// constant for selection and output on the selection list
	private final String all = "All Teams";
	// current selected team
	protected String currentTeam = all;
        //Calling the constructor for the database management class that was written for this project. 
	private DataMan dataManager;

	/**
	 * The constructor for this class is called by the control document for the GUI. This alows the controller to 
	 * call the methods for processing uesr input to the gui. When called This method Connects to the database and  
	 * submits querys to gain the information from the database that is used to populate the arraylists in this
	 * Class. 
	 */
	public MainApp() {


		try {
			// Connect to the DataBase
			dataManager = new DataMan("config.txt");
			ResultSet set;

			// Ask for Query REsult from TEAM from DataManager
			set = dataManager.getData("TEAM");
			// construct Team List
			populateMasterTeamList(set);

			// Ask for Query result for GAME from DataManager
			set = dataManager.getData("GAME");
			// Construct Master GameData List
			populateMasterGameList(set);
			updateDisplayGamesList();
		} catch (Exception e) {
			System.err.print(e);
		}

	}
        
        /**
         * fixGameNames Iterates threw both the Team list and The Master Game list. It pulls the Team ID from the Team 
         * object it is currently on and begins an iteration of Game objects checking the IDs of the teams from that 
         * game. When a match is found The appropriate Name Variable from that game object is set to the Name of the Team
         * object currently being iterated over. 
         * 
         */
	private void fixGameNames() {

                //iterate the team list
		for (Team team : teamData) {
		        //Retrieve the ID of the team currently being iterated on
			String teamID = team.getTeamID();
			//Begin iteration over the game objects
			for (Game game : gameData) {
				String visit = game.getGameVTeamID();
				String home = game.getGameHTeamID();
                                //if the home team of visiting team id's for the current game match the id for the current
                                // team set the appropriate name variable to the name of the Team. 
				if (home.equals(teamID)) {
					game.setGameHName(team.getTeamName());
				}
				if (visit.equals(teamID)) {
					game.setGameVName(team.getTeamName());
				}
			}
		}

	}
        /**
        *
        *returns the value of the Currently Selected Team from the List View. This is the filter for showing only one 
        * teams games at a time. 
        * 
        * @return String the current team representing the currently selected team filter. Initiates to "all"
        */
        
	public String getCurrentTeam() {
		return currentTeam;
	}

	/**
	 * getDisplayGames is a method to retrieve the ArrayList of Game objects in the ObservableArrayList displayGames.
	 * the Objects in this list are used to populate the Table on the interface. 
	 * 
	 * @return the displayGames list of Game Objects
	 */
	public ObservableList<Game> getDisplayGames() {
		return displayGames;
	}

	/**
	 * getGameData is a method to retrieve the ArrayList of Game objects in the ObservableArrayList gameData.
	 * The Objects in this list are used as a basis to form the displayGame List. By doing this we can modify 
	 * The displayGame list without trying to iterate and change a list at the same time. 
	 * 
	 * @return the gameData list of Game Objects
	 */
	public ObservableList<Game> getGameData() {
		return gameData;

	}

	/**
	 *  getTeamData is a method to retrieve the ArrayList of Team objects in the ObservableArrayList teamData.
	 *  
	 * @return the teamData list of Team Objects
	 */
	public ObservableList<Team> getTeamData() {
		return teamData;
	}
        
        /**
         *	Translates Team names or short team names to their corresponding id numbers by iterating threw the Team 
         * List and returning the team id of the requested team. Returns the all constant if their are no matches to a
         * team name.
         * 
         * @param teamName String containing the name or short name of the a team.
         * @return a String containing the ID number of the requested team.
         */
	public String getTeamID(String teamName) {
		String temp = null;
		for (Team team : teamData) {
			if (teamName.toUpperCase().equals(team.getTeamName().toUpperCase())
					|| teamName.toUpperCase().equals(
							team.getTeamShort().toUpperCase())) {
				temp = team.getTeamID();
				break;
			} else {
				temp = all;
			}
		}
		return temp;
	}

		/**
		 * 
		 * Insert data calls on the data Manager to Insert Game object information into the GAME table in our Database. 
		 * after insertion the new database table is requested and the ResultSet is used to repopulate the master game list. 
		 * Then the displayGame list objects are updated with the new data. 
		 * 
		 * @param g a Game object to have its values inserted into the DataBase. 
		 */
	public void insertData(Game g) {
		// calls insert from DataMan and insert the values from Sunbelt
		// Controller. calls Populate master List when finished to refresh
		// tableView
		// and updateDislayGame list to update the display
		String hScore = String.valueOf(g.getGameHScore());
		String vScore = String.valueOf(g.getGameVScore());

		try {
			//call to dataMan insert method passing the variables from the Game object
			dataManager.insert(g.getGameDate(), g.getGameHTeamID(),
					g.getGameVTeamID(), hScore, vScore);
			//Request The New GAME table information
			ResultSet set = dataManager.getData("GAME");
			// Repopulate the Master Game list.
			this.populateMasterGameList(set);
			
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			System.err.println("ERROR-Failed to Insert or ");
			e.printStackTrace();
		}
		this.updateDisplayGamesList();

	}
	
	/**
	 * Method to iterate threw a ResultSet and build Game objects while applying them to the gameData Observable List
	 * After building the gameData List fixGameNames is called to insert appropriate Team Names into the objects.  
	 * 
	 * @param set The ResultSet to be iterated over. 
	 * @throws SQLException
	 */
	public void populateMasterGameList(ResultSet set) throws SQLException {
		gameData.clear();
		while (set.next()) {
			gameData.add(new Game(set.getInt("gid"), set.getString("day"), set
					.getString("hteam"), set.getString("vteam"), set
					.getInt("hscore"), set.getInt("vscore")));

		}
		fixGameNames();
	}

	/**
	 *  Method to iterate threw a ResultSet and build Team objects while applying them to the teamData Observable List and
	 *  short names from the ResultSet to the sTeams list. Begins by erasing any current Data in the teams and sTeams lists.  
	 * 
	 * @param set ResultSet to be iterated over.
	 * @throws SQLException
	 */
	public void populateMasterTeamList(ResultSet set) throws SQLException {

		teams.clear();
		sTeams.clear();
		teams.add(all);
		while (set.next()) {
			// Add the returned Team Data to the teamData list as team objects
			teamData.add(new Team(set.getString("teamid"), set
					.getString("short"), set.getString("name"), set
					.getString("mascot")));
			// add just the names of the teams to a list for the selector on the
			// left of the GUI

			teams.add(set.getString("name"));
			sTeams.add(set.getString("short"));
		}

	}

	/**
	 * 
	 * @param game
	 */
	public void removeGame(Game game) {
		// calls delete from DataMan and deletes the vaules sent from Sunbelt
		// Controller. calls Populate master List when finsihed to refresh
		// tableView
		// and updateDislayGame list to update the display

		try {
			dataManager.delete(game.getGameID());
			ResultSet set = dataManager.getData("GAME");
			this.populateMasterGameList(set);
		} catch (SQLException e) {
			System.err.println("ERROR - Faild to Query DataBase");
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			System.err.println("ERROR - Faild to DELETE from DataBase");
			e.printStackTrace();
		}
		this.updateDisplayGamesList();

	}

	public void setCurrentTeam(String teamID) {
		currentTeam = teamID;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/SunBelt.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			SunBeltController controller = loader.getController();
			controller.setMainApp(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateDisplayGamesList() {
		// empty display list
		displayGames.clear();
		// if all is selected display entire list of games copied from the master
		// list
		if (currentTeam.equals(all)) {
			displayGames.addAll(gameData);
		} else {
			// if the current selection is a team iterate threw the master list
			// and add only games with that team id to the display list.
			for (Game game : gameData) {
				if (game.getGameHTeamID().equals(getCurrentTeam())
						|| game.getGameVTeamID().equals(getCurrentTeam())) {
					displayGames.add(game);
				}
			}
		}
	}

	// TODO Method to calculate Wins For Teams on Update of Master Game List.

	// Validates Table Input so no sneaky teacher surprises cant hinder my good
	// grade.
	public Boolean vailidateInput(String date, String hTeam, String vTeam,
			String hScore, String vScore) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Boolean valid = true;
		int test1, test2;

		try {
			Date newDate = df.parse(date);
			System.err.println("yay" + newDate + "worked");
			if (!teams.contains(hTeam.toUpperCase())
					&& !sTeams.contains(hTeam.toUpperCase())) {
				valid = false;
				System.err.println("Hteam invalid");
			}
			if (!teams.contains(vTeam.toUpperCase())
					&& !sTeams.contains(vTeam.toUpperCase())) {
				valid = false;
				System.err.println("Vteam invalid");
			}
			test1 = Integer.parseInt(hScore);
			if (test1 < 0) {
				valid = false;
			}
			test2 = Integer.parseInt(vScore);
			if (test2 < 0) {
				valid = false;
			}
		} catch (ParseException pe) {
			System.err.println("Date invalid");
			return false;
		} catch (NumberFormatException e) {
			System.err.println("Scores invalid");
			return false;
		}

		return valid;

	}

}
