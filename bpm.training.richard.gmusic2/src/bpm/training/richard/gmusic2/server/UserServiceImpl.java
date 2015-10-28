package bpm.training.richard.gmusic2.server;

import java.util.List;

import javax.servlet.http.HttpSession;

import bpm.training.richard.gmusic2.client.resources.IConstants;
import bpm.training.richard.gmusic2.client.services.IUserService;
import bpm.training.richard.gmusic2.server.dao.UserDao;
import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements IUserService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserDao userDao = new UserDao();
	
	/**
	 * Sessions
	 */
	 public void setUserInSession(User user) {
	      HttpSession session = getThreadLocalRequest().getSession();
	      session.setAttribute(IConstants.USER_SESSION, user);
	 }

	 public User getUserFromSession() {
	      HttpSession session = getThreadLocalRequest().getSession();
	      return (User) session.getAttribute(IConstants.USER_SESSION);
	 }
	
	 public void logOutUser() {
		HttpSession session =getThreadLocalRequest().getSession();  
        session.invalidate();  
	}
	
	@Override
	public void addUser(User user) throws Exception {
		userDao.saveUser(user);
	}

	@Override
	public User loginUser(User user) throws Exception {
		user = userDao.loginUser(user);
		setUserInSession(user);
		return user;
	}
	
	@Override
	public List<User> getAllUsers() throws Exception {
		return userDao.getAllUsers();
	}

	@Override
	public void shareMusic(List<String> itemValues, Music music) throws Exception {
		userDao.shareMusic(itemValues, music);
	}
}
