package bpm.training.richard.gmusic2.client.dialog;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import bpm.training.richard.gmusic2.client.page.HomePage;
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

public class AddNewUser extends Composite {

	private static AddNewUserUiBinder uiBinder = GWT
			.create(AddNewUserUiBinder.class);

	interface AddNewUserUiBinder extends UiBinder<Widget, AddNewUser> {
	}
	@UiField MaterialTextBox txtName, txtLoginId, txtPassword, txtProfilePhoto;
	private User user;

	public AddNewUser() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	@UiHandler("btnRegister")
	void onClickRegister(ClickEvent e) {
		boolean isValid = true;
		
		if (txtName.getText().isEmpty()) {
			isValid = false;
			txtName.setError("Please enter your name");
		}
		else{
			txtName.setSuccess("Accepted");
		}
		
		if (txtLoginId.getText().isEmpty()) {
			isValid = false;
			txtLoginId.setError("This field cannot be blank");
		}
		else{
			txtLoginId.setSuccess("");
		}
		
		if (txtPassword.getText().isEmpty()) {
			isValid = false;
			txtPassword.setError("Please enter a password");
		}
		else{
			txtName.setSuccess("Accepted");
		}
		
		if (isValid) {
			user = new User(txtName.getText(),txtLoginId.getText(), txtPassword.getText(), txtProfilePhoto.getText()); 
			
			//Call Async
			MaterialLoader.showLoading(true);
			IUserService.Connect.getService().addUser(user,new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					MaterialToast.alert(caught.getMessage());
					MaterialLoader.showLoading(false);
					
				}

				@Override
				public void onSuccess(Void result) {
					MaterialModal.closeModal();
					RootPanel.get().clear();
					RootPanel.get().add(new HomePage(user));
				}
				
			});
		}
	}
	
	@UiHandler("btnCancel")
	void onClickCancel(ClickEvent e) {
		MaterialModal.closeModal();
	}
}
