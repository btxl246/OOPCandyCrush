/**
 * Object-Oriented Programming project.
 * @author Bui Thi Xuan Lan - ITDSIU19007
 * @author Nguyen Duc Minh - ITITIU19030
 */

package ui.leaderBoard;

import java.io.Serializable;

/**
 * A player's profile containing when their game finishes, name, and score.
 */

public class Profile implements Serializable, Comparable<Profile> {
    private String dateAndTime;
    private String name;
    private int score;

    public String getDateAndTime() {
        return this.dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int finalScore) {
        this.score = finalScore;
    }

    @Override
    public String toString() {
        return "Player: " + this.name + " - Score: " + this.score;
    }

    @Override
    public int compareTo(Profile otherProfile) {
        return Integer.compare(otherProfile.getScore(), this.getScore());
    }
}