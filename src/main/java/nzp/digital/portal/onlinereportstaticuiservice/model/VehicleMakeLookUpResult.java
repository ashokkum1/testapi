package nzp.digital.portal.onlinereportstaticuiservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleMakeLookUpResult {	
	@JsonProperty("Code")
	private String Code;
	@JsonProperty("Description")
	private String Description;
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}

}
