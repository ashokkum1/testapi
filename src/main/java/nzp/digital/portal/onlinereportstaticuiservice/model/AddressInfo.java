package nzp.digital.portal.onlinereportstaticuiservice.model;

public class AddressInfo {
	
	private boolean cannotFind;
	private String search;
	private Address manual;
	private String type;
	
	public boolean isCannotFind() {
		return cannotFind;
	}
	public void setCannotFind(boolean cannotFind) {
		this.cannotFind = cannotFind;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Address getManual() {
		return manual;
	}
	public void setManual(Address manual) {
		this.manual = manual;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
		

}
