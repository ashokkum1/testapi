package nzp.digital.portal.onlinereportstaticuiservice.payload;

import java.util.List;

import nzp.digital.portal.onlinereportstaticuiservice.model.Contacts;
import nzp.digital.portal.onlinereportstaticuiservice.model.DamagedVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.EventInfo;
import nzp.digital.portal.onlinereportstaticuiservice.model.ItemDetail;
import nzp.digital.portal.onlinereportstaticuiservice.model.ReportType;
import nzp.digital.portal.onlinereportstaticuiservice.model.StolenItems;
import nzp.digital.portal.onlinereportstaticuiservice.model.StolenVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhatHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhenItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhereItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhoAssaulted;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhoWasInvolved;

public class OnlineReportCaseRequest {
	 private boolean notAnEmergency;
	 private ReportType reportType;
	 private EventInfo eventInfo;
	 private Contacts contacts;
	 
	
	 // Getter Methods 	

	

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	public boolean isNotAnEmergency() {
		return notAnEmergency;
	}

	public void setNotAnEmergency(boolean notAnEmergency) {
		this.notAnEmergency = notAnEmergency;
	}

	public EventInfo getEventInfo() {
		return eventInfo;
	}

	public void setEventInfo(EventInfo eventInfo) {
		this.eventInfo = eventInfo;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
}
