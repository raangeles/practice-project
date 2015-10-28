package bpm.training.richard.gmusic2.client.services;

import java.util.List;

import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IUserServiceAsync {

	void addUser(User user, AsyncCallback<Void> callback);
	void loginUser(User user, AsyncCallback<User> callback);
	void logOutUser(AsyncCallback<Void> callback);
	void getUserFromSession(AsyncCallback<User> callback);
	void setUserInSession(User user, AsyncCallback<Void> callback);
	void getAllUsers(AsyncCallback<List<User>> callback);
	void shareMusic(List<String> itemValues, Music music, AsyncCallback<Void> asyncCallback);
}
