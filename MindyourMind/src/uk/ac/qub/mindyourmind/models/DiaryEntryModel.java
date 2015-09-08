package uk.ac.qub.mindyourmind.models;

import java.util.Calendar;

public class DiaryEntryModel {
	
	private String title;
	private String content;
	private Calendar timeStamp;
	private DiaryMedia[] media;
	
	public DiaryEntryModel(String title, String content, Calendar timeStamp, DiaryMedia[] media) {
		this.title = title;
		this.content = content;
		this.timeStamp = timeStamp;
		this.media = media;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}

	public DiaryMedia[] getMedia() {
		return media;
	}

	public void setMedia(DiaryMedia[] media) {
		this.media = media;
	}
	
	
}
