/**
 * 
 */
package sunBelt.model;

/**
 * @author Sam TODO document this shit
 */
public class Game {
	private Integer gameID;
	private String gameDate;
	private String gameHTeamID;
	private String gameHName;
	private String gameVTeamID;
	private String gameVName;
	private Integer gameHScore;
	private Integer gameVScore;

	public Game() {
	}

	// This is the constructor that comes from the SunBelt Controller on Insert.
	// the Strings need to be changed to Integers
	public Game(String date, String hTeam, String vTeam, String hScore,
			String vScore) {
		setGameDate(date);
		setGameHTeamID(hTeam);
		setGameVTeamID(vTeam);
		setGameHScore(Integer.valueOf(hScore));
		setGameVScore(Integer.valueOf(vScore));
	}

	public Game(int gid, String date, String hTeam, String vTeam, int hScore,
			int vScore) {
		setGameID((Integer) gid);
		setGameDate(date);
		setGameHTeamID(hTeam);
		setGameVTeamID(vTeam);
		setGameHScore((Integer) hScore);
		setGameVScore((Integer) vScore);
	}

	/**
	 * @return the gameID
	 */
	public Integer getGameID() {
		return gameID;
	}

	/**
	 * @param gameID
	 *            the gameID to set
	 */
	public void setGameID(Integer gameID) {
		this.gameID = gameID;
	}

	/**
	 * @return the gameDate
	 */
	public String getGameDate() {
		return gameDate;
	}

	/**
	 * @param gameDate
	 *            the gameDate to set
	 */
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}

	/**
	 * @return the gameHTeam
	 */
	public String getGameHTeamID() {
		return gameHTeamID;
	}

	/**
	 * @param gameHTeam
	 *            the gameHTeam to set
	 */
	public void setGameHTeamID(String gameHTeam) {
		this.gameHTeamID = gameHTeam;
	}

	/**
	 * @return the gameVTeam
	 */
	public String getGameVTeamID() {
		return gameVTeamID;
	}

	/**
	 * @param gameVTeam
	 *            the gameVTeam to set
	 */
	public void setGameVTeamID(String gameVTeam) {
		this.gameVTeamID = gameVTeam;
	}

	/**
	 * @return the gameHScore
	 */
	public Integer getGameHScore() {
		return gameHScore;
	}

	/**
	 * @param gameHScore
	 *            the gameHScore to set
	 */
	public void setGameHScore(Integer gameHScore) {
		this.gameHScore = gameHScore;
	}

	/**
	 * @return the gameVScore
	 */
	public Integer getGameVScore() {
		return gameVScore;
	}

	/**
	 * @param gameVScore
	 *            the gameVScore to set
	 */
	public void setGameVScore(Integer gameVScore) {
		this.gameVScore = gameVScore;
	}

	/**
	 * @return the gameHName
	 */
	public String getGameHName() {
		return gameHName;
	}

	/**
	 * @param gameHName
	 *            the gameHName to set
	 */
	public void setGameHName(String gameHName) {
		this.gameHName = gameHName;
	}

	/**
	 * @return the gameVName
	 */
	public String getGameVName() {
		return gameVName;
	}

	/**
	 * @param gameVName
	 *            the gameVName to set
	 */
	public void setGameVName(String gameVName) {
		this.gameVName = gameVName;
	}

	public String toString() {
		return "" + getGameID() + " " + getGameDate() + " " + getGameHName()
				+ " " + getGameVName() + " " + getGameHScore() + " "
				+ getGameVScore();
	}
}
