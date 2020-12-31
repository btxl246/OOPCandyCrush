package ui.leaderBoard;

import java.io.Serializable;

public class Profile implements Serializable, Comparable<Profile> {
    private String playerName;
    private int score;

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

    @Override
    public String toString() {
        return "Player: " + this.playerName + " - Score: " + this.score;
    }

    @Override
    public int compareTo(Profile otherProfile) {
        return Integer.compare(otherProfile.getScore(), this.getScore());
    }

}