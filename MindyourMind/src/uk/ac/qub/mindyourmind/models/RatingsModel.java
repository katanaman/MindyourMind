package uk.ac.qub.mindyourmind.models;

import java.util.Calendar;

public class RatingsModel {

	private RatingTypes ratingType;
	private int ratingValue;
	private Calendar timeStamp;

	public static enum RatingTypes{
		HAPPINESS, SATISFACTION;
	}

	public RatingsModel() {
	}

	public RatingsModel(RatingTypes ratingType) {
		this.ratingType = ratingType;
	}

	public RatingsModel(RatingTypes ratingType, int ratingValue, Calendar timeStamp) {
		this.ratingType = ratingType;
		this.ratingValue = ratingValue;
		this.timeStamp = timeStamp;
	}

	public RatingTypes getRatingType() {
		return ratingType;
	}

	public void setRatingType(RatingTypes ratingType) {
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


