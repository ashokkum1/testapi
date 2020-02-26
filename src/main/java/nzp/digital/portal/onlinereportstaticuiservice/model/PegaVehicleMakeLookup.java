package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.List;

public class PegaVehicleMakeLookup {
	private String pxResultCount;
	private List<VehicleMakeLookUpResult> pxResults;
	public String getPxResultCount() {
		return pxResultCount;
	}
	public void setPxResultCount(String pxResultCount) {
		this.pxResultCount = pxResultCount;
	}
	public List<VehicleMakeLookUpResult> getPxResults() {
		return pxResults;
	}
	public void setPxResults(List<VehicleMakeLookUpResult> pxResults) {
		this.pxResults = pxResults;
	}
}
