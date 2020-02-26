package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumbers {
	private Phone primary;	
	private List<Phone> others = new ArrayList<Phone>();
	
	public Phone getPrimary() {
		return primary;
	}
	public void setPrimary(Phone primary) {
		this.primary = primary;
	}
	public List<Phone> getOthers() {
		return others;
	}
	public void setOthers(List<Phone> others) {
		this.others = others;
	}
}
