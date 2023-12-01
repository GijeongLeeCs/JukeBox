// This handles all the getter like artist and names of a song

package model;

import java.io.Serializable;

public class Song implements Serializable {
	private String name;
	private String artist;
	private String songTime;

	public Song(String name, String artist, String songTime) {
		this.name = name;
		this.artist = artist;
		this.songTime = songTime;
	}

	/**
	 * 
	 * @return Song name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Song artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @return Time of song
	 */
	public String getSongTime() {
		return songTime;
	}

}