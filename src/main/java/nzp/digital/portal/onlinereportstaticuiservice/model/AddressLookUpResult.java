package nzp.digital.portal.onlinereportstaticuiservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


public class AddressLookUpResult {
	private String pyLabel;
	private String NIAID;
	public String getPyLabel() {
		return pyLabel;
	}
	public void setPyLabel(String pyLabel) {
		this.pyLabel = pyLabel;
	}
	public String getNIAID() {
		return NIAID;
	}
	public void setNIAID(String nIAID) {
		NIAID = nIAID;
	}	

}
