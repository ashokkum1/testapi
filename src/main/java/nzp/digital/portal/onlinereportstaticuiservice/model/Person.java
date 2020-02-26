package nzp.digital.portal.onlinereportstaticuiservice.model;

public class Person {
	 private Name name;
	 private Name previousName;
	 private boolean hasPreviousName;
	 private String dateOfBirth;
	 private String gender;
	 private String driverLicence;
	 private String relation;
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Name getPreviousName() {
		return previousName;
	}
	public void setPreviousName(Name previousName) {
		this.previousName = previousName;
	}
	public boolean isHasPreviousName() {
		return hasPreviousName;
	}
	public void setHasPreviousName(boolean hasPreviousName) {
		this.hasPreviousName = hasPreviousName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}	
	public String getDriverLicence() {
		return driverLicence;
	}
	public void setDriverLicence(String driverLicence) {
		this.driverLicence = driverLicence;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}	 
	 
}
