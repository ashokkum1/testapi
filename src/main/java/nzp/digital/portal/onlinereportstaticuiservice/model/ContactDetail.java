package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class ContactDetail {
	 private AddressInfo address;
	 private String preferredContactMethod;
	 private List<Phone> phoneNumbers= new ArrayList<Phone>();
	 private List<String> emails= new ArrayList<String>();;
	 
	public String getPreferredContactMethod() {
		return preferredContactMethod;
	}
	public void setPreferredContactMethod(String preferredContactMethod) {
		this.preferredContactMethod = preferredContactMethod;
	}
	public AddressInfo getAddress() {
		return address;
	}
	public void setAddress(AddressInfo address) {
		this.address = address;
	}
	public List<Phone> getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<Phone> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	
	 

	 
	 
}
