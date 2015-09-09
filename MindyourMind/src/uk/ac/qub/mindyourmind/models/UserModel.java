package uk.ac.qub.mindyourmind.models;

public class UserModel {
	
	private String userID;
	private String emailAddress;
	private String name;
	private String dateOfBirth;
	private int yearOfStudy;
	private String gender;
	private String code;
	private String userPassword;
	private boolean isAdmin;
	
	public UserModel(){
	}
	
	public UserModel(String userID, String emailAddress){
		this.userID = userID;
		this.emailAddress = emailAddress;
	}
	
	public UserModel(String userID, String emailAddress, String name, String dateOfBirth, int yearOfStudy,
			String gender, String code, String userPassword) {
		super();
		this.userID = userID;
		this.emailAddress = emailAddress;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.yearOfStudy = yearOfStudy;
		this.gender = gender;
		this.code = code;
		this.userPassword = userPassword;
	}
	
	
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getYearOfStudy() {
		return yearOfStudy;
	}
	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}	
	public boolean isAdmin() {
		return isAdmin;
	}
	public void isAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}	
}
