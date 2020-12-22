package code.ui;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import code.model.*;

public class LeaderBoard implements Serializable {
	private static LeaderBoard lBoard;
	private Profile player;
	private ArrayList<Profile> leaderBoard = new ArrayList<Profile>();
	
	private LeaderBoard() {}
	
	public static LeaderBoard getInstance() {
		if (lBoard == null) lBoard = new LeaderBoard();
		return lBoard;
	}
	
	/** considerScore() method "considers" the score whether it would be on the leader board
	 * 
	 * */
	public void considerScore(int finalScore, Profile player) {
		for(int i = 0; i < leaderBoard.size(); i++) {
			if (finalScore > leaderBoard.get(i).getScore()) {
				leaderBoard.add(player);
				sortData();
				leaderBoard.remove(leaderBoard.size()-1); //removing the 11th-highest player
				break;
			}
		}
	}
	
	//Sort the leader board in a descending order
	public void sortData() {
		Collections.sort(leaderBoard);
	}
	
	/** writeData() method writes the ArrayList of sorted Profiles into an external file
	 * 
	 * */
	public void writeData() {
		try {
			FileOutputStream fos = new FileOutputStream("D:\\leaderboard.csv"); 
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
	
	
	/** readData() method reads the ArrayList of sorted Profiles from an external file and put it in an object
	 * 
	 * */	
	public ArrayList<Profile> readData() {
		ArrayList<Profile> highscores = null;
		FileInputStream fis = null; //to read data
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("D:\\leaderboard.csv");
			ois = new ObjectInputStream(fis);
			highscores = (ArrayList<Profile>) ois.readObject(); //read the file and put the Object stored in the file in another object
			System.out.println(highscores);									//test
		} 
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				fis.close();
				ois.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return highscores;
	}
	
	public void test() {
		
		Profile player1 = new Profile();
		player1.setName("A");
		player1.setScore(50);
		
		Profile player2 = new Profile();
		player2.setName("B");
		player2.setScore(100);
		
		Profile player3 = new Profile();
		player3.setName("C");
		player3.setScore(75);
		
		Profile player4 = new Profile();
		player4.setName("D");
		player4.setScore(80);
		
		this.leaderBoard.add(player1);
		this.leaderBoard.add(player2);
		this.leaderBoard.add(player3);
		
		this.sortData();
		
		this.considerScore(player4.getScore(), player4);
		
		System.out.println(leaderBoard);
		this.writeData();
	}
}
