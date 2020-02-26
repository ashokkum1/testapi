package nzp.digital.portal.onlinereportstaticuiservice.model;

public class Witness {
	private boolean witnessIndicator;
	private String description;
	public boolean isWitnessIndicator() {
		return witnessIndicator;
	}
	public void setWitnessIndicator(boolean witnessIndicator) {
		this.witnessIndicator = witnessIndicator;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
