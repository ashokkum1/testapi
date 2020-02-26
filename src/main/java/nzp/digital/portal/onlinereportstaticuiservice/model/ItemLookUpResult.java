package nzp.digital.portal.onlinereportstaticuiservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemLookUpResult {
	@JsonProperty("Description")
	private String Description;
	@JsonProperty("ItemName")
	private String ItemName;
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	
	
}
