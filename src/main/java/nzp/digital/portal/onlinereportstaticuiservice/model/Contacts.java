package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class Contacts {
	 private PersonalContact reporter;	 
	 private List<PersonalContact> otherPeople = new ArrayList<PersonalContact>();
	 private List<OrganisationContact> organisations = new ArrayList<OrganisationContact>();
	
	public PersonalContact getReporter() {
		return reporter;
	}
	public void setReporter(PersonalContact reporter) {
		this.reporter = reporter;
	}
	public List<PersonalContact> getOtherPeople() {
		return otherPeople;
	}
	public void setOtherPeople(List<PersonalContact> otherPeople) {
		this.otherPeople = otherPeople;
	}
	public List<OrganisationContact> getOrganisations() {
		return organisations;
	}
	public void setOrganisations(List<OrganisationContact> organisations) {
		this.organisations = organisations;
	}
	
	 
}
