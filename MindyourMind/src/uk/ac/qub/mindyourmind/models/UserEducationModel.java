package uk.ac.qub.mindyourmind.models;

public class UserEducationModel {

	private String institutionName;
	private String faculty;
	
	public UserEducationModel(String institutionName, String faculty) {
		this.institutionName = institutionName;
		this.faculty = faculty;
	}
	
	
	public String getInstitution() {
		return institutionName;
	}
	public void setInstitution(String institutionName) {
		this.institutionName = institutionName;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	
}
