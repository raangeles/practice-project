package bpm.training.richard.gmusic2.client.card;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import bpm.training.richard.gmusic2.client.dialog.AddMusicDialog;
import bpm.training.richard.gmusic2.client.dialog.DeleteMusicDialog;
import bpm.training.richard.gmusic2.client.dialog.ShareMusicDialog;
import bpm.training.richard.gmusic2.client.page.HomePage;
import bpm.training.richard.gmusic2.shared.Music;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MusicCard extends Composite  {

	private static MusicCardUiBinder uiBinder = GWT
			.create(MusicCardUiBinder.class);

	interface MusicCardUiBinder extends UiBinder<Widget, MusicCard> {
	}
	
	private HomePage homepage;
	private Music music;
	
	@UiField MaterialCard cardMusic;
	@UiField MaterialLink btnPlay;
	
	public MusicCard(Music music, HomePage homepage) {
		initWidget(uiBinder.createAndBindUi(this));
		this.homepage = homepage;
		this.music = music;
		cardMusic.setUrl(music.getPicture());
		cardMusic.setTitle(music.getTitle() + "-" + music.getArtist());
		cardMusic.setDescription(music.getDescription());
		cardMusic.setType("reveal");
		cardMusic.setGrid("s12 m12 l3");
		cardMusic.setImageHeight("25");
	}
	
	@UiHandler("btnPlay")
	void onPlay(ClickEvent e) {
		homepage.playMusic(music);
		boolean isPlay = btnPlay.getText().equals("Play");
		
		//RESET ALL FIELDS 
		for(Widget w : homepage.getRowMusic()){
			if(w instanceof MusicCard){
				((MusicCard) w).getBtnPlay().setText("Play");
			}
		}

		if (isPlay) {
			btnPlay.setText("Pause");
			homepage.getBtnPlay().setIcon("mdi-av-pause");
		}
		else {
			btnPlay.setText("Play");
			homepage.getBtnPlay().setIcon("mdi-av-play-arrow");
		}		
		homepage.onPressPlayPause(e);
	}
	
	@UiHandler("btnUpdate")
	void onUpdate(ClickEvent e){
		MaterialModal.showModal(new AddMusicDialog(homepage, music), TYPE.FIXED_FOOTER, true);
	}
	
	@UiHandler("btnDelete")
	void onDelete(ClickEvent e){
		MaterialModal.showModal(new DeleteMusicDialog(music, homepage), TYPE.BOTTOM_SHEET , true);
	}

	public MaterialLink getBtnPlay() {
		return btnPlay;
	}

	public void setBtnPlay(MaterialLink btnPlay) {
		this.btnPlay = btnPlay;
	}
	
	@UiHandler("btnShare")
	void onShare(ClickEvent e) {
		MaterialModal.showModal(new ShareMusicDialog(music), TYPE.FIXED_FOOTER, true);
	}
	

}
