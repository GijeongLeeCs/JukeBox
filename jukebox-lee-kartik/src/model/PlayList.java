/*
 * Gijeong Lee and Kartik
 * This is a queue for showing the list of songs.
 * It is FIFO.
 */

package model;

import controller_view.LoginCreateAccountPane;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import demoMediaPlayer.PlayAnMP3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayList {
	private ArrayList list;
	private ObservableList<String> playlist;
	private PlayAnMP3 mp3;
	private boolean isPlaying = false;
	LoginCreateAccountPane loginPane;
	
//	private JukeboxAccount user;

	/*
	 * Constructor creating a new LinkedList for queue.
	 */
	public PlayList() {
		list = new ArrayList<String>();
		playlist = FXCollections.observableArrayList();
		mp3 = new PlayAnMP3();
	}

	/*
	 * @param songToAdd which is a String that will be added to queue
	 */
	public void queueUpNextSong(String songToAdd) {
		if (playlist.size() == 0) {
			isPlaying = false;
		}
		playlist.add(songToAdd);
	}

	/*
	 * @return a boolean specifying if the playlist is playing right now
	 */
	public boolean getIsPlaying() {
		return isPlaying;
	}

	@SuppressWarnings("unchecked")
	public void savePlayList() {
		try {
			FileOutputStream bytesToDisk = new FileOutputStream("playlist.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);

			list.clear();
			list.addAll(playlist);

			outFile.writeObject(list);

			list.clear();

			outFile.close();

		} catch (IOException ioe) {
			System.out.println("Write failed");
		}
	}

	/**
	 * Deserializes the playlist into an observable arraylist
	 */
	@SuppressWarnings("unchecked")
	public void readPlayList() {
		try {
			FileInputStream bytesFromDisk = new FileInputStream("playlist.ser");
			ObjectInputStream inFile = new ObjectInputStream(bytesFromDisk);

			list = (ArrayList<String>) inFile.readObject();

			playlist.clear();
			playlist.addAll(list);

			inFile.close();

		} catch (IOException ioe) {
			System.out.println("read failed");
		} catch (ClassNotFoundException e) {
			System.out.println("Class cast exception");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current playlist object
	 * 
	 * @return playlist the song in a given playlist
	 */
	public ObservableList<String> getPlayList() {
		return this.playlist;
	}

	public void playSongs(JukeboxAccount user) {
		isPlaying = true;
		mp3.play(playlist);
	}
	
}