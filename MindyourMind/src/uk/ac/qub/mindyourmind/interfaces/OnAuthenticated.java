package uk.ac.qub.mindyourmind.interfaces;

public interface OnAuthenticated {

	public void success(long userId, String university, String degreePathway, String universityEmail, int yearOfStudy);
}
