package bpm.training.richard.gmusic2.shared;

import java.io.Serializable;

public class Music implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String description;
	private String artist;
	private String picture;
	private Long userId;
	
	public Music() {
		// TODO Auto-generated constructor stub
	}

	public Music(String title, String description, String artist, String picture) {
		super();
		this.title = title;
		this.description = description;
		this.artist = artist;
		this.picture = picture;
	}
	
		
	public Music( String title, String description, String artist,
			String picture, Long userId) {
		super();
		this.title = title;
		this.description = description;
		this.artist = artist;
		this.picture = picture;
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
