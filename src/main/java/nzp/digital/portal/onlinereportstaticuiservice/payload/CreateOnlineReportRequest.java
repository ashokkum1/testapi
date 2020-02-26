package nzp.digital.portal.onlinereportstaticuiservice.payload;

import nzp.digital.portal.onlinereportstaticuiservice.model.ContactDetail;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhenItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhereItHappened;

public class CreateOnlineReportRequest {
	 private WhenItHappened whenHappened;
	 private WhereItHappened whereHappened;
	 private String whatHappened;
	 private String additionalInformation;
	 private ContactDetail contactDetail;


	 // Getter Methods 

	 public WhenItHappened getWhenHappened() {
	  return whenHappened;
	 }

	 public WhereItHappened getWhereHappened() {
	  return whereHappened;
	 }

	 public String getWhatHappened() {
	  return whatHappened;
	 }

	 public String getAdditionalInformation() {
	  return additionalInformation;
	 }

	 public ContactDetail getContactDetail() {
	  return contactDetail;
	 }

	 // Setter Methods 

	 public void setWhenHappened(WhenItHappened whenHappened) {
	  this.whenHappened = whenHappened;
	 }

	 public void setWhereHappened(WhereItHappened whereHappened) {
	  this.whereHappened = whereHappened;
	 }

	 public void setWhatHappened(String whatHappened) {
	  this.whatHappened = whatHappened;
	 }

	 public void setAdditionalInformation(String additionalInformation) {
	  this.additionalInformation = additionalInformation;
	 }

	 public void setContactDetail(ContactDetail contactDetail) {
	  this.contactDetail = contactDetail;
	 }
}
