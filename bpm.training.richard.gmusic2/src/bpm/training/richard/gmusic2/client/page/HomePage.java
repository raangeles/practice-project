package bpm.training.richard.gmusic2.client.page;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.animate.MaterialAnimator;
import gwt.material.design.client.ui.animate.Transition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bpm.training.richard.gmusic2.client.card.MusicCard;
import bpm.training.richard.gmusic2.client.content.ProfileContent;
import bpm.training.richard.gmusic2.client.dialog.AddMusicDialog;
import bpm.training.richard.gmusic2.client.panel.MusicListTable;
import bpm.training.richard.gmusic2.client.services.IMusicService;
import bpm.training.richard.gmusic2.client.services.IUserService;
import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class HomePage extends Composite {

	private static HomePageUiBinder uiBinder = GWT
			.create(HomePageUiBinder.class);

	interface HomePageUiBinder extends UiBinder<Widget, HomePage> {
	}

	private ArrayList<Music> currentMusicCards = new ArrayList<Music>();
	 
	@UiField MaterialRow rowMusic, rowShared; 
	@UiField MaterialTitle lblUser;
	@UiField MaterialLabel txtTitle, txtArtist;
	@UiField MaterialImage imgMusic;
	@UiField MaterialIcon btnPlay;
	@UiField MaterialSearch txtSearch;
	@UiField MaterialLink sideBarProfile, sidebarExplore;
	@UiField MaterialContainer mainCon;
	@UiField MaterialPanel homeContent, sharedPanel;
	
	private User user;
	
	public HomePage(User user) {
		initWidget(uiBinder.createAndBindUi(this));
		IUserService.Connect.getService().getUserFromSession(new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				MaterialLoader.showLoading(false);
				MaterialToast.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(User result) {
				MaterialLoader.showLoading(false);
				lblUser.setTitle("Hi " + result.getName() +  "your user id is: " + result.getId());
			}
		});
		
		this.setUser(user);
		onGetMusic();
		onGetRecentMusic();
	}


	@UiHandler("btnAddMusic")
	void onAddMusic(ClickEvent e){
		MaterialModal.showModal(new AddMusicDialog(this), TYPE.FIXED_FOOTER, true);
	}
		
	/*Search the record/ file with the keyword entered on the search bar.
	 * 
	 */
	@UiHandler("txtSearch")
	void onSearch(KeyUpEvent k) {
		if(!txtSearch.getText().isEmpty()){
			MaterialLoader.showLoading(true);
			IMusicService.Connect.getService().searchMusic(txtSearch.getText(),new AsyncCallback<List<Music>>() {
	
				@Override
				public void onFailure(Throwable caught) {
					MaterialLoader.showLoading(false);
					MaterialToast.alert(caught.getMessage());
				}
	
				@Override
				public void onSuccess(List<Music> result) {
					MaterialLoader.showLoading(false);
					rowMusic.clear();
					currentMusicCards.clear();
					for(Music m : result){
						getRowMusic().add(buildMusicCard(m));
						currentMusicCards.add(m);
					}
					MaterialAnimator.animate(Transition.SHOW_GRID, rowMusic, 0);
				}
			});
		}
		else {
			onGetMusic();
		}
		
	}
	
	/** Sets the Play Button to Pause when clicked and vice-versa
	 * 
	 * @param e
	 */
	
	/*Sorting methods
	 * 
	 */
	
	@UiHandler("btnSortByTitle")
	public void onSortByTitle(ClickEvent e) {
		Collections.sort(currentMusicCards, compareByTitle);
		populateRowMusic(currentMusicCards);
	}
	
	@UiHandler("btnSortByArtist")
	public void onSortByArtist(ClickEvent e) {
		Collections.sort(currentMusicCards, compareByArtist);
		populateRowMusic(currentMusicCards);
	}
	
	@UiHandler("btnSortByDescription")
	public void onSortByDescription(ClickEvent e) {
		Collections.sort(currentMusicCards, compareByDescription);
		populateRowMusic(currentMusicCards);
	}
	
/** Populate Row rowMusic with cards
 * 
 */
	private void populateRowMusic(ArrayList<Music> list) {
		rowMusic.clear();
		for(Music m : list){
			getRowMusic().add(buildMusicCard(m));
		}
		MaterialAnimator.animate(Transition.SHOW_GRID, rowMusic, 0);
	}
	
	@UiHandler("sideBarListenNow")
	void onClickListenNow(ClickEvent e) {
		mainCon.clear();
		mainCon.add(homeContent);
		onGetMusic();
		mainCon.add(sharedPanel);
		onGetRecentMusic();
	}
	
	@UiHandler("sideBarProfile")
	void onClickProfile(ClickEvent e) {
		mainCon.clear();
		mainCon.add(new ProfileContent(this));
	}
	
	@UiHandler("sidebarExplore")
	void onClickExplore(ClickEvent e) {
		mainCon.clear();
		mainCon.add(new MusicListTable(this));
		
	}
	@UiHandler("btnPlay")
	public void onPressPlayPause(ClickEvent e){
		boolean isPlay = btnPlay.getIcon().equals("mdi-av-play-arrow");
		if (isPlay) {
			btnPlay.setIcon("mdi-av-pause");
		}
		else {
			btnPlay.setIcon("mdi-av-play-arrow");
		}
	}
	
	/**
	 * Get all music and populate it on the row music panel
	 */
	
	public void onGetMusic() {
		
		MaterialLoader.showLoading(true); //show loader when loading
		IMusicService.Connect.getService().getAllMusicByUser(new AsyncCallback<List<Music>>() { //connect to the service
			
			public void onSuccess(List<Music> result) {
	
				getRowMusic().clear();
				currentMusicCards.clear();
				for(Music music : result){
					// BUILD YOUR VIEW / CARD
					// USE HOMEPAGE ROW TO POPULATE THE CARD
					getRowMusic().add(buildMusicCard(music));
					currentMusicCards.add(music);
				}
				MaterialLoader.showLoading(false);
				MaterialAnimator.animate(Transition.SHOW_GRID, rowMusic, 0);
			}
			
			public void onFailure(Throwable caught) {
				MaterialLoader.showLoading(false);
				MaterialToast.alert(caught.getMessage());
			}
		});
	}
	
	private void onGetRecentMusic() {
		MaterialLoader.showLoading(true);
		IMusicService.Connect.getService().getAllSharedMusic(new AsyncCallback<List<Music>>() {

			public void onSuccess(List<Music> result) {
				MaterialLoader.showLoading(false);
				
				for(Music music : result){
					rowShared.add(buildMusicCard(music));
				}
			}
			
			public void onFailure(Throwable caught) {
				MaterialLoader.showLoading(false);
				MaterialToast.alert(caught.getMessage());
			}
		});
	}
	
	@UiHandler("btnLogOut")
	void onClickLogOut(ClickEvent e) {
		MaterialLoader.showLoading(true);
		IUserService.Connect.getService().logOutUser(new AsyncCallback<Void>()  {

			public void onFailure(Throwable caught) {
				MaterialLoader.showLoading(false);
				MaterialToast.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				MaterialLoader.showLoading(false);
				RootPanel.get().clear();
				RootPanel.get().add(new LoginPage());
			}
		});
	}
	
	/**
	 * This method will build a card for music object
	 * @param music
	 * @return music card
	 */
	public MusicCard buildMusicCard(Music music){
		return new MusicCard(music, this);
	}
	/**
	 * Sets the title, artist and album art to the now playing section
	 * @param music
	 */
	public void playMusic(Music music) {
		imgMusic.setUrl(music.getPicture());
		txtTitle.setText(music.getTitle());
		txtArtist.setText(music.getArtist());
	}
	
	/*Comparators used
	 * 
	 */
	private static Comparator<Music> compareByTitle = new Comparator<Music>() {
		@Override
		public int compare(Music o1, Music o2) {
			return o1.getTitle().compareToIgnoreCase(o2.getTitle());
		}
	};
	
	private static Comparator<Music> compareByArtist = new Comparator<Music>() {
		@Override
		public int compare(Music o1, Music o2) {
			return o1.getArtist().compareToIgnoreCase(o2.getArtist());
		}
	};
	private static Comparator<Music> compareByDescription = new Comparator<Music>() {
		@Override
		public int compare(Music o1, Music o2) {
			return o1.getDescription().compareToIgnoreCase(o2.getDescription());
		}
	};
	
	public MaterialRow getRowMusic() {
		return rowMusic;
	}

	public void setRowMusic(MaterialRow rowMusic) {
		this.rowMusic = rowMusic;
	}

	public MaterialIcon getBtnPlay() {
		return btnPlay;
	}

	public void setBtnPlay(MaterialIcon btnPlay) {
		this.btnPlay = btnPlay;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
}