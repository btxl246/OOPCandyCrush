package ui.leaderBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard implements Serializable {
    private static LeaderBoard instance;
    private ArrayList<Profile> leaderBoard;
    private final File FILE = new File("out/leaderBoard.csv");

    private LeaderBoard() {
        if (this.leaderBoard != null) {
            leaderBoard = readData();
        }
        else leaderBoard = new ArrayList<>();
    }

    public static LeaderBoard getInstance() {
        if (instance == null) instance = new LeaderBoard();
        return instance;
    }

    public void considerScore(int finalScore, Profile profile) {
        if (this.leaderBoard.size() == 0 || leaderBoard.size() < 10) {
            leaderBoard.add(profile);
            sortData();
        }
        else {
            for (int i = 0; i < leaderBoard.size(); i++) {
                if (finalScore > leaderBoard.get(i).getScore()) {
                    leaderBoard.add(profile);
                    sortData();
                    if (leaderBoard.size() >= 10) leaderBoard.remove(10);
                    break;
                }
            }
        }
    }

    public void sortData() {
        Collections.sort(leaderBoard);
    }

    public void writeData() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance.leaderBoard);
            oos.flush();
            oos.close();
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Profile> readData() {
        ArrayList<Profile> leaderBoardRecord = null;
        FileInputStream fis = null;
        ObjectInputStream ois;

        try {
            fis = new FileInputStream(FILE);
            ois = new ObjectInputStream(fis);
            leaderBoardRecord = (ArrayList<Profile>) ois.readObject();
            System.out.println(leaderBoardRecord);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fis.close();
                //ois.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return leaderBoardRecord;
    }
}