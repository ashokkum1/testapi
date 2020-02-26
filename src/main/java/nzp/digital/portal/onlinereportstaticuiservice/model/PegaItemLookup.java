package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.List;

public class PegaItemLookup {
	private String pxResultCount;
	private List<ItemLookUpResult> pxResults;
	public String getPxResultCount() {
		return pxResultCount;
	}
	public void setPxResultCount(String pxResultCount) {
		this.pxResultCount = pxResultCount;
	}
	public List<ItemLookUpResult> getPxResults() {
		return pxResults;
	}
	public void setPxResults(List<ItemLookUpResult> pxResults) {
		this.pxResults = pxResults;
	}
	
}
