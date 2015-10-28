/**
 * 
 */
package bpm.training.richard.gmusic2.client.dialog;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import bpm.training.richard.gmusic2.client.page.HomePage;
import bpm.training.richard.gmusic2.client.services.IMusicService;
import bpm.training.richard.gmusic2.shared.Music;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DeleteMusicDialog extends Composite {

	private static DeleteMusicDialogUiBinder uiBinder = GWT
			.create(DeleteMusicDialogUiBinder.class);

	interface DeleteMusicDialogUiBinder extends
			UiBinder<Widget, DeleteMusicDialog> {
	}
	
	private HomePage homepage;
	private Music music;
	public DeleteMusicDialog(Music music, HomePage homepage) {
		initWidget(uiBinder.createAndBindUi(this));
		this.music = music;
		this.homepage = homepage;
	}
	
	@UiHandler("btnYes")
	void onConfirmYes(ClickEvent e){
		MaterialLoader.showLoading(true);
		IMusicService.Connect.getService().deleteMusic(music,new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				MaterialToast.alert(caught.getMessage());
				MaterialLoader.showLoading(false);
			}

			@Override
			public void onSuccess(Void result) {
				MaterialLoader.showLoading(false);
				MaterialModal.closeModal();
				homepage.onGetMusic();
			}
		}); 
	}
	
	@UiHandler("btnNo")
	void onConfirmNo(ClickEvent e){
		MaterialModal.closeModal();
	}
	
	

}
