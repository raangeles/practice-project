/*package bpm.training.richard.gmusic2.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bpm.training.richard.gmusic2.client.services.IMusicService;
import bpm.training.richard.gmusic2.shared.Music;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

//Step 2: Create a class that extends RemoteServiceServlet and implements the interface you created in step 1
public class MusicServiceImplFile extends RemoteServiceServlet implements
		IMusicService {

	private static final long serialVersionUID = 1L;

	// implement the interface you created(service)
	public void addMusic(Music music) throws Exception {
		if (music.getTitle().equals("puta")) {
			throw new Exception("Dont say bad words");
		} else {

			try {
				File log = new File("log.csv");
				BufferedWriter writer = new BufferedWriter(new FileWriter(log,
						true));
				writer.write(musicFormat(music));
				writer.close();
			}

			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void deleteMusic(Music music) throws Exception {

		File logFile = new File("log.csv");
		String toDelete = musicFormat(music);
		List<String> temp = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(logFile))) { // instantiate BufferReader br that takes the file "log.csv"
			for (String line; (line = br.readLine()) != null;) { // read line by line
				temp.add(line);
			}
		}

		// Write the file overwrite it
		File log = new File("log.csv");
		BufferedWriter writer = new BufferedWriter(new FileWriter(log, false));
		StringBuffer buff = new StringBuffer();
		for (String file : temp) {
			if (!file.equals(toDelete.replace("\n", ""))) {
				buff.append(file + "\n");
			}
		}
		writer.write(buff.toString());
		writer.close();

	}
	
	This method will update the music information
	 * 
	 
	public void updateMusic(Music music, Music updateMusic) throws Exception {
		File log = new File("log.csv");
		String toUpdate = musicFormat(music);
		StringBuffer buff = new StringBuffer();
		List<String> temp = new ArrayList<String>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(log))) {
			
			for (String line; (line = reader.readLine()) != null;) {
				temp.add(line);
			}
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(log, false));
		for(String file : temp) {
			if (!file.equals(toUpdate.replace("\n",""))) {
				buff.append(file +"\n");
			}
			else {
				buff.append(musicFormat(updateMusic));
			}
		}
		writer.write(buff.toString());
		writer.close();
	}

	private String musicFormat(Music music) {
		String musicInfo = music.getTitle() + ";" + music.getDescription() + ";" + music.getArtist() + ";" + music.getPicture() + "\n";
		return musicInfo;
	}

	public List<Music> getAllMusic() throws Exception {
		List<Music> musics = new ArrayList<Music>();

		String file = "log.csv";
		// Write the data input to file
		try (BufferedReader br = new BufferedReader(new FileReader(file))) { // instantiate BufferReader br that takes the file "log.csv"
			
			for (String line; (line = br.readLine()) != null;) { // read line by
																	// line
				String[] values = line.split(";"); // store the data in a String
													// array called values, with
													// the ';' char as the
													// delimiter.
				Music music = new Music(values[0], values[1], values[2],
						values[3]);
				musics.add(music); // add to the list
			}
		}
		return musics;
	}

	public List<Music> getAllRecentMusic() throws Exception {
		List<Music> music = new ArrayList<Music>();
		Music recent1 = new Music(
				"Recent 1",
				"Your recent music 1",
				"Artist 1",
				"https://d13yacurqjgara.cloudfront.net/users/299266/screenshots/1871250/materialme_1x.jpg");
		Music recent2 = new Music(
				"Recent 2",
				"Your recent music 2",
				"Artist 2",
				"https://d13yacurqjgara.cloudfront.net/users/299266/screenshots/1871250/materialme_1x.jpg");
		Music recent3 = new Music(
				"Recent 3",
				"Your recent music 3",
				"Artist 3",
				"https://d13yacurqjgara.cloudfront.net/users/299266/screenshots/1871250/materialme_1x.jpg");
		Music recent4 = new Music(
				"Recent 4",
				"Your recent music 4",
				"Artist 4",
				"https://d13yacurqjgara.cloudfront.net/users/299266/screenshots/1871250/materialme_1x.jpg");
		music.add(recent1);
		music.add(recent2);
		music.add(recent3);
		music.add(recent4);

		return music;
	}
	
Search
 * 
 
	public List<Music> searchMusic(String keyword)throws Exception{
		List<Music> musics = new ArrayList<Music>();
		
		File log = new File("log.csv");
		
		try (BufferedReader reader = new BufferedReader(new FileReader(log))) {
			
			for (String line; (line = reader.readLine()) != null;) {
				String[] result = line.split(";");
				String title = result[0]; 
				String author = result[2];
				String description = result[1];
				String picture = result[3];
				
				boolean multipleHits = (title.contains(keyword)) || (author.contains(keyword) || (description.contains(keyword)));
				
				if (multipleHits) {
					musics.add(new Music(title, description, author, picture));
				}
			}
		}
		return musics;
		
	}

}
*/