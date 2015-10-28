package bpm.training.richard.gmusic2.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.UserMusic;

public class MusicDAO {

	public MusicDAO() {

	}

	public void saveMusic(Music music) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(music);
		session.getTransaction().commit();
	}

	public void deleteMusic(Music music) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(music);
		session.getTransaction().commit();
	}

	public void updateMusic(Music music) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(music);
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Music> getAllMusicByUser(long userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Music> records = new ArrayList<Music>(session.createQuery("from Music WHERE userId='" + userId + "'").list());
		session.getTransaction().commit();
		return records;
	}

	public List<Music> getAllSharedMusic(long userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<UserMusic> musicList = new ArrayList<UserMusic>(session.createQuery("from UserMusic WHERE userId='" + userId + "'").list());
		List<Music> sharedMusic  = new ArrayList<Music>();
		for(UserMusic um : musicList){
			Music music = (Music) session.createQuery("from Music WHERE id='" + um.getMusicId() + "'").list().get(0);
			sharedMusic.add(music);
		}
		
		session.getTransaction().commit();
		return sharedMusic;
	}

	public List<Music> searchMusic(String keyword) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Music> records = new ArrayList<Music>(session.createQuery("from Music WHERE music_title LIKE '%" + keyword + "%' OR music_artist LIKE '%" + keyword + "%' OR music_description LIKE '%" + keyword + "%'").list());
		session.getTransaction().commit();
		return records;
	}
}
