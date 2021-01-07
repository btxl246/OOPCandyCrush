package ui.leaderBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard implements Serializable {
    private ArrayList<Profile> leaderBoard;
    private Profile profile;
    private final File FILE = new File("out/leaderBoard");
    private int position;

    public LeaderBoard() {
        if (this.FILE.length() != 0)
            readData();
        else
            this.leaderBoard = new ArrayList<>();
    }

    public void considerScore(Profile profile) {
        this.profile = profile;
        this.leaderBoard.add(profile);
        sortData();
    }

    private void sortData() {
        Collections.sort(this.leaderBoard);
    }

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

    public boolean topThree() {
        return (getPosition() >= 1) && (getPosition() <= 3);
    }

    public ArrayList<Profile> getLeaderBoard() {
        return this.leaderBoard;
    }

    public int getPosition() {
        for (Profile p : this.leaderBoard)
            if (p.getDateAndTime().equals(this.profile.getDateAndTime()) && p.getName().equals(profile.getName()) && (p.getScore() == profile.getScore()))
                this.position = leaderBoard.indexOf(p) + 1;

        return position;
    }
}