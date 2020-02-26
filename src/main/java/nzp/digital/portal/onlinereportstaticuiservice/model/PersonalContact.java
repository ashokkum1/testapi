package nzp.digital.portal.onlinereportstaticuiservice.model;

public class PersonalContact {
	private Person personalDetails;
	private ContactDetail contactDetails;
	private boolean wantsVictimSupport;
	
	
	public Person getPersonalDetails() {
		return personalDetails;
	}
	public void setPersonalDetails(Person personalDetails) {
		this.personalDetails = personalDetails;
	}
	public ContactDetail getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(ContactDetail contactDetails) {
		this.contactDetails = contactDetails;
	}
	public boolean isWantsVictimSupport() {
		return wantsVictimSupport;
	}
	public void setWantsVictimSupport(boolean wantsVictimSupport) {
		this.wantsVictimSupport = wantsVictimSupport;
	}	

}
