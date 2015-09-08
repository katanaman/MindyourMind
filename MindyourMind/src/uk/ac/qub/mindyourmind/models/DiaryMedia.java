package uk.ac.qub.mindyourmind.models;

import android.media.Image;
import android.net.Uri;

public class DiaryMedia {

	private Uri uri;
	private Image[] images;
	
	public DiaryMedia(Uri uri, Image[] images) {
		this.uri = uri;
		this.setImages(images);
	}
	
	public Uri getUri() {
		return uri;
	}
	public void setUri(Uri uri) {
		this.uri = uri;
	}

	public Image[] getImages() {
		return images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}
}
