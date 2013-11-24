/**
 * 
 */
package sunBelt.model;

/**
 * @author Sam Butler TODO Document this shit
 */
public class Team {

	private String teamID;
	private String teamShort;
	private String teamName;
	private String teamMascot;
	private int wins;
	private int losses;

	public Team(String id, String abbreviation, String name, String mascot) {
		// Constructor for the Team object
		teamID = id;
		teamShort = abbreviation;
		teamName = name;
		teamMascot = mascot;

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
