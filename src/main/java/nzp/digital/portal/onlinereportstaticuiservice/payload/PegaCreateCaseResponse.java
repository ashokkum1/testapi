package nzp.digital.portal.onlinereportstaticuiservice.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PegaCreateCaseResponse {
	 @JsonProperty("ID") 
	 private String ID;
	 private String nextPageID;
	 private String nextAssignmentID;
	 private String pxObjClass;


	 // Getter Methods 

	 public String getID() {
	  return ID;
	 }

	 public String getNextPageID() {
	  return nextPageID;
	 }

	 public String getNextAssignmentID() {
	  return nextAssignmentID;
	 }

	 public String getPxObjClass() {
	  return pxObjClass;
	 }

	 // Setter Methods 

	 public void setID(String ID) {
	  this.ID = ID;
	 }

	 public void setNextPageID(String nextPageID) {
	  this.nextPageID = nextPageID;
	 }

	 public void setNextAssignmentID(String nextAssignmentID) {
	  this.nextAssignmentID = nextAssignmentID;
	 }

	 public void setPxObjClass(String pxObjClass) {
	  this.pxObjClass = pxObjClass;
	 }
}
