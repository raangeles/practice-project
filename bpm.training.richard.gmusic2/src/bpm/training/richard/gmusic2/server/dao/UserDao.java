package bpm.training.richard.gmusic2.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.User;
import bpm.training.richard.gmusic2.shared.UserMusic;

public class UserDao {
	
	public UserDao() {
		
	}
	
	public void saveUser(User user) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}

	public User loginUser(User user) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<User> users =  session.createQuery("from User WHERE loginId='" + user.getLoginId() + "'").list();
		session.getTransaction().commit();
		
		if(users.isEmpty()){
			throw new Exception("User does not exist...");
		}else{
			User userData = users.get(0);
			if(userData.getPassword().equals(user.getPassword())){
				return userData;
			}else{
				throw new Exception("Wrong password");
			}
		}
		
	}
	public List<User> getAllUsers() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<User> records = new ArrayList<User>(session.createQuery("from User").list());
		session.getTransaction().commit();
		return records;
	}
	
	public void shareMusic(List<String> itemValues, Music music) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		for(String item : itemValues){
			User user = (User) session.createQuery("from User where loginId='" + item + "'").list().get(0);	
			UserMusic userMusic = new UserMusic(user.getId(), music.getId());
			session.save(userMusic);
			session.getTransaction().commit();
		}
		
	}
	
	
}
