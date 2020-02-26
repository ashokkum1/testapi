package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class Organisation {
	private String orgName;
	private String orgDescription;
	//private List<Phone> contactPhone = new ArrayList<Phone>();

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}	

	public String getOrgDescription() {
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}
}
