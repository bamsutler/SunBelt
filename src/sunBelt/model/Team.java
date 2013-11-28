/**
 * 
 */
package sunBelt.model;

/**
 * Team class of the SunBelt Project. This class holds the names, shortnames, and other information
 * about the teams in the database. This data is used to build display information and 
 * translate ID numebrs into names and names into ID numbers. 
 * 
 * @author Sam Butler 
 */
public class Team {

	private String teamID;
	private String teamShort;
	private String teamName;
	private String teamMascot;
	private int wins;
	private int losses;
	/**
	 * Constructor for the Team object. all inputs are Strings. 
	 * 
	 * @param id string representing the ID of the team
	 * @param abbreviation String of the short name of the team
	 * @param name the String that is the full name of the team
	 * @param mascot  String that holds what the mascot of the team is called.
	 */
	public Team(String id, String abbreviation, String name, String mascot) {
		// Constructor for the Team object
		teamID = id;
		teamShort = abbreviation;
		teamName = name;
		teamMascot = mascot;

	}
	
	public String getRecord(){
		return  "" + this.getWins()+" / "+this.getLosses()+"";
	}
	/**
	 * empty constructor for Team object
	 */
	public Team() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the teamID
	 */
	public String getTeamID() {
		return teamID;
	}

	/**
	 * @param teamID
	 *            the teamID to set
	 */
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	/**
	 * @return the teamShort
	 */
	public String getTeamShort() {
		return teamShort;
	}

	/**
	 * @param teamShort
	 *            the teamShort to set
	 */
	public void setTeamShort(String teamShort) {
		this.teamShort = teamShort;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName
	 *            the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the teamMascot
	 */
	public String getTeamMascot() {
		return teamMascot;
	}

	/**
	 * @param teamMascot
	 *            the teamMascot to set
	 */
	public void setTeamMascot(String teamMascot) {
		this.teamMascot = teamMascot;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @param wins
	 *            the wins to set
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * @return the losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * @param losses
	 *            the losses to set
	 */
	public void setLosses(int losses) {
		this.losses = losses;
	}

}
