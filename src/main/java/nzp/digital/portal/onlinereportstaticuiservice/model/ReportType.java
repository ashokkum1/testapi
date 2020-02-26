package nzp.digital.portal.onlinereportstaticuiservice.model;

public class ReportType {
	private boolean someoneHurt;
	private boolean somethingDamaged;
	private boolean somethingStolen;
	private boolean somethingLost;
	private boolean somethingElse;
	
	
	public boolean isSomeoneHurt() {
		return someoneHurt;
	}
	public void setSomeoneHurt(boolean someoneHurt) {
		this.someoneHurt = someoneHurt;
	}
	public boolean isSomethingDamaged() {
		return somethingDamaged;
	}
	public void setSomethingDamaged(boolean somethingDamaged) {
		this.somethingDamaged = somethingDamaged;
	}
	public boolean isSomethingStolen() {
		return somethingStolen;
	}
	public void setSomethingStolen(boolean somethingStolen) {
		this.somethingStolen = somethingStolen;
	}
	public boolean isSomethingLost() {
		return somethingLost;
	}
	public void setSomethingLost(boolean somethingLost) {
		this.somethingLost = somethingLost;
	}
	public boolean isSomethingElse() {
		return somethingElse;
	}
	public void setSomethingElse(boolean somethingElse) {
		this.somethingElse = somethingElse;
	}
}
