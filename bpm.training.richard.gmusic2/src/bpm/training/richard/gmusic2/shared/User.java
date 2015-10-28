package bpm.training.richard.gmusic2.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String loginId;
	private String password;
	private String profilePhoto;
	private List<Music> musics = new ArrayList<Music>();
	
	public User() {
		
	}
	
	public User(String loginId, String password) {
		super();
		this.loginId = loginId;
		this.password = password;
	}

	public User( String name, String loginId, String password, String profilePhoto) {
		super();
		this.name = name;
		this.loginId = loginId;
		this.password = password;
		this.profilePhoto = profilePhoto;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public List<Music> getMusics() {
		return musics;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}
}
