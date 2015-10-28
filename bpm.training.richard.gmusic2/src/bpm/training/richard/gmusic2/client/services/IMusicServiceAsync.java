package bpm.training.richard.gmusic2.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import bpm.training.richard.gmusic2.shared.Music;

/*Add an AsyncCallback parameter to all your service methods. 
 *** It must have the same name as the service interface, appended with Async
 *** Each method must have the same name and signature as in the service interface
 *** but : the methods must have no return type and the last parameter is and AsyncCallback object */
public interface IMusicServiceAsync {

	void addMusic(Music music, AsyncCallback<Void> asyncCallback);

	void getAllMusicByUser(AsyncCallback<List<Music>> callback);

	void getAllSharedMusic(AsyncCallback<List<Music>> callback);

	void deleteMusic(Music music, AsyncCallback<Void> callback);

	void updateMusic(Music music,
			AsyncCallback<Void> callback);

	void searchMusic(String keyword, AsyncCallback<List<Music>> callback);


}
