package nzp.digital.portal.onlinereportstaticuiservice.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import nzp.digital.portal.onlinereportstaticuiservice.enums.AddressType;
import nzp.digital.portal.onlinereportstaticuiservice.enums.ContactMediaPreference;
import nzp.digital.portal.onlinereportstaticuiservice.enums.Gender;
import nzp.digital.portal.onlinereportstaticuiservice.enums.HowCloseToTheAddress;
import nzp.digital.portal.onlinereportstaticuiservice.enums.LocationType;
import nzp.digital.portal.onlinereportstaticuiservice.enums.OwnerDisplayType;
import nzp.digital.portal.onlinereportstaticuiservice.enums.OwnerType;
import nzp.digital.portal.onlinereportstaticuiservice.enums.PhoneType;
import nzp.digital.portal.onlinereportstaticuiservice.enums.Relationship;
import nzp.digital.portal.onlinereportstaticuiservice.enums.UnitType;
import nzp.digital.portal.onlinereportstaticuiservice.enums.VehicleLocation;
import nzp.digital.portal.onlinereportstaticuiservice.enums.VehicleType;
import nzp.digital.portal.onlinereportstaticuiservice.model.Address;
import nzp.digital.portal.onlinereportstaticuiservice.model.AddressLookUpResult;
import nzp.digital.portal.onlinereportstaticuiservice.model.Contacts;
import nzp.digital.portal.onlinereportstaticuiservice.model.ItemDetail;
import nzp.digital.portal.onlinereportstaticuiservice.model.ItemLookUpResult;
import nzp.digital.portal.onlinereportstaticuiservice.model.LookUpResult;
import nzp.digital.portal.onlinereportstaticuiservice.model.LookUpValue;
import nzp.digital.portal.onlinereportstaticuiservice.model.MissingVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.Name;
import nzp.digital.portal.onlinereportstaticuiservice.model.Organisation;
import nzp.digital.portal.onlinereportstaticuiservice.model.OrganisationContact;
import nzp.digital.portal.onlinereportstaticuiservice.model.Owner;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaAddressLookup;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaCase;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaItemLookup;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaVehicleMakeLookup;
import nzp.digital.portal.onlinereportstaticuiservice.model.PersonalContact;
import nzp.digital.portal.onlinereportstaticuiservice.model.Phone;
import nzp.digital.portal.onlinereportstaticuiservice.model.ReportType;
import nzp.digital.portal.onlinereportstaticuiservice.model.Vehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.VehicleMakeLookUpResult;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhatHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhenItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhereItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhoAssaulted;
import nzp.digital.portal.onlinereportstaticuiservice.model.WhoWasInvolved;
import nzp.digital.portal.onlinereportstaticuiservice.payload.OnlineReportCaseRequest;
import nzp.digital.portal.onlinereportstaticuiservice.payload.PegaCreateCaseResponse;



public class ModelMapper {
	public static JSONObject stringToJson(String json) {
		JSONObject jsonObject=null;
		try {
			JSONParser parser = new JSONParser(json);
			jsonObject = (JSONObject) parser.parse();
		}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return jsonObject;
		
	}
    public static String javaToJson(Object obj) {
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonString="";
    	try {
    		 jsonString =  mapper.writeValueAsString(obj);
    	}catch(Exception e) {
    		
    	}
    	return jsonString;
    }
    

    public static String mapPegaCreateRequest(OnlineReportCaseRequest createOnlineReportRequest) {    	
    	String pegaCreateCaseRequest = new JSONObject()
                .put("caseTypeID", "Police-SDW-Work-OnlineReporting")
                .put("processID", "pyStartCase")
                .put("parentCaseID", "")
                .put("content", mapCaseAnswerQuestionsRequest(createOnlineReportRequest)).toString();    	
    	return pegaCreateCaseRequest;
    	
    }
    
    //mapUpdateCaseAnswerQuestionsRequest
    
    public static JSONObject mapEventTypeIndicator(boolean notAnEmergency, ReportType reportType) {
    	JSONObject eventTypeObject = new JSONObject().put("pxObjClass", "Police-SDW-Data-EventTypeIndicator");
    	if(notAnEmergency) {eventTypeObject.put("DoContinue", (notAnEmergency)?"true":"false");}
    	if(reportType!=null) {mapReportType(eventTypeObject,reportType);}    	
    	return eventTypeObject;
    }
    
    public static JSONObject mapReportType(JSONObject eventTypeObject, ReportType reportType) {    	
    	if(reportType!=null) {
    		eventTypeObject.put("BeenDamaged", reportType.isSomethingDamaged());
    		eventTypeObject.put("BeenHurt", reportType.isSomeoneHurt());
    		eventTypeObject.put("BeenStolen", reportType.isSomethingStolen());
    		eventTypeObject.put("LostProperty", reportType.isSomethingLost());
    		eventTypeObject.put("Other", reportType.isSomethingElse());    		
    	}else {
    		eventTypeObject.put("BeenDamaged", "false");
    		eventTypeObject.put("BeenHurt", "false");
    		eventTypeObject.put("BeenStolen", "false");
    		eventTypeObject.put("LostProperty", "false");
    		eventTypeObject.put("Other", "false");
    	}
    	return eventTypeObject;
    }
    
    public static String mapCancelCaseRequest() {    	
    	String pegaCancelCaseRequest = new JSONObject()                
                .put("content", new JSONObject()
                     .put("pyChangeStageOption", "goto")
                     .put("pxGotoStage", "Cancelled")
                  	 .put("pyGotoStage", "ALT1")).toString();    	
    	return pegaCancelCaseRequest;
    	
    }
    
    public static PegaCase mapPegaCreateCaseResponse(PegaCreateCaseResponse pegaCreateCaseResponse) {
    	PegaCase pegaCase = new PegaCase();
    	if(StringUtils.isNoneEmpty(pegaCreateCaseResponse.getID())) {pegaCase.setCaseId(pegaCreateCaseResponse.getID());}
    	//if(StringUtils.isNoneEmpty(pegaCreateCaseResponse.getNextAssignmentID())) {pegaCase.setNextAssignmentId(pegaCreateCaseResponse.getNextAssignmentID());}
    	return pegaCase;
    	
    }
    
    public static LookUpValue mapPegaAddressLookupResponse(PegaAddressLookup pegaAddressLookup) {
    	LookUpValue lookUpValue = new LookUpValue();
    	List<LookUpResult> lookUpResultSet = new ArrayList<LookUpResult>();
    	lookUpValue.setResults(lookUpResultSet);
    	if(pegaAddressLookup!=null && pegaAddressLookup.getPxResults()!=null && pegaAddressLookup.getPxResults().size()>0) {
    		for(AddressLookUpResult addressLookUpResult : pegaAddressLookup.getPxResults()) {
    			LookUpResult lookUpResult = new LookUpResult();
    			if(StringUtils.isNoneEmpty(addressLookUpResult.getNIAID())) {lookUpResult.setId(addressLookUpResult.getNIAID());}
    			if(StringUtils.isNoneEmpty(addressLookUpResult.getPyLabel())) {lookUpResult.setText(addressLookUpResult.getPyLabel());}
    			lookUpResultSet.add(lookUpResult);
    		}    		
    		
    	}
    	return lookUpValue;    	
    }
    
