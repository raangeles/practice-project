package bpm.training.richard.gmusic2.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface IProjectResources extends ClientBundle {
	public static final IProjectResources INSTANCE = GWT.create(IProjectResources.class);
	
	ImageResource ic_gplay_music_logo();
	ImageResource ic_profile_photo();
	ImageResource ic_profile_photo2();
}
