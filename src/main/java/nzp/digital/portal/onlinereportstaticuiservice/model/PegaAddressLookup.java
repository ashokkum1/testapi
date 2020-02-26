package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


public class PegaAddressLookup {
	private String pxResultCount;
	private List<AddressLookUpResult> pxResults;
	public String getPxResultCount() {
		return pxResultCount;
	}
	public void setPxResultCount(String pxResultCount) {
		this.pxResultCount = pxResultCount;
	}
	public List<AddressLookUpResult> getPxResults() {
		return pxResults;
	}
	public void setPxResults(List<AddressLookUpResult> pxResults) {
		this.pxResults = pxResults;
	}

}