    public static LookUpValue mapPegaVehicleMakeLookupResponse(PegaVehicleMakeLookup pegaVehicleMakeLookup) {
    	LookUpValue lookUpValue = new LookUpValue();
    	List<LookUpResult> lookUpResultSet = new ArrayList<LookUpResult>();
    	lookUpValue.setResults(lookUpResultSet);    	
    	if(pegaVehicleMakeLookup!=null && pegaVehicleMakeLookup.getPxResults()!=null && pegaVehicleMakeLookup.getPxResults().size()>0) {
    		for(VehicleMakeLookUpResult vehicleMakeLookUpResult : pegaVehicleMakeLookup.getPxResults()) {
    			LookUpResult lookUpResult = new LookUpResult();    			
    			if(StringUtils.isNoneEmpty(vehicleMakeLookUpResult.getCode())) {lookUpResult.setId(vehicleMakeLookUpResult.getCode());}
    			if(StringUtils.isNoneEmpty(vehicleMakeLookUpResult.getDescription())) {lookUpResult.setText(vehicleMakeLookUpResult.getDescription());}
    			lookUpResultSet.add(lookUpResult);
    		}   		
    		
    	}
    	return lookUpValue;  
    	
    }
    
    
    public static LookUpValue mapPegaItemLookupResponse(PegaItemLookup pegaItemLookup) {
    	LookUpValue lookUpValue = new LookUpValue();
    	List<LookUpResult> lookUpResultSet = new ArrayList<LookUpResult>();
    	lookUpValue.setResults(lookUpResultSet);
    	if(pegaItemLookup!=null && pegaItemLookup.getPxResults()!=null && pegaItemLookup.getPxResults().size()>0) {
    		for(ItemLookUpResult itemLookUpResult : pegaItemLookup.getPxResults()) {
    			LookUpResult lookUpResult = new LookUpResult();
    			if(StringUtils.isNoneEmpty(itemLookUpResult.getItemName())) {lookUpResult.setId(itemLookUpResult.getItemName());}
    			if(StringUtils.isNoneEmpty(itemLookUpResult.getDescription())) {lookUpResult.setText(itemLookUpResult.getDescription());}
    			lookUpResultSet.add(lookUpResult);
    		}    		
    		
    	}
    	return lookUpValue;  
    	
    }
    
    public static PegaCase mapPegaUpdateCaseResponse(String caseId, PegaCreateCaseResponse pegaCreateCaseResponse) {
    	PegaCase pegaCase = new PegaCase();
    	if(StringUtils.isNoneEmpty(caseId)) {pegaCase.setCaseId(caseId);}
    	//if(StringUtils.isNoneEmpty(pegaCreateCaseResponse.getNextAssignmentID())) {pegaCase.setNextAssignmentId(pegaCreateCaseResponse.getNextAssignmentID());}
    	return pegaCase;
    	
    }    
    
    public static String mapUpdateCaseReportTypeRequest(OnlineReportCaseRequest updateCaseReportTypeRequest) {
    	JSONObject eventTypeObject = new JSONObject().put("pxObjClass", "Police-SDW-Data-EventTypeIndicator");
    	JSONObject pegaUpdateCaseReportTypeRequest = new JSONObject();    	
    	if(updateCaseReportTypeRequest!=null && updateCaseReportTypeRequest.getReportType()!=null) {
    		eventTypeObject.put("BeenDamaged", updateCaseReportTypeRequest.getReportType().isSomethingDamaged());
    		eventTypeObject.put("BeenHurt", updateCaseReportTypeRequest.getReportType().isSomeoneHurt());
    		eventTypeObject.put("BeenStolen", updateCaseReportTypeRequest.getReportType().isSomethingStolen());
    		eventTypeObject.put("LostProperty", updateCaseReportTypeRequest.getReportType().isSomethingLost());
    		eventTypeObject.put("Other", updateCaseReportTypeRequest.getReportType().isSomethingElse()); 
    	}else {
    		eventTypeObject.put("BeenDamaged", "false");
    		eventTypeObject.put("BeenHurt", "false");
    		eventTypeObject.put("BeenStolen", "false");
    		eventTypeObject.put("LostProperty", "false");
    		eventTypeObject.put("Other", "false");
    	}
    	
    	pegaUpdateCaseReportTypeRequest.put("content", new JSONObject()
                .put("EventTypeIndicator", eventTypeObject
                		));
    	
    	return pegaUpdateCaseReportTypeRequest.toString();
    	
    }
    

    
    public static JSONObject mapCaseAnswerQuestionsRequest(OnlineReportCaseRequest updateCaseReportTypeRequest) {
    	
    	JSONObject pegaUpdateCaseAnswerQuestionsRequest = new JSONObject();
    	pegaUpdateCaseAnswerQuestionsRequest.put("pxObjClass", "Police-SDW-Work-OnlineReporting");    	
    	if(updateCaseReportTypeRequest.isNotAnEmergency() || updateCaseReportTypeRequest.getReportType()!=null) {
    		pegaUpdateCaseAnswerQuestionsRequest.put("EventTypeIndicator", mapEventTypeIndicator(updateCaseReportTypeRequest.isNotAnEmergency(), updateCaseReportTypeRequest.getReportType()));
    	}
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getWhenItHappened()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("DateEntry", mapWhenHappened(updateCaseReportTypeRequest.getEventInfo().getWhenItHappened()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getWhereItHappened()!=null) {
    		pegaUpdateCaseAnswerQuestionsRequest.put("LocationType", mapWhereHappened(updateCaseReportTypeRequest.getEventInfo().getWhereItHappened()));
    		pegaUpdateCaseAnswerQuestionsRequest.put("Stolenfromavehicleindicator",(updateCaseReportTypeRequest.getEventInfo().getWhereItHappened().isStolenFromVehicle())?"Yes":"No");
    		pegaUpdateCaseAnswerQuestionsRequest.put("Property", mapProperty(updateCaseReportTypeRequest.getEventInfo().getWhereItHappened().getLicencePlate(), updateCaseReportTypeRequest.getEventInfo().getWhereItHappened().getVehicleLocation()));
    	}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null &&  updateCaseReportTypeRequest.getEventInfo().getLostItems()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Item", mapItem(updateCaseReportTypeRequest.getEventInfo().getLostItems(),  updateCaseReportTypeRequest.getContacts()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null &&  updateCaseReportTypeRequest.getEventInfo().getDamagedItems()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Properties", mapProperties(updateCaseReportTypeRequest.getEventInfo().getDamagedItems(),  updateCaseReportTypeRequest.getContacts()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null &&  updateCaseReportTypeRequest.getEventInfo().getWhatHappened()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Narrative", mapWhatHappened(updateCaseReportTypeRequest.getEventInfo().getWhatHappened()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null &&  updateCaseReportTypeRequest.getEventInfo().getWhoWasHurt()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Assault", mapWhoAssaulted(updateCaseReportTypeRequest.getEventInfo().getWhoWasHurt()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null &&  updateCaseReportTypeRequest.getEventInfo().getWhoWasInvolved()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Offender", mapWhoWasInvolved(updateCaseReportTypeRequest.getEventInfo().getWhoWasInvolved()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getAdditionalInformation()!=null && StringUtils.isNoneEmpty(updateCaseReportTypeRequest.getEventInfo().getAdditionalInformation().getOtherEvidence())) {pegaUpdateCaseAnswerQuestionsRequest.put("CCTVFootageAttachmentDescription", updateCaseReportTypeRequest.getEventInfo().getAdditionalInformation().getOtherEvidence());}
    	    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getStolenItems()!=null) {
    		if(updateCaseReportTypeRequest.getEventInfo().getStolenItems().size()>0) {pegaUpdateCaseAnswerQuestionsRequest.put("Item", mapItem(updateCaseReportTypeRequest.getEventInfo().getStolenItems(),  updateCaseReportTypeRequest.getContacts()));}
    	}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getStolenVehicles()!=null) {
    		pegaUpdateCaseAnswerQuestionsRequest.put("VehicleStolenIndicator",(updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().isAnyStolen())?"Yes":"No");
    		if(updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().getVehicles()!=null && updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().getVehicles().size()>0) {pegaUpdateCaseAnswerQuestionsRequest.put("StolenVehicle", mapStolenVehicle(updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().getVehicles(),  updateCaseReportTypeRequest.getContacts()));}
    	}
    	    	
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles()!=null) {
    		if(updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().isAnyDamaged()) {
    			pegaUpdateCaseAnswerQuestionsRequest.put("VehicleDamagedIndicator", "Yes");
    			if(updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().getVehicles()!=null && updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().getVehicles().size()>0) {pegaUpdateCaseAnswerQuestionsRequest.put("DamagedVehicle", mapDamagedVehicle(updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().getVehicles(),  updateCaseReportTypeRequest.getContacts()));}    			
    		}else {
    			pegaUpdateCaseAnswerQuestionsRequest.put("VehicleDamagedIndicator", "No");    			
    		}
    	}
    	    	
    	if(updateCaseReportTypeRequest.getContacts()!=null) {mapContact(pegaUpdateCaseAnswerQuestionsRequest, updateCaseReportTypeRequest.getContacts());}    	
    	
    	return pegaUpdateCaseAnswerQuestionsRequest;
    }
    //mapUpdateCaseEnterPersonalInfoRequest
    public static String mapUpdateCaseEnterPersonalInfoRequest(OnlineReportCaseRequest updateCaseReportTypeRequest) {
    	JSONObject pegaUpdateCaseReportTypeRequest = new JSONObject();
    	JSONObject pegaUpdateCaseEnterPersonalInfoRequest = new JSONObject();
    	pegaUpdateCaseEnterPersonalInfoRequest.put("pxObjClass", "Police-SDW-Work-OnlineReporting");
    	
//    	if(updateCaseReportTypeRequest.getContactDetail()!=null) {pegaUpdateCaseEnterPersonalInfoRequest.put("Person", mapContactDetail(updateCaseReportTypeRequest.getContactDetail()));}
    	
    	pegaUpdateCaseReportTypeRequest.put("content", pegaUpdateCaseEnterPersonalInfoRequest);
    	
    	return pegaUpdateCaseReportTypeRequest.toString();
    }
    
