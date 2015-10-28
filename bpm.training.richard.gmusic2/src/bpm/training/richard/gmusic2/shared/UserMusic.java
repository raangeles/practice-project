package bpm.training.richard.gmusic2.shared;

import java.io.Serializable;

public class UserMusic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long userId;
	private long musicId;
	
	public UserMusic() {
		
	}

	public UserMusic(long userId, long musicId) {
		super();
		this.userId = userId;
		this.musicId = musicId;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMusicId() {
		return musicId;
	}

	public void setMusicId(long musicId) {
		this.musicId = musicId;
	}
	
	
	
}
