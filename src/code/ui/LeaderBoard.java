package code.ui;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import code.model.*;

public class LeaderBoard implements Serializable {
	private ArrayList<Profile> highscoreArray;
	//private String FILE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
	private File saveFile = new File("out/leaderboard.csv");
	
	public LeaderBoard() {
		if (saveFile.length() != 0) {
			readData();
		}
		else {
			highscoreArray = new ArrayList<Profile>();
		}
	}
	
	public ArrayList<Profile> getArray() {
		return this.highscoreArray;
	}
	
	/** considerScore() method "considers" the score whether it would be on the leader board
	 * 
	 * */
	public void considerScore(int finalScore, Profile player) {
		if (highscoreArray.size() == 0 || highscoreArray.size() < 10) {
			highscoreArray.add(player);
			sortData();
		}
		else {
			for(int i = 0; i < highscoreArray.size(); i++) {
				if (finalScore > highscoreArray.get(i).getScore()) {
					highscoreArray.add(player);
					sortData();
					if (highscoreArray.size() >= 10) highscoreArray.remove(10); //removing the 11th-highest player
					break;
				}
			}
		}
	}
	
	//Sort the leader board in a descending order
	public void sortData() {
		Collections.sort(highscoreArray);
	}
	
	/** writeData() method writes the ArrayList of sorted Profiles into an external file
	 * 
	 * */
	public void writeData() {
		try {
			FileOutputStream fos = new FileOutputStream(saveFile); 
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.highscoreArray);
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
	public void readData() {
		FileInputStream fis = null; 													//to read data
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(saveFile);
			ois = new ObjectInputStream(fis);
			this.highscoreArray = (ArrayList<Profile>) ois.readObject(); 				//read the file and put the Object stored in the file in another object
			//System.out.println(this.highscoreArray);									//test
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
	}
}
