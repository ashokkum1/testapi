package nzp.digital.portal.onlinereportstaticuiservice.model;

public class WhoWasInvolved {
	private boolean knowsWho;
	private Suspect who;
	private boolean knowsDescription;
	private String description;
	private boolean knowsVehicle;
	private OffenderVehicle vehicle;
	private boolean knowsContact;
	private String contact;
	
	public boolean isKnowsWho() {
		return knowsWho;
	}
	public void setKnowsWho(boolean knowsWho) {
		this.knowsWho = knowsWho;
	}
	public Suspect getWho() {
		return who;
	}
	public void setWho(Suspect who) {
		this.who = who;
	}
	public boolean isKnowsDescription() {
		return knowsDescription;
	}
	public void setKnowsDescription(boolean knowsDescription) {
		this.knowsDescription = knowsDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isKnowsVehicle() {
		return knowsVehicle;
	}
	public void setKnowsVehicle(boolean knowsVehicle) {
		this.knowsVehicle = knowsVehicle;
	}
	public OffenderVehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(OffenderVehicle vehicle) {
		this.vehicle = vehicle;
	}
	public boolean isKnowsContact() {
		return knowsContact;
	}
	public void setKnowsContact(boolean knowsContact) {
		this.knowsContact = knowsContact;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	

}
