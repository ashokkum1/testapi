package nzp.digital.portal.onlinereportstaticuiservice.model;

public class Item {
	private boolean cannotFind;
	private SearchedItem search;
	private String manual;
	
	public boolean isCannotFind() {
		return cannotFind;
	}
	public void setCannotFind(boolean cannotFind) {
		this.cannotFind = cannotFind;
	}
	public SearchedItem getSearch() {
		return search;
	}
	public void setSearch(SearchedItem search) {
		this.search = search;
	}
	public String getManual() {
		return manual;
	}
	public void setManual(String manual) {
		this.manual = manual;
	}
}
