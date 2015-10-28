package bpm.training.richard.gmusic2.server;

import java.util.List;

import javax.servlet.http.HttpSession;

import bpm.training.richard.gmusic2.client.resources.IConstants;
import bpm.training.richard.gmusic2.client.services.IMusicService;
import bpm.training.richard.gmusic2.server.dao.MusicDAO;
import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MusicServiceImpl extends RemoteServiceServlet implements
		IMusicService {

	private static final long serialVersionUID = 1L;

	private MusicDAO musicDao = new MusicDAO();

	public User getUserFromSession() {
		HttpSession session = getThreadLocalRequest().getSession();
		return (User) session.getAttribute(IConstants.USER_SESSION);
	}

	public void addMusic(Music music) throws Exception {
		musicDao.saveMusic(music);
	}

	public void deleteMusic(Music music) throws Exception {
		musicDao.deleteMusic(music);
	}

	public void updateMusic(Music music) throws Exception {
		musicDao.updateMusic(music);
	}

	public List<Music> getAllMusicByUser() throws Exception {
		return musicDao.getAllMusicByUser(getUserFromSession().getId());
	}

	public List<Music> getAllSharedMusic() throws Exception {
		return musicDao.getAllSharedMusic(getUserFromSession().getId());
	}

	public List<Music> searchMusic(String keyword) throws Exception {
		return musicDao.searchMusic(keyword);

	}

}
