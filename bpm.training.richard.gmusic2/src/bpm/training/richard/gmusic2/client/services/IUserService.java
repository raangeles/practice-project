package bpm.training.richard.gmusic2.client.services;

import java.util.List;

import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface IUserService extends RemoteService {
	public class Connect {
		  private static IUserServiceAsync service;

		  public static IUserServiceAsync getService() {
		  if (service == null) {
		    service = (IUserServiceAsync) GWT.create(IUserService.class);
		  }

		   return service;
		 }
		  
	}
	void addUser(User user) throws Exception;
	void setUserInSession(User user) throws Exception;
	User getUserFromSession() throws Exception;
	void logOutUser() throws Exception;
	User loginUser(User user)throws Exception;
	List<User> getAllUsers() throws Exception;
	void shareMusic(List<String> itemValues, Music music) throws Exception;
}
