package bpm.training.richard.gmusic2.client.dialog;

import gwt.material.design.client.custom.MaterialSuggestionOracle;
import gwt.material.design.client.ui.MaterialAutoComplete;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;

import java.util.List;

import bpm.training.richard.gmusic2.client.services.IUserService;
import bpm.training.richard.gmusic2.shared.Music;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ShareMusicDialog extends Composite {

	private static ShareMusicDialogUiBinder uiBinder = GWT
			.create(ShareMusicDialogUiBinder.class);

	interface ShareMusicDialogUiBinder extends
			UiBinder<Widget, ShareMusicDialog> {
	}
	
	@UiField MaterialAutoComplete autocomplete;
	private Music music;
	
	public ShareMusicDialog(Music music) {
		initWidget(uiBinder.createAndBindUi(this));
		this.music = music;
		initAutocomplete();
	}

	private void initAutocomplete() {
		MaterialLoader.showLoading(true);
		IUserService.Connect.getService().getAllUsers(new AsyncCallback<List<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				MaterialToast.alert(caught.getMessage());
				MaterialLoader.showLoading(false);
			}

			@Override
			public void onSuccess(List<User> result) {
				MaterialLoader.showLoading(false);
				MaterialSuggestionOracle suggestions = new MaterialSuggestionOracle();
				for(User user : result) {
					suggestions.add(user.getLoginId(), new Image(user.getProfilePhoto()));
					
				}
				autocomplete.setSuggestions(suggestions);
			}
		});
	}
	
	@UiHandler("btnShare")
	void onShare(ClickEvent e){
		MaterialLoader.showLoading(true);
		IUserService.Connect.getService().shareMusic(autocomplete.getItemValues(), music, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				MaterialToast.alert(caught.getMessage());
				MaterialLoader.showLoading(false);
				
			}

			@Override
			public void onSuccess(Void result) {
				MaterialLoader.showLoading(false);
				MaterialModal.closeModal();
			}
		});
	}
	
	@UiHandler("btnCancel")
	void onCancel(ClickEvent e){
		MaterialModal.closeModal();
	}
	

}
