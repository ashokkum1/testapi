package nzp.digital.portal.onlinereportstaticuiservice.model;

public class WhenItHappened {
	 private String startDateTime;
	 private String endDateTime;
	 
	 private String startDate;
	 private String endDate;
	 
	 private String startTime;
	 private String endTime;


	 // Getter Methods 

	 public String getStartDateTime() {
	  return startDateTime;
	 }

	 public String getEndDateTime() {
	  return endDateTime;
	 }

	 // Setter Methods 

	 public void setStartDateTime(String startDateTime) {
	  this.startDateTime = startDateTime;
	 }

	 public void setEndDateTime(String endDateTime) {
	  this.endDateTime = endDateTime;
	 }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	}
