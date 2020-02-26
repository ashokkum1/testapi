package nzp.digital.portal.onlinereportstaticuiservice.model;

public class Owner {
	private String type;
	private int index;
	private Name person;
	private String organisationName;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Name getPerson() {
		return person;
	}
	public void setPerson(Name person) {
		this.person = person;
	}
	public String getOrganisationName() {
		return organisationName;
	}
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	
}
