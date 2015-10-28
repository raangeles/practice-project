package bpm.training.richard.gmusic2.client.services;

import java.util.List;

import bpm.training.richard.gmusic2.shared.Music;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

//Step 1: define an interface that extends RemoteService
@RemoteServiceRelativePath("music")
public interface IMusicService extends RemoteService{

	public class Connect {
		  private static IMusicServiceAsync service;

		  public static IMusicServiceAsync getService() {
		  if (service == null) {
		    service = (IMusicServiceAsync) GWT.create(IMusicService.class);
		  }

		   return service;
		 }

	}
	
	//list all your RPC methods
	void addMusic(Music music) throws Exception;
	void updateMusic(Music music) throws Exception;
	void deleteMusic(Music music) throws Exception;
	List<Music> getAllMusicByUser() throws Exception;
	List<Music> getAllSharedMusic() throws Exception;
	List<Music> searchMusic(String keyword) throws Exception;
	
}
