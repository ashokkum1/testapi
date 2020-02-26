package nzp.digital.portal.onlinereportstaticuiservice.model;

public class TowingApproval {
	private boolean selfTowingApproval;
	private boolean someonesTowingApproval;
	public boolean isSelfTowingApproval() {
		return selfTowingApproval;
	}
	public void setSelfTowingApproval(boolean selfTowingApproval) {
		this.selfTowingApproval = selfTowingApproval;
	}
	public boolean isSomeonesTowingApproval() {
		return someonesTowingApproval;
	}
	public void setSomeonesTowingApproval(boolean someonesTowingApproval) {
		this.someonesTowingApproval = someonesTowingApproval;
	}

}
