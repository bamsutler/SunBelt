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

	private DataMan dataManager;

	/**
	 * constructor
	 * 
	 */
	public MainApp() {

		// for some reason when i try to use the config.txt file the system
		// cannot find it. so i made 2 constructors for DataMan.
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

			// buildNameIDMap(); i don't think i will need this anymore. removed
			// with the addition of gameHName and gameVName in Game class

		} catch (Exception e) {
			System.err.print(e);
		}

	}

	private void fixGameNames() {

		for (Team team : teamData) {
			String teamID = team.getTeamID();
			for (Game game : gameData) {
				String visit = game.getGameVTeamID();
				String home = game.getGameHTeamID();

				if (home.equals(teamID)) {
					game.setGameHName(team.getTeamName());
				}
				if (visit.equals(teamID)) {
					game.setGameVName(team.getTeamName());
				}
			}
		}

	}

	public String getCurrentTeam() {
		return currentTeam;
	}

	/**
	 * @return the displayGames list of Game Objects
	 */
	public ObservableList<Game> getDisplayGames() {
		return displayGames;
	}

	/**
	 * @return the gameData list of Game Objects
	 */
	public ObservableList<Game> getGameData() {
		return gameData;

	}

	/**
	 * @return the teamData list of Team Objects
	 */
	public ObservableList<Team> getTeamData() {
		return teamData;
	}

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

	public void insertData(Game g) {
		// calls insert from DataMan and insert the vaules from Sunbelt
		// Controller. calls Populate master List when finsihed to refresh
		// tableView
		// and updateDislayGame list to update the display
		String hScore = String.valueOf(g.getGameHScore());
		String vScore = String.valueOf(g.getGameVScore());

		try {

			dataManager.insert(g.getGameDate(), g.getGameHTeamID(),
					g.getGameVTeamID(), hScore, vScore);
			ResultSet set = dataManager.getData("GAME");
			this.populateMasterGameList(set);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			System.err.println("ERROR-Failed to Insert or ");
			e.printStackTrace();
		}
		this.updateDisplayGamesList();

	}

	public void populateMasterGameList(ResultSet set) throws SQLException {
		gameData.clear();
		while (set.next()) {
			gameData.add(new Game(set.getInt("gid"), set.getString("day"), set
					.getString("hteam"), set.getString("vteam"), set
					.getInt("hscore"), set.getInt("vscore")));

		}
		fixGameNames();
	}

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
		// if all is selected diplay entire list of games copied from the master
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

	// Validates Table Input so no sneaky teacher surprises can hinder my good
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
