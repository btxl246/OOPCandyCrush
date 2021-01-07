/**
 * Object-Oriented Programming project.
 * @author Bui Thi Xuan Lan - ITDSIU19007
 * @author Nguyen Duc Minh - ITITIU19030
 */

package ui.leaderBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Handles the record of players.
 */

public class LeaderBoard implements Serializable {
    private ArrayList<Profile> leaderBoard;                             // Profiles of players.
    private Profile profile;                                            // New player.
    private final File FILE = new File("out/leaderBoard");     // Saved record.
    private int position;                                               // New player's position.

    /**
     * LeaderBoard constructor.
     */

    public LeaderBoard() {
        // If the record is available.
        if (this.FILE.length() != 0)
            readData();                             // Reads it.
        else
            this.leaderBoard = new ArrayList<>();   // Creates a new record.
    }

    /**
     * Enters a profile.
     * @param profile Profile.
     */

    public void considerScore(Profile profile) {
        this.profile = profile;
        this.leaderBoard.add(profile);
        sortData();
    }

    /**
     * Sorts the record.
     */

    private void sortData() {
        Collections.sort(this.leaderBoard);
    }

    /**
     * Writes the record to file.
     */

    public void writeData() {
        try {
            FileOutputStream fos = new FileOutputStream(this.FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.leaderBoard);
            oos.flush();
            oos.close();
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the record.
     */

    public void readData() {
        try {
            FileInputStream fis = new FileInputStream(this.FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.leaderBoard = (ArrayList<Profile>) ois.readObject();
            fis.close();
            ois.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the player is in the top three.
     * @return True if in top three.
     */

    public boolean topThree() {
        return (getPosition() >= 1) && (getPosition() <= 3);
    }

    /**
     * Returns the record.
     * @return Record.
     */

    public ArrayList<Profile> getLeaderBoard() {
        return this.leaderBoard;
    }

    /**
     * Returns the position in the record of the new player.
     * @return Position.
     */

    public int getPosition() {
        for (Profile p : this.leaderBoard)
            if (p.getDateAndTime().equals(this.profile.getDateAndTime()) && p.getName().equals(profile.getName()) && (p.getScore() == profile.getScore()))
                this.position = leaderBoard.indexOf(p) + 1;

        return position;
    }
}