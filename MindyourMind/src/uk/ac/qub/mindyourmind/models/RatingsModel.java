package uk.ac.qub.mindyourmind.models;

import java.util.Calendar;

public class RatingsModel {
	
	private ratingTypes ratingType;
	private int ratingValue;
	private Calendar timeStamp;
	
	public static enum ratingTypes{
		Happiness, Satisfaction;
	}
	
	public RatingsModel() {
	}
	
	public RatingsModel(ratingTypes ratingType, int ratingValue, Calendar timeStamp) {
		this.ratingType = ratingType;
		this.ratingValue = ratingValue;
		this.timeStamp = timeStamp;
	}

	public ratingTypes getRatingType() {
		return ratingType;
	}

	public void setRatingType(ratingTypes ratingType) {
		this.ratingType = ratingType;
	}

	public int getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}
	}


