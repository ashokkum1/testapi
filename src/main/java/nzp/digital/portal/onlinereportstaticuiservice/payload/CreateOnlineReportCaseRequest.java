package nzp.digital.portal.onlinereportstaticuiservice.payload;

import javax.validation.constraints.NotNull;

public class CreateOnlineReportCaseRequest {
	@NotNull(message = "Not an emergency should not be null")
	private boolean notAnEmergency;

	public boolean isNotAnEmergency() {
		return notAnEmergency;
	}

	public void setNotAnEmergency(boolean notAnEmergency) {
		this.notAnEmergency = notAnEmergency;
	}

}
