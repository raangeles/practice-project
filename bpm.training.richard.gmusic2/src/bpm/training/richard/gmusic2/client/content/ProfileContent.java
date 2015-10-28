package bpm.training.richard.gmusic2.client.content;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;
import bpm.training.richard.gmusic2.client.page.HomePage;
import bpm.training.richard.gmusic2.client.services.IUserService;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ProfileContent extends Composite implements HasText {

	private static ProfileContentUiBinder uiBinder = GWT
			.create(ProfileContentUiBinder.class);

	interface ProfileContentUiBinder extends UiBinder<Widget, ProfileContent> {
	}
	
	@UiField Button button;
	
	String user;

	public ProfileContent() {
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
				user = result.getName();
				button.setText(user);
			}
		});
	}

	public ProfileContent(HomePage homePage) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello " + user );
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
