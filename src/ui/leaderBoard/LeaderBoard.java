package ui.leaderBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard implements Serializable {
    private ArrayList<Profile> leaderBoard;
    private final File FILE = new File("out/leaderBoard");

    public LeaderBoard() {
        if (this.FILE.length() != 0)
            readData();
        else
            this.leaderBoard = new ArrayList<>();
    }

    public ArrayList<Profile> getLeaderBoard() {
        return this.leaderBoard;
    }

    public void considerScore(Profile profile) {
        if (this.leaderBoard.size() == 0 || leaderBoard.size() < 10) {
            leaderBoard.add(profile);
            sortData();
        }
        else {
            for (int i = 0; i < leaderBoard.size(); i++) {
                if (profile.getScore() > leaderBoard.get(i).getScore()) {
                    leaderBoard.add(profile);
                    sortData();
                    if (leaderBoard.size() >= 10)
                        leaderBoard.remove(10);
                    break;
                }
            }
        }
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
}