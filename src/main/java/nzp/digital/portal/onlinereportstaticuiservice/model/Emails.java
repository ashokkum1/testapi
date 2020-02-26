package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class Emails {

	private String primary;
	private List<String> others = new ArrayList<String>();
	
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	public List<String> getOthers() {
		return others;
	}
	public void setOthers(List<String> others) {
		this.others = others;
	}
	
}
