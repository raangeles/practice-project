package bpm.training.richard.gmusic2.client;

import bpm.training.richard.gmusic2.client.page.LoginPage;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Bpm_training_richard_gmusic2 implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get().add(new LoginPage());
	}
}
