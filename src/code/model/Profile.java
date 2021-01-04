package code.model;

import java.io.Serializable;

public class Profile implements Serializable, Comparable<Profile> {
	private static final long serialVersionUID = 1L;
	private String playerName;
	private int score;
	private String characterChoice;
	private int timeSurvived;
	
	public int getTimeSurvived() {
		return timeSurvived;
	}

	public void setTimeSurvived(int timeSurvived) {
		this.timeSurvived = timeSurvived;
	}

	public void setName(String name) {
		this.playerName = name;
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public void setScore(int finalScore) {
		this.score = finalScore;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setCharacterChoice(String character) {
		this.characterChoice = character;
	}
	
	public String getCharacterChoice() {
		return this.characterChoice;
	}
	
	public String toString() {
		return "Player: " + this.playerName + " - Score: " + this.score;
	}

	@Override
	public int compareTo(Profile otherProfile) {
		if (this.getScore() == otherProfile.getScore()) return 0;
		else if (this.getScore() > otherProfile.getScore()) return -1;
		else return 1;
	}

}
