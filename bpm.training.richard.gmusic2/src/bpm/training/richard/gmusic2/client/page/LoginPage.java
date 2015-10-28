package bpm.training.richard.gmusic2.client.page;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import bpm.training.richard.gmusic2.client.dialog.AddNewUser;
import bpm.training.richard.gmusic2.client.services.IUserService;
import bpm.training.richard.gmusic2.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginPage extends Composite {

	private static LoginPageUiBinder uiBinder = GWT
			.create(LoginPageUiBinder.class);

	interface LoginPageUiBinder extends UiBinder<Widget, LoginPage> {
	}

	@UiField MaterialTextBox txtEmail, txtPassword;
	@UiField MaterialTitle lblLogin;
	
	public LoginPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("btnLogin")
	void onClickLogin(ClickEvent e) {
		IUserService.Connect.getService().loginUser(new User(txtEmail.getText(), txtPassword.getText()), new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				MaterialToast.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(User result) {
				RootPanel.get().clear();
				RootPanel.get().add(new HomePage(result));
			}
		});
	}
	@UiHandler("txtRegister")
	void onClickRegister(ClickEvent e) {
		MaterialModal.showModal(new AddNewUser(),TYPE.FIXED_FOOTER, false);
	}
	
}
