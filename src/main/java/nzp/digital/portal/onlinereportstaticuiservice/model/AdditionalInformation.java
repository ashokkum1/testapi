package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.List;

public class AdditionalInformation {
	private String otherEvidence;
	private List<String> files;
	
	public String getOtherEvidence() {
		return otherEvidence;
	}
	public void setOtherEvidence(String otherEvidence) {
		this.otherEvidence = otherEvidence;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	
}
