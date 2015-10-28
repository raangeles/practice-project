package bpm.training.richard.gmusic2.client.dialog;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import bpm.training.richard.gmusic2.client.page.HomePage;
import bpm.training.richard.gmusic2.client.services.IMusicService;
import bpm.training.richard.gmusic2.shared.Music;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AddMusicDialog extends Composite {

	private static AddMusicDialogUiBinder uiBinder = GWT
			.create(AddMusicDialogUiBinder.class);

	interface AddMusicDialogUiBinder extends UiBinder<Widget, AddMusicDialog> {
	}

	@UiField MaterialTextBox txtTitle, txtAuthor, txtPicture;
	@UiField MaterialTextArea txtDescription;
	@UiField MaterialTitle modalTitle;
	@UiField MaterialButton btnAdd;
	
	private HomePage homePage;
	private Music music;
	
	/**
	 * For new musics
	 * @param homePage
	 */
	public AddMusicDialog(HomePage homePage) {
		initWidget(uiBinder.createAndBindUi(this));
		this.homePage = homePage;
	}
	
	/**
	 * For music updates
	 * @param homePage
	 * @param music - this is the music you want to update
	 */
	public AddMusicDialog(HomePage homePage, Music music) {
		initWidget(uiBinder.createAndBindUi(this));
		this.homePage = homePage;
		this.setMusic(music);
		//SET TITLE
		modalTitle.setTitle("Update Music");
		modalTitle.setDescription("Update your music information");
		//SET FIELDS
		txtTitle.setText(music.getTitle());
		txtDescription.setText(music.getDescription());
		txtAuthor.setText(music.getArtist());
		txtPicture.setText(music.getPicture());
		btnAdd.setText("Update");
	}
	
	@UiHandler("btnAdd")
	void onAddMusic(ClickEvent e){
		boolean isValid = true;
		if (txtTitle.getText().isEmpty()) {
			txtTitle.setError("Please enter your song title");
			isValid = false;
		}else{
			txtTitle.setSuccess("Correct");
		}
		
		if (txtAuthor.getText().isEmpty()) {
			txtAuthor.setError("Please enter your song title");
			isValid = false;
		}else{
			txtAuthor.setSuccess("Correct");
		}
		
		if (txtPicture.getText().isEmpty()) {
			txtPicture.setError("Please enter your song title");
			isValid = false;
		}else{
			txtPicture.setSuccess("Correct");
		}
		
		
		if(isValid){
			// INSTANTIATE A MUSIC CLASS TO STORE THE VALUES ON EACH FIELDS
			if(music==null){
				music = new Music(txtTitle.getText(), txtDescription.getText(), txtAuthor.getText(), txtPicture.getText(), homePage.getUser().getId());
				//CALL ASYNC TASK
				MaterialLoader.showLoading(true);
				IMusicService.Connect.getService().addMusic(music, new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						MaterialToast.alert(caught.getMessage());
						MaterialLoader.showLoading(false);
					}

					public void onSuccess(Void result) {
						// USE HOMEPAGE ROW TO POPULATE THE CARD
						homePage.onGetMusic();
						MaterialModal.closeModal();
						MaterialLoader.showLoading(false);
					}
				});
			}
			
			else{
				//UPDATE THE MUSIC USING ASYNC
				MaterialLoader.showLoading(true);
				music.setTitle(txtTitle.getText());
				music.setArtist(txtAuthor.getText());
				music.setDescription(txtDescription.getText());
				music.setPicture(txtPicture.getText());
				IMusicService.Connect.getService().updateMusic(music, new AsyncCallback<Void>() {
					
					public void onFailure(Throwable caught) {
						MaterialToast.alert(caught.getMessage());
						MaterialLoader.showLoading(false);
					}

					public void onSuccess(Void result) {
						// USE HOMWPAGE ROW TO POPULATE THE CARD
						homePage.onGetMusic();
						MaterialModal.closeModal();
						MaterialLoader.showLoading(false);
					}
				});				
			}
			
		}
		
	}
	
	@UiHandler("btnCancel")
	void onCancel(ClickEvent e){
		MaterialModal.closeModal();
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
	

}