    private static void mapContact(JSONObject jsonObject, Contacts contact) {
    	if(contact.getReporter()!=null) {jsonObject.put("Person", mapPersonalContact(contact.getReporter()));}
    	if(contact.getOtherPeople()!=null) {jsonObject.put("SomebodyElse", mapSomebodyElseContact(contact.getOtherPeople()));}
    	if(contact.getOrganisations()!=null) {jsonObject.put("OrganizationDoes", mapOrganizationContact(contact.getOrganisations()));}
    }
    
    
    //mapOrganizationContact
    private static List<JSONObject> mapOrganizationContact(List<OrganisationContact> orgContactList) {
    	List<JSONObject> contactList = new ArrayList<JSONObject>();
    	for(OrganisationContact contact : orgContactList) {
    		contactList.add(mapOrgContact(contact));
    	}    	
    	return contactList;
    }
    //mapSomebodyElseContact
    private static List<JSONObject> mapSomebodyElseContact(List<PersonalContact> othersContactList) {
    	List<JSONObject> contactList = new ArrayList<JSONObject>();
    	for(PersonalContact contact : othersContactList) {
    		contactList.add(mapPersonalContact(contact));
    	}    	
    	return contactList;
    }
    private static JSONObject mapOrgContact(OrganisationContact orgContact) {
    	JSONObject orgContactJson = new JSONObject();
    	orgContactJson.put("pxObjClass", "Police-SDW-Data-Person");
    	if(orgContact.getContactDetails()!=null && orgContact.getContactDetails().getAddress()!=null) {orgContactJson.put("NotInLookUp", (orgContact.getContactDetails().getAddress().isCannotFind())?"true":"false");}
    	if(orgContact.getContactDetails()!=null && orgContact.getContactDetails().getAddress()!=null && orgContact.getContactDetails().getAddress().isCannotFind() && orgContact.getContactDetails().getAddress().getManual()!=null) {orgContactJson.put("FreeFormAddress", mapAddress(orgContact.getContactDetails().getAddress().getManual()));}
    	if(StringUtils.isNoneEmpty(orgContact.getName())) {orgContactJson.put("Organization", mapOrganization(orgContact.getName()));}
    	if(orgContact.getContactDetails()!=null && orgContact.getContactDetails().getPhoneNumbers()!=null) {orgContactJson.put("Phones", mapPhones(orgContact.getContactDetails().getPhoneNumbers()));}
    	
    	return orgContactJson;
    }
    private static JSONObject mapPersonalContact(PersonalContact personalContact) {
    	JSONObject personalContactJson = new JSONObject();
    	personalContactJson.put("pxObjClass", "Police-SDW-Data-Person");
    	if(personalContact.getPersonalDetails()!=null && personalContact.getPersonalDetails().getName()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getName().getFirst())) {personalContactJson.put("FirstName", personalContact.getPersonalDetails().getName().getFirst());}
    	if(personalContact.getPersonalDetails()!=null && personalContact.getPersonalDetails().getName()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getName().getMiddle())) {personalContactJson.put("MiddleName", personalContact.getPersonalDetails().getName().getMiddle());}
    	if(personalContact.getPersonalDetails()!=null && personalContact.getPersonalDetails().getName()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getName().getLast())) {personalContactJson.put("SurName", personalContact.getPersonalDetails().getName().getLast());}
    	if(personalContact.getPersonalDetails()!=null) {personalContactJson.put("KnowByOtherName", (personalContact.getPersonalDetails().isHasPreviousName())?"true":"false");}
    	if(personalContact.getPersonalDetails()!=null && personalContact.getPersonalDetails().getPreviousName()!=null) {personalContactJson.put("AlternativeName", mapName(personalContact.getPersonalDetails().getPreviousName()));}
    	if(personalContact.getPersonalDetails()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getDateOfBirth())) {personalContactJson.put("DOB", formatDob(personalContact.getPersonalDetails().getDateOfBirth()));}
    	if(personalContact.getPersonalDetails()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getDateOfBirth())) {personalContactJson.put("AgeOrBirth", formatDob(personalContact.getPersonalDetails().getDateOfBirth(), "dd/MM/yyyy"));}
    	if(personalContact.getPersonalDetails()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getGender())) {personalContactJson.put("Gender", Gender.valueOf(personalContact.getPersonalDetails().getGender()).getPegaGender());}
    	if(personalContact.getPersonalDetails()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getDriverLicence())) {personalContactJson.put("LicenseNumber", personalContact.getPersonalDetails().getDriverLicence());}
    	if(personalContact.getPersonalDetails()!=null && StringUtils.isNoneEmpty(personalContact.getPersonalDetails().getRelation())) {personalContactJson.put("Relation", Relationship.valueOf(personalContact.getPersonalDetails().getRelation()).getPegaRelationship());}
    	if(personalContact.getContactDetails()!=null && StringUtils.isNoneEmpty(personalContact.getContactDetails().getPreferredContactMethod())) {personalContactJson.put("PreferedContact", ContactMediaPreference.valueOf(personalContact.getContactDetails().getPreferredContactMethod()).getPegaContactMediaPreference());}
    	personalContactJson.put("VictimSupport", (personalContact.isWantsVictimSupport())?"Yes" : "No");
    	if(personalContact.getContactDetails()!=null && personalContact.getContactDetails().getAddress()!=null && StringUtils.isNoneEmpty(personalContact.getContactDetails().getAddress().getType())) {personalContactJson.put("AddressType", AddressType.valueOf(personalContact.getContactDetails().getAddress().getType()).getPegaAddressType());}
    	if(personalContact.getContactDetails()!=null && personalContact.getContactDetails().getAddress()!=null) {personalContactJson.put("NotInLookUp", (personalContact.getContactDetails().getAddress().isCannotFind())?"true":"false");}
    	if(personalContact.getContactDetails()!=null && personalContact.getContactDetails().getAddress()!=null && personalContact.getContactDetails().getAddress().isCannotFind() && personalContact.getContactDetails().getAddress().getManual()!=null) {personalContactJson.put("FreeFormAddress", mapAddress(personalContact.getContactDetails().getAddress().getManual()));}
    	if(personalContact.getContactDetails()!=null && personalContact.getContactDetails().getEmails()!=null) {personalContactJson.put("EmailAddress", mapEmailAddress(personalContact.getContactDetails().getEmails()));}
    	if(personalContact.getContactDetails()!=null && personalContact.getContactDetails().getPhoneNumbers()!=null) {personalContactJson.put("Phones", mapPhones(personalContact.getContactDetails().getPhoneNumbers()));}
    	return personalContactJson;
    }
   
    
    private static List<JSONObject> mapPhones(List<Phone> contactPhoneList) {
    	List<JSONObject> phoneList = new ArrayList<JSONObject>();
    	for(Phone contactPhone : contactPhoneList) {
    		JSONObject phoneJson = new JSONObject();
        	phoneJson.put("pxObjClass", "Police-SDW-Data-Person-Phones");
        	if(StringUtils.isNoneEmpty(contactPhone.getArea())) {phoneJson.put("AreaCodeText", contactPhone.getArea());}
        	if(StringUtils.isNoneEmpty(contactPhone.getCountry())) {phoneJson.put("CountryCode", contactPhone.getCountry());}
        	if(StringUtils.isNoneEmpty(contactPhone.getNumber())) {phoneJson.put("PhoneNumberText", contactPhone.getNumber());}
        	if(StringUtils.isNoneEmpty(contactPhone.getType())) {phoneJson.put("PhoneType", PhoneType.valueOf(contactPhone.getType()).getPegaPhoneType());}
        	phoneList.add(phoneJson);
    	}    	
    	return phoneList;
    }
    
    private static List<JSONObject> mapEmailAddress(List<String> emails) {
    	List<JSONObject> emailList = new ArrayList<JSONObject>();
    	for(String email : emails) {
    		
    		JSONObject emailJson = new JSONObject();
    		emailJson.put("pxObjClass", "Police-SDW-Data-Person-Email");
    		if(StringUtils.isNoneEmpty(email)) {emailJson.put("Email", email);}
    		emailList.add(emailJson);
    		
    	}    	
    	return emailList;
    }
    
    //mapItem
    private static List<JSONObject> mapItem(List<ItemDetail> items, Contacts contacts) {    	
    	List<JSONObject> lostItemList = new ArrayList<JSONObject>();
    	for(ItemDetail item : items) {
	    	JSONObject lostItemJson = new JSONObject();
	    	lostItemJson.put("pxObjClass", "Police-SDW-Data-Item");	    	
	    	if(StringUtils.isNoneEmpty(item.getId())) {lostItemJson.put("SerialNumber", item.getId());}
	    	if(StringUtils.isNoneEmpty(item.getValue())) {lostItemJson.put("ItemValueDoller", item.getValue());}
	    	if(StringUtils.isNoneEmpty(item.getDescription())) {lostItemJson.put("ItemDescription", item.getDescription());}
	    	if(item.getWhat()!=null) {
	    		if(item.getWhat().isCannotFind()) {
	    			if(StringUtils.isNoneEmpty(item.getWhat().getManual())) {lostItemJson.put("ItemName", item.getWhat().getManual());}
	    		}else {
	    			lostItemJson.put("NotInLookUp", (item.getWhat().isCannotFind())?"true":"false");
	    			if(item.getWhat().getSearch()!=null) {	    				
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getCategory())) {lostItemJson.put("Category", item.getWhat().getSearch().getCategory());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getSubCategory())) {lostItemJson.put("SubCategory", item.getWhat().getSearch().getSubCategory());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getItemName())) {lostItemJson.put("ItemName", item.getWhat().getSearch().getItemName());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getNiaItemName())) {lostItemJson.put("NIAItemName", item.getWhat().getSearch().getNiaItemName());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getDisplayValue())) {lostItemJson.put("DisplayValue", item.getWhat().getSearch().getDisplayValue());}
	    			}
	    		}
	    		
	    	}
	    	
	    	if(item.getOwner()!=null) {
	    		extractOwnerDetailFromContact(item.getOwner(), contacts);
	    		if(StringUtils.isNoneEmpty(item.getOwner().getType())) {lostItemJson.put("WhoOwnsIt", OwnerType.valueOf(item.getOwner().getType()).getPegaOwnerType());}
	    		if(StringUtils.isNoneEmpty(item.getOwner().getType())) {lostItemJson.put("DisplayValue", OwnerDisplayType.valueOf(item.getOwner().getType()).getPegaOwnerDisplayType());}
	    		if(StringUtils.isNoneEmpty(item.getOwner().getOrganisationName())) {	    			
	    			lostItemJson.put("OrganizationItem", new JSONObject()
	    	                .put("pxObjClass", "Police-SDW-Data-Organisation")
	    	                .put("OrganisationName", item.getOwner().getOrganisationName()));
	    		}else {	    			
	    			JSONObject ownerPersonJson = new JSONObject();
	    			ownerPersonJson.put("pxObjClass", "Police-SDW-Data-Owner");
	    			if(item.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(item.getOwner().getPerson().getFirst())) {ownerPersonJson.put("Fname", item.getOwner().getPerson().getFirst());}
	    	    	if(item.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(item.getOwner().getPerson().getLast())) {ownerPersonJson.put("Sname", item.getOwner().getPerson().getLast());}
	    	    	if(item.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(item.getOwner().getPerson().getMiddle())) {ownerPersonJson.put("Mname", item.getOwner().getPerson().getMiddle());}
	    	    	lostItemJson.put("Owner", ownerPersonJson);
	    		}
	    	
	    	}
	    	lostItemList.add(lostItemJson);
    	}
    	return lostItemList;
    }
    
    private static void extractOwnerDetailFromContact(Owner owner, Contacts contacts) {    	
    	if(owner!=null && contacts!=null) {    		
	    	if(StringUtils.isNoneEmpty(owner.getType()) && "Somebody else does".equalsIgnoreCase(OwnerType.valueOf(owner.getType()).getPegaOwnerType()) && contacts.getOtherPeople()!=null && contacts.getOtherPeople().size() > owner.getIndex()) {
	    		extractPersonOwnerFromContact(owner, contacts.getOtherPeople().get(owner.getIndex()));
	    		
	    	}else if(StringUtils.isNoneEmpty(owner.getType()) && "An organisation does".equalsIgnoreCase(OwnerType.valueOf(owner.getType()).getPegaOwnerType())&& contacts.getOrganisations()!=null && contacts.getOrganisations().size() > owner.getIndex()) {
	    		extractOrganisationDetailFromContact(owner, contacts.getOrganisations().get(owner.getIndex()));
	    	}
    	}
    	
    }
    
    private static void extractPersonOwnerFromContact(Owner owner, PersonalContact personalContact) {    	
    	if(personalContact!=null && personalContact.getPersonalDetails()!=null && personalContact.getPersonalDetails().getName()!=null) {    		
    		owner.setPerson(personalContact.getPersonalDetails().getName());
    	}
    	
    }
    
    private static void extractOrganisationDetailFromContact(Owner owner, OrganisationContact organisationContact) {    	
    	if(organisationContact!=null && StringUtils.isNoneEmpty(organisationContact.getName())) {
    		owner.setOrganisationName(organisationContact.getName());
    	}
    }
    
    //mapProperty
    private static List<JSONObject> mapProperties(List<ItemDetail> items, Contacts contacts) {
    	List<JSONObject> damagedItemList = new ArrayList<JSONObject>();
    	for(ItemDetail item : items) {
	    	JSONObject itemJson = new JSONObject();
	    	itemJson.put("pxObjClass", "Police-SDW-Data-Property");
	    	itemJson.put("Category", "PROPERTY");
	    	
	    	if(StringUtils.isNoneEmpty(item.getId())) {itemJson.put("SerialNumber", item.getId());}
	    	if(StringUtils.isNoneEmpty(item.getValue())) {itemJson.put("ItemValueDoller", item.getValue());}
	    	if(StringUtils.isNoneEmpty(item.getDescription())) {itemJson.put("DamageDescription", item.getDescription());}
	    	if(item.getWhat()!=null) {
	    		itemJson.put("NotInLookUp", (item.getWhat().isCannotFind())?"true":"false");
	    		if(item.getWhat().isCannotFind()) {
	    			if(StringUtils.isNoneEmpty(item.getWhat().getManual())) {itemJson.put("PropertyType", item.getWhat().getManual());}
	    		}else {
	    			
	    			if(item.getWhat().getSearch()!=null) {	    				
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getCategory())) {itemJson.put("Category", item.getWhat().getSearch().getCategory());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getSubCategory())) {itemJson.put("SubCategory", item.getWhat().getSearch().getSubCategory());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getItemName())) {itemJson.put("PropertyType", item.getWhat().getSearch().getItemName());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getNiaItemName())) {itemJson.put("NIAItemName", item.getWhat().getSearch().getNiaItemName());}
	    				if(StringUtils.isNoneEmpty(item.getWhat().getSearch().getDisplayValue())) {itemJson.put("DisplayValue", item.getWhat().getSearch().getDisplayValue());}
	    			}
	    		}
	    		
	    	}
	    	
	    	if(item.getOwner()!=null) {
	    		extractOwnerDetailFromContact(item.getOwner(), contacts);
	    		if(StringUtils.isNoneEmpty(item.getOwner().getType())) {itemJson.put("WhoOwnsIt", OwnerType.valueOf(item.getOwner().getType()).getPegaOwnerType());}
	    		if(StringUtils.isNoneEmpty(item.getOwner().getType())) {itemJson.put("DisplayValue", OwnerDisplayType.valueOf(item.getOwner().getType()).getPegaOwnerDisplayType());}
	    		if(StringUtils.isNoneEmpty(item.getOwner().getOrganisationName())) {	    			
	    			itemJson.put("Organization", new JSONObject()
	    	                .put("pxObjClass", "Police-SDW-Data-Organisation")
	    	                .put("OrganisationName", item.getOwner().getOrganisationName()));
	    		}else {	    			
	    			JSONObject ownerPersonJson = new JSONObject();
	    			ownerPersonJson.put("pxObjClass", "Police-SDW-Data-Owner");
	    			if(item.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(item.getOwner().getPerson().getFirst())) {ownerPersonJson.put("Fname", item.getOwner().getPerson().getFirst());}
	    	    	if(item.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(item.getOwner().getPerson().getLast())) {ownerPersonJson.put("Sname", item.getOwner().getPerson().getLast());}
	    	    	if(item.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(item.getOwner().getPerson().getMiddle())) {ownerPersonJson.put("Mname", item.getOwner().getPerson().getMiddle());}
	    	    	itemJson.put("Owner", ownerPersonJson);
	    		}
	    	
	    	}
	    	damagedItemList.add(itemJson);
    	}
    	return damagedItemList;
    }
    
    //mapProperty
    private static JSONObject mapProperty(String licencePlate, String vehicleLocation) {
    	JSONObject propertyJson = new JSONObject();
    	propertyJson.put("pxObjClass", "Police-SDW-Data-Property");
    	JSONObject propertyVehicleJson = new JSONObject();
    	propertyVehicleJson.put("pxObjClass", "Police-SDW-Data-Vehicle");
    	if(StringUtils.isNoneEmpty(licencePlate)) {propertyVehicleJson.put("Registration", licencePlate);}
    	if(StringUtils.isNoneEmpty(vehicleLocation)) {propertyVehicleJson.put("VehicleLocation", vehicleLocation);}
    	propertyJson.put("PropertyVehicle", propertyVehicleJson);
    	return propertyJson;
    }
    
    //mapStolenVehicle
    private static List<JSONObject> mapStolenVehicle(List<MissingVehicle> missingVehicle, Contacts contacts) {
    	List<JSONObject> stolenVehicleList = new ArrayList<JSONObject>();
    	for(MissingVehicle vehicle : missingVehicle) {
	    	JSONObject vehicleJson = new JSONObject();
	    	vehicleJson.put("pxObjClass", "Police-SDW-Data-Vehicle");	    	   	
	    	if(StringUtils.isNoneEmpty(vehicle.getHowAndWhere())) {vehicleJson.put("Description", vehicle.getHowAndWhere());}
	    	if(StringUtils.isNoneEmpty(vehicle.getMake())) {vehicleJson.put("Make", vehicle.getMake());}
	    	if(StringUtils.isNoneEmpty(vehicle.getLicencePlate())) {vehicleJson.put("Registration", vehicle.getLicencePlate());}
	    	if(StringUtils.isNoneEmpty(vehicle.getDescription())) {vehicleJson.put("VehicleDescription", vehicle.getDescription());}
	    	if(StringUtils.isNoneEmpty(vehicle.getLocation())) {vehicleJson.put("VehicleLocation", VehicleLocation.valueOf(vehicle.getLocation()).getPegaVehicleLocation());}
	    	if(StringUtils.isNoneEmpty(vehicle.getType())) {vehicleJson.put("VehicleType", VehicleType.valueOf(vehicle.getType()).getPegaVehicleType());}
	    	
	    	if(vehicle.getOwner()!=null) {
	    		extractOwnerDetailFromContact(vehicle.getOwner(), contacts);
	    		if(StringUtils.isNoneEmpty(vehicle.getOwner().getType())) {vehicleJson.put("WhoOwnsIt", OwnerType.valueOf(vehicle.getOwner().getType()).getPegaOwnerType());}
	    		if(StringUtils.isNoneEmpty(vehicle.getOwner().getType())) {vehicleJson.put("DisplayValue", OwnerDisplayType.valueOf(vehicle.getOwner().getType()).getPegaOwnerDisplayType());}
	    		if("ME".equalsIgnoreCase(vehicle.getOwner().getType())) {vehicleJson.put("SelfTowingApproval", (vehicle.isAgreesToTow())?"Yes":"No");}
		    	if(!"ME".equalsIgnoreCase(vehicle.getOwner().getType())) {vehicleJson.put("SomeonesTowingApproval", (vehicle.isAgreesToTow())?"Yes":"No");}
		    	
	    		if(StringUtils.isNoneEmpty(vehicle.getOwner().getOrganisationName())) {	    			
	    			vehicleJson.put("Organization", new JSONObject()
	    	                .put("pxObjClass", "Police-SDW-Data-Organisation")
	    	                .put("OrganisationName", vehicle.getOwner().getOrganisationName()));
	    		}else {	    			
	    			JSONObject ownerPersonJson = new JSONObject();
	    			ownerPersonJson.put("pxObjClass", "Police-SDW-Data-Owner");
	    			if(vehicle.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(vehicle.getOwner().getPerson().getFirst())) {ownerPersonJson.put("Fname", vehicle.getOwner().getPerson().getFirst());}
	    	    	if(vehicle.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(vehicle.getOwner().getPerson().getLast())) {ownerPersonJson.put("Sname", vehicle.getOwner().getPerson().getLast());}
	    	    	if(vehicle.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(vehicle.getOwner().getPerson().getMiddle())) {ownerPersonJson.put("Mname", vehicle.getOwner().getPerson().getMiddle());}
	    	    	vehicleJson.put("Owner", ownerPersonJson);
	    		}
	    	
	    	}
	    	stolenVehicleList.add(vehicleJson);
    	}    	
    	
    	return stolenVehicleList;
    }
    
    
    //mapDamagedVehicle
    private static List<JSONObject> mapDamagedVehicle(List<Vehicle> vehicles, Contacts contacts) {
    	List<JSONObject> damagedVehicleList = new ArrayList<JSONObject>();
    	for(Vehicle vehicle : vehicles) {
	    	JSONObject vehicleJson = new JSONObject();
	    	vehicleJson.put("pxObjClass", "Police-SDW-Data-Vehicle");
	    	vehicleJson.put("MovedTampered", (vehicle.isWasMoved())?"Yes":"No");
	    	vehicleJson.put("VehicleTampered", (vehicle.isIgnitionTamperedWith())?"Yes":"No");    	
	    	if(StringUtils.isNoneEmpty(vehicle.getHowAndWhere())) {vehicleJson.put("Description", vehicle.getHowAndWhere());}
	    	if(StringUtils.isNoneEmpty(vehicle.getMake())) {vehicleJson.put("Make", vehicle.getMake());}
	    	if(StringUtils.isNoneEmpty(vehicle.getLicencePlate())) {vehicleJson.put("Registration", vehicle.getLicencePlate());}
	    	if(StringUtils.isNoneEmpty(vehicle.getDescription())) {vehicleJson.put("VehicleDescription", vehicle.getDescription());}
	    	if(StringUtils.isNoneEmpty(vehicle.getLocation())) {vehicleJson.put("VehicleLocation",  VehicleLocation.valueOf(vehicle.getLocation()).getPegaVehicleLocation());}
	    	if(StringUtils.isNoneEmpty(vehicle.getType())) {vehicleJson.put("VehicleType", VehicleType.valueOf(vehicle.getType()).getPegaVehicleType());}
	    		    	
	    	if(vehicle.getOwner()!=null) {
	    		extractOwnerDetailFromContact(vehicle.getOwner(), contacts);
	    		if(StringUtils.isNoneEmpty(vehicle.getOwner().getType())) {vehicleJson.put("WhoOwnsIt", OwnerType.valueOf(vehicle.getOwner().getType()).getPegaOwnerType());}
	    		if(StringUtils.isNoneEmpty(vehicle.getOwner().getType())) {vehicleJson.put("DisplayValue", OwnerDisplayType.valueOf(vehicle.getOwner().getType()).getPegaOwnerDisplayType());}
	    		if(StringUtils.isNoneEmpty(vehicle.getOwner().getOrganisationName())) {	    			
	    			vehicleJson.put("OrganizationItem", new JSONObject()
	    	                .put("pxObjClass", "Police-SDW-Data-Organisation")
	    	                .put("OrganisationName", vehicle.getOwner().getOrganisationName()));
	    		}else {	    			
	    			JSONObject ownerPersonJson = new JSONObject();
	    			ownerPersonJson.put("pxObjClass", "Police-SDW-Data-Owner");
	    			if(vehicle.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(vehicle.getOwner().getPerson().getFirst())) {ownerPersonJson.put("Fname", vehicle.getOwner().getPerson().getFirst());}
	    	    	if(vehicle.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(vehicle.getOwner().getPerson().getLast())) {ownerPersonJson.put("Sname", vehicle.getOwner().getPerson().getLast());}
	    	    	if(vehicle.getOwner().getPerson()!=null && StringUtils.isNoneEmpty(vehicle.getOwner().getPerson().getMiddle())) {ownerPersonJson.put("Mname", vehicle.getOwner().getPerson().getMiddle());}
	    	    	vehicleJson.put("Owner", ownerPersonJson);
	    		}
	    	
	    	}
	    	damagedVehicleList.add(vehicleJson);
    	}    	
    	
    	return damagedVehicleList;
    }
    
    private static JSONObject mapName(Name name) {    	
    	JSONObject nameJson = new JSONObject();
    	if(StringUtils.isNoneEmpty( name.getFirst())) {nameJson.put("FirstName", name.getFirst());}
    	if(StringUtils.isNoneEmpty(name.getMiddle())) {nameJson.put("MiddleName", name.getMiddle());}
    	if(StringUtils.isNoneEmpty(name.getLast())) {nameJson.put("LastName", name.getLast());}    	   	
    	return nameJson;
    }
    
    //mapOrganization
    private static JSONObject mapOrganization(String organisationName) {    	
    	JSONObject orgJson = new JSONObject();
    	if(StringUtils.isNoneEmpty( organisationName)) {orgJson.put("OrganisationName", organisationName);}
    	//if(StringUtils.isNoneEmpty( organisation.getOrgDescription())) {orgJson.put("OrgNameDescription", organisation.getOrgDescription());}
    	orgJson.put("pxObjClass", "Police-SDW-Data-Organisation");   	   	
    	return orgJson;
    }
    
    private static String formatDob(String dob) {
    	String formatedDOB="";
    	String fromPattern = "yyyy-MM-dd";
    	String toPattern = "yyyyMMdd";
    	SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromPattern);
    	SimpleDateFormat toDateFormat = new SimpleDateFormat(toPattern);
    	try {
    		formatedDOB=toDateFormat.format(fromDateFormat.parse(dob));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return formatedDOB;
    }
    
    private static String formatDob(String dob, String format) {
    	String formatedDOB="";
    	String fromPattern = "yyyy-MM-dd";
    	String toPattern = "yyyyMMdd";
    	if(StringUtils.isNoneEmpty(format)) {
    		toPattern = format;
    	}
    	SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromPattern);
    	SimpleDateFormat toDateFormat = new SimpleDateFormat(toPattern);
    	try {
    		formatedDOB=toDateFormat.format(fromDateFormat.parse(dob));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return formatedDOB;
    }
    private static JSONObject mapWhenHappened(WhenItHappened whenHappened) {   
    	String fromDateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    	String toDateTimePattern = "yyyyMMdd'T'HHmmss.SSS";
    	String fromDatePattern = "yyyy-MM-dd";
    	String toDatePattern = "yyyyMMdd";
    	String fromTimePattern = "HH:mm:ss";
    	String toTimePattern = "HHmmss";
    	SimpleDateFormat fromDateTimeFormat = new SimpleDateFormat(fromDateTimePattern);
    	fromDateTimeFormat.setTimeZone(TimeZone.getDefault());
    	SimpleDateFormat toDateTimeFormat = new SimpleDateFormat(toDateTimePattern);
    	toDateTimeFormat.setTimeZone(TimeZone.getDefault());
    	SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromDatePattern);
    	SimpleDateFormat toDateFormat = new SimpleDateFormat(toDatePattern);
    	SimpleDateFormat fromTimeFormat = new SimpleDateFormat(fromTimePattern);
    	SimpleDateFormat toTimeFormat = new SimpleDateFormat(toTimePattern);
    	JSONObject whenHappenedJson = new JSONObject();
    	whenHappenedJson.put("pxObjClass", "Police-SDW-Data-DateEntry");    	
    	try {
	    	
	    	if(StringUtils.isNoneEmpty(whenHappened.getStartDateTime())) {whenHappenedJson.put("FromDateTime",toDateTimeFormat.format(fromDateTimeFormat.parse(whenHappened.getStartDateTime())));}
	    	
	    	if(StringUtils.isNoneEmpty(whenHappened.getEndDateTime())) {whenHappenedJson.put("ToDateTime",toDateTimeFormat.format(fromDateTimeFormat.parse(whenHappened.getEndDateTime())));}
	    	
	    	if(StringUtils.isNoneEmpty(whenHappened.getStartDateTime())) {whenHappenedJson.put("FromDate",toDateFormat.format(fromDateTimeFormat.parse(whenHappened.getStartDateTime())));}
	    	
	    	if(StringUtils.isNoneEmpty(whenHappened.getEndDateTime())) {whenHappenedJson.put("ToDate",toDateFormat.format(fromDateTimeFormat.parse(whenHappened.getEndDateTime())));}
	    	
	    	if(StringUtils.isNoneEmpty(whenHappened.getStartDateTime())) {whenHappenedJson.put("FromTime",toTimeFormat.format(fromDateTimeFormat.parse(whenHappened.getStartDateTime())));}
	    	
	    	if(StringUtils.isNoneEmpty(whenHappened.getEndDateTime())) {whenHappenedJson.put("ToTime",toTimeFormat.format(fromDateTimeFormat.parse(whenHappened.getEndDateTime())));}
	    	
	    	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return whenHappenedJson;
    }
    
    private static JSONObject mapWhatHappened(WhatHappened whatHappened) {    	
    	JSONObject whatHappenedJson = new JSONObject();
    	whatHappenedJson.put("pxObjClass", "Police-SDW-Data-Narrative");
    	if(StringUtils.isNoneEmpty(whatHappened.getDescription())) {whatHappenedJson.put("NarrativeText",whatHappened.getDescription());} 
    	if(StringUtils.isNoneEmpty(whatHappened.getWhy())) {whatHappenedJson.put("ReasonWhy",whatHappened.getWhy());}
    	return whatHappenedJson;
    }
    
    //mapWhoAssaulted
    private static JSONObject mapWhoAssaulted(WhoAssaulted whoAssaulted) {    	
    	JSONObject whoAssaultedJson = new JSONObject();
    	whoAssaultedJson.put("pxObjClass", "Police-SDW-Data-Assault");
    	if(StringUtils.isNoneEmpty(whoAssaulted.getWho())) {whoAssaultedJson.put("WhoInjured",whoAssaulted.getWho());} 
    	if(StringUtils.isNoneEmpty(whoAssaulted.getHow())) {whoAssaultedJson.put("HowInjured",whoAssaulted.getHow());}
    	return whoAssaultedJson;
    }    
    private static JSONObject mapWhereHappened(WhereItHappened whereHappened) {    	
    	JSONObject whereHappenedJson = new JSONObject();
    	whereHappenedJson.put("pxObjClass", "Police-SDW-Data-LocationType");    	
    	if(StringUtils.isNoneEmpty(whereHappened.getLocationType())) {whereHappenedJson.put("LocationTypeIndicator", LocationType.valueOf(whereHappened.getLocationType()).getPegaLocationType());}
    	if(whereHappened!=null) {whereHappenedJson.put("LocationDetails", mapLocationDetails(whereHappened));}   	
    	return whereHappenedJson;
    }
    
    private static JSONObject mapLocationDetails(WhereItHappened whereItHappened) {    	
    	JSONObject locationDetailsJson = new JSONObject();
    	locationDetailsJson.put("pxObjClass", "Police-SDW-Data-Organisation");
    	locationDetailsJson.put("NotInLookUp", (whereItHappened.getAddress()!=null && !whereItHappened.getAddress().isCannotFind())?"false":"true");
    	if(StringUtils.isNoneEmpty(whereItHappened.getHowCloseToAddress())) {locationDetailsJson.put("Proximity", HowCloseToTheAddress.valueOf(whereItHappened.getHowCloseToAddress()).getPegaHowCloseToTheAddress());}
    	if(StringUtils.isNoneEmpty(whereItHappened.getAdditionalInformation())) {locationDetailsJson.put("AdditonalLocationInfo", whereItHappened.getAdditionalInformation());}
    	if(StringUtils.isNoneEmpty(whereItHappened.getLocationName())) {locationDetailsJson.put("LocationName", whereItHappened.getLocationName());}
    	if(whereItHappened.getAddress()!=null && whereItHappened.getAddress().getManual()!=null) {locationDetailsJson.put("FreeFormAddress", mapAddress(whereItHappened.getAddress().getManual()));}
    	return locationDetailsJson;
    }
    
    private static JSONObject mapAddress(Address address) {    	
    	JSONObject addressJson = new JSONObject();
    	addressJson.put("pxObjClass", "Police-SDW-Data-FreeFormAddress");
    	if(StringUtils.isNoneEmpty(address.getStreetName())) {addressJson.put("StreetName", address.getStreetName());}
    	if(StringUtils.isNoneEmpty(address.getStreetNumber())) {addressJson.put("StreetNumber", address.getStreetNumber());}
    	if(StringUtils.isNoneEmpty(address.getStreetType())) {addressJson.put("StreetType", address.getStreetType());}
    	if(StringUtils.isNoneEmpty(address.getSuburb())) {addressJson.put("TownSuburb", address.getSuburb());}
    	if(StringUtils.isNoneEmpty(address.getUnitType())) {addressJson.put("UnitType", UnitType.valueOf(address.getUnitType()).getPegaUnitType());}
    	if(StringUtils.isNoneEmpty(address.getUnitNumber())) {addressJson.put("UnitNumber", address.getUnitNumber());}
    	if(StringUtils.isNoneEmpty(address.getCountry())) {addressJson.put("Country", address.getCountry());}
    	if(StringUtils.isNoneEmpty(address.getCountry())) {addressJson.put("IsOverSee", ("New Zealand".equalsIgnoreCase(address.getCountry()))?"New Zealand":"International");}   	 
    	return addressJson;
    }  
    
    //mapWhoWasInvolved
    private static JSONObject mapWhoWasInvolved(WhoWasInvolved whoWasInvolved) {     	
    	JSONObject whoWasInvolvedJson = new JSONObject();
    	whoWasInvolvedJson.put("pxObjClass", "Police-SDW-Data-Offender");
    	whoWasInvolvedJson.put("KnownIndicator", (whoWasInvolved.isKnowsWho()) ? "Yes" : "No");
    	if(whoWasInvolved.getWho()!=null) {			
			if(StringUtils.isNoneEmpty(whoWasInvolved.getWho().getDescription())) {whoWasInvolvedJson.put("Description", whoWasInvolved.getWho().getDescription());}
			if(StringUtils.isNoneEmpty(whoWasInvolved.getWho().getRelationType())) {whoWasInvolvedJson.put("RelationshipToVictim", Relationship.valueOf(whoWasInvolved.getWho().getRelationType()).getPegaRelationship());}
			if(StringUtils.isNoneEmpty(whoWasInvolved.getWho().getRelationOther())) {whoWasInvolvedJson.put("OtherQuestion", whoWasInvolved.getWho().getRelationOther());}
		}
    	whoWasInvolvedJson.put("DescriptionQuestion", (whoWasInvolved.isKnowsContact()) ? "Yes" : "No");
		if(StringUtils.isNoneEmpty(whoWasInvolved.getContact())) {whoWasInvolvedJson.put("OffenderDescription", whoWasInvolved.getContact());}    		    		
	    
		whoWasInvolvedJson.put("SeenOffender", (whoWasInvolved.isKnowsVehicle()) ? "Yes" : "No");
		
		if(whoWasInvolved.getVehicle()!=null) {			
	    	if(StringUtils.isNoneEmpty(whoWasInvolved.getVehicle().getDescription())) {whoWasInvolvedJson.put("VehicleDescription", whoWasInvolved.getVehicle().getDescription());}
	    	if(StringUtils.isNoneEmpty(whoWasInvolved.getVehicle().getLicencePlate())) {whoWasInvolvedJson.put("VehicleRegistration", whoWasInvolved.getVehicle().getLicencePlate());}
		}		
		
		JSONObject witnessJson = new JSONObject();
		witnessJson.put("pxObjClass", "Police-SDW-Data-Witness");
		witnessJson.put("WitnessIndicator", (whoWasInvolved.isKnowsContact()) ? "Yes" : "No");
		if(StringUtils.isNoneEmpty(whoWasInvolved.getContact())) {witnessJson.put("NameContact", whoWasInvolved.getContact());}
		whoWasInvolvedJson.put("Witness", witnessJson);		
    	
		return whoWasInvolvedJson;
    }
    
    public static String mapPegaCreateRequest1(OnlineReportCaseRequest createOnlineReportRequest) {    	
    	String pegaCreateCaseRequest = new JSONObject()
                .put("caseTypeID", "Police-SDW-Work-OnlineReporting")
                .put("processID", "pyStartCase")
                .put("parentCaseID", "")
                .put("content", new JSONObject()
                     .put("EventTypeIndicator", mapEventTypeIndicator(createOnlineReportRequest.isNotAnEmergency(), createOnlineReportRequest.getReportType()))).toString();		
    	
    	return pegaCreateCaseRequest;
    	
    }
    public static String mapPegaCreateRequest_depricate(OnlineReportCaseRequest createOnlineReportRequest) {    	
    	String pegaCreateCaseRequest = new JSONObject()
                .put("caseTypeID", "Police-SDW-Work-OnlineReporting")
                .put("processID", "pyStartCase")
                .put("parentCaseID", "")
                .put("content", new JSONObject()
                     .put("EventTypeIndicator", new JSONObject()
                    	   .put("pxObjClass", "Police-SDW-Data-EventTypeIndicator")
                  		   .put("DoContinue", (createOnlineReportRequest.isNotAnEmergency())?"true":"false"))).toString();		
    	
    	return pegaCreateCaseRequest;
    	
    }
    
    public static JSONObject mapUpdateCaseRequest(OnlineReportCaseRequest updateCaseReportTypeRequest) {
    	JSONObject pegaUpdateCaseReportTypeRequest = new JSONObject();
    	JSONObject pegaUpdateCaseAnswerQuestionsRequest = new JSONObject();
    	pegaUpdateCaseAnswerQuestionsRequest.put("pxObjClass", "Police-SDW-Work-OnlineReporting");
    	
    	if(updateCaseReportTypeRequest.isNotAnEmergency() || updateCaseReportTypeRequest.getReportType()!=null) {
    		pegaUpdateCaseAnswerQuestionsRequest.put("EventTypeIndicator", mapEventTypeIndicator(updateCaseReportTypeRequest.isNotAnEmergency(), updateCaseReportTypeRequest.getReportType()));
    	}    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getWhenItHappened()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("DateEntry", mapWhenHappened(updateCaseReportTypeRequest.getEventInfo().getWhenItHappened()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getWhereItHappened()!=null) {
    		pegaUpdateCaseAnswerQuestionsRequest.put("LocationType", mapWhereHappened(updateCaseReportTypeRequest.getEventInfo().getWhereItHappened()));
    		pegaUpdateCaseAnswerQuestionsRequest.put("Stolenfromavehicleindicator",(updateCaseReportTypeRequest.getEventInfo().getWhereItHappened().isStolenFromVehicle())?"Yes":"No");
    		pegaUpdateCaseAnswerQuestionsRequest.put("Property", mapProperty(updateCaseReportTypeRequest.getEventInfo().getWhereItHappened().getLicencePlate(), updateCaseReportTypeRequest.getEventInfo().getWhereItHappened().getVehicleLocation()));
    	
    	}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null &&  updateCaseReportTypeRequest.getEventInfo().getLostItems()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Item", mapItem(updateCaseReportTypeRequest.getEventInfo().getLostItems(),  updateCaseReportTypeRequest.getContacts()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getDamagedItems()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Properties", mapProperties(updateCaseReportTypeRequest.getEventInfo().getDamagedItems(),  updateCaseReportTypeRequest.getContacts()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getWhatHappened()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Narrative", mapWhatHappened(updateCaseReportTypeRequest.getEventInfo().getWhatHappened()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getWhoWasHurt()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Assault", mapWhoAssaulted(updateCaseReportTypeRequest.getEventInfo().getWhoWasHurt()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getWhoWasInvolved()!=null) {pegaUpdateCaseAnswerQuestionsRequest.put("Offender", mapWhoWasInvolved(updateCaseReportTypeRequest.getEventInfo().getWhoWasInvolved()));}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getAdditionalInformation()!=null && StringUtils.isNoneEmpty(updateCaseReportTypeRequest.getEventInfo().getAdditionalInformation().getOtherEvidence())) {pegaUpdateCaseAnswerQuestionsRequest.put("CCTVFootageAttachmentDescription", updateCaseReportTypeRequest.getEventInfo().getAdditionalInformation().getOtherEvidence());}
    	
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getStolenItems()!=null) {
    		if(updateCaseReportTypeRequest.getEventInfo().getStolenItems()!=null && updateCaseReportTypeRequest.getEventInfo().getStolenItems().size()>0) {pegaUpdateCaseAnswerQuestionsRequest.put("Item", mapItem(updateCaseReportTypeRequest.getEventInfo().getStolenItems(),  updateCaseReportTypeRequest.getContacts()));}
    	}
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getStolenVehicles()!=null) {
    		pegaUpdateCaseAnswerQuestionsRequest.put("VehicleStolenIndicator",(updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().isAnyStolen())?"Yes":"No");
    		if(updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().getVehicles()!=null && updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().getVehicles().size()>0) {pegaUpdateCaseAnswerQuestionsRequest.put("StolenVehicle", mapStolenVehicle(updateCaseReportTypeRequest.getEventInfo().getStolenVehicles().getVehicles(),  updateCaseReportTypeRequest.getContacts()));}
    	}    	
    	
    	
    	if(updateCaseReportTypeRequest.getEventInfo()!=null && updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles()!=null) {
    		if(updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().isAnyDamaged()) {
    			pegaUpdateCaseAnswerQuestionsRequest.put("VehicleDamagedIndicator", "Yes");
    			if(updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().getVehicles()!=null && updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().getVehicles().size()>0) {pegaUpdateCaseAnswerQuestionsRequest.put("DamagedVehicle", mapDamagedVehicle(updateCaseReportTypeRequest.getEventInfo().getDamagedVehicles().getVehicles(),  updateCaseReportTypeRequest.getContacts()));}    			
    		}else {
    			pegaUpdateCaseAnswerQuestionsRequest.put("VehicleDamagedIndicator", "No");    			
    		}
    	}    	
    	
    	if(updateCaseReportTypeRequest.getContacts()!=null) {mapContact(pegaUpdateCaseAnswerQuestionsRequest, updateCaseReportTypeRequest.getContacts());}
    	
    	pegaUpdateCaseReportTypeRequest.put("content", pegaUpdateCaseAnswerQuestionsRequest);
    	
    	return pegaUpdateCaseReportTypeRequest;
    }
}
