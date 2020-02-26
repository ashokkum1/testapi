package nzp.digital.portal.onlinereportstaticuiservice.services;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import nzp.digital.portal.onlinereportstaticuiservice.exception.InvalidRequestException;
import nzp.digital.portal.onlinereportstaticuiservice.exception.PegaException;
import nzp.digital.portal.onlinereportstaticuiservice.model.LookUpValue;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaAddressLookup;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaCase;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaItemLookup;
import nzp.digital.portal.onlinereportstaticuiservice.model.PegaVehicleMakeLookup;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.OnlineReportPega;
import nzp.digital.portal.onlinereportstaticuiservice.payload.OnlineReportCaseRequest;
import nzp.digital.portal.onlinereportstaticuiservice.payload.PegaCreateCaseResponse;
import nzp.digital.portal.onlinereportstaticuiservice.util.ModelConverter;
import nzp.digital.portal.onlinereportstaticuiservice.util.ModelMapper;
import org.json.JSONObject;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class ManageCaseService implements ManageCaseInterface {
	@Value("${pega.dev.userid}")
    private String pegaUserId;
	@Value("${pega.dev.password}")
    private String pegaUserPassword;
	@Value("${pega.dev.url}")
    private String pegaURL;

	private RestTemplate restTemplate;
	private ModelConverter modelConverter;

	@Autowired
	public ManageCaseService(RestTemplate restTemplate, ModelConverter modelConverter) {
		Assert.notNull(restTemplate, "RestTemplate injection must not be null");
		Assert.notNull(modelConverter, "ModelConverter injection must not be null");
		this.restTemplate = restTemplate;
		this.modelConverter = modelConverter;
	}

	public PegaCase createCase(OnlineReportCaseRequest createOnlineReportCaseRequest) {
		String pegaCreateOnlineReportCaseRequest = ModelMapper.mapPegaCreateRequest(createOnlineReportCaseRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String> (pegaCreateOnlineReportCaseRequest, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		final PegaCreateCaseResponse pegaCreateCaseResponse = restTemplate.postForObject(pegaURL+"/cases", entity, PegaCreateCaseResponse.class);

		System.out.println("responseBody: " + ModelMapper.javaToJson(pegaCreateCaseResponse));

		PegaCase pegaCase = ModelMapper.mapPegaCreateCaseResponse(pegaCreateCaseResponse);
		return pegaCase;
	}

	//commitOnlineFormCase(caseId)

	public PegaCase commitOnlineFormCase(String caseId) throws PegaException{
		String pegaUpdateCaseRequest="";
		String nextAssignmentId=getPegaCaseNextAssignmentId(caseId);
		System.out.println("nextAssignmentId: "+ nextAssignmentId);
		if(StringUtils.isEmpty(nextAssignmentId)) {
			throw new PegaException("Pega exception: nextAssignmentId not found for the case " + caseId);
		}else{
			pegaUpdateCaseRequest = "{}";
		}

		System.out.println("pegaUpdateCaseRequest: "+ pegaUpdateCaseRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String> (pegaUpdateCaseRequest, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		System.out.println(ModelMapper.javaToJson(entity));
		System.out.println("URL:"+ pegaURL+"/assignments/"+ nextAssignmentId+"?actionID=ShowSummary");
		PegaCreateCaseResponse pegaCreateCaseResponse = restTemplate.postForObject(pegaURL+"/assignments/"+ nextAssignmentId+"?actionID=ShowSummary", entity, PegaCreateCaseResponse.class);
		System.out.println("responseBody: " + ModelMapper.javaToJson(pegaCreateCaseResponse));
		PegaCase pegaCase = ModelMapper.mapPegaUpdateCaseResponse(caseId, pegaCreateCaseResponse);
		return pegaCase;
	}

	public PegaCase updateOnlineFormCase(String caseId, OnlineReportCaseRequest updateCaseReportTypeRequest) throws PegaException{
		String pegaUpdateCaseRequest="";
		String nextAssignmentId=getPegaCaseNextAssignmentId(caseId);
		System.out.println("nextAssignmentId: "+ nextAssignmentId);
		if(StringUtils.isEmpty(nextAssignmentId)) {
			throw new PegaException("Pega exception: nextAssignmentId not found for the case " + caseId);
		}else{
			System.out.println("mapUpdateCaseRequest");
			pegaUpdateCaseRequest = ModelMapper.mapUpdateCaseRequest(updateCaseReportTypeRequest).toString();
		}

		System.out.println("pegaUpdateCaseRequest: "+ pegaUpdateCaseRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String> (pegaUpdateCaseRequest, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		System.out.println(ModelMapper.javaToJson(entity));
		System.out.println("URL:"+ pegaURL+"/assignments/"+ nextAssignmentId+"?actionID=pyUpdateCaseDetails");
		PegaCreateCaseResponse pegaCreateCaseResponse = restTemplate.postForObject(pegaURL+"/assignments/"+ nextAssignmentId+"?actionID=pyUpdateCaseDetails", entity, PegaCreateCaseResponse.class);
		System.out.println("responseBody: " + ModelMapper.javaToJson(pegaCreateCaseResponse));
		PegaCase pegaCase = ModelMapper.mapPegaUpdateCaseResponse(caseId, pegaCreateCaseResponse);

		return pegaCase;
	}


	public PegaCase moveCaseToPreviousState(String caseId, String nextAssignmentId) throws PegaException{
		String pegaUpdateCaseForReportTypeRequest="";
		String actionId=getPegaCaseActionId(caseId);
		System.out.println("actionId: "+ actionId);
		if(StringUtils.isEmpty(actionId)) {
			throw new PegaException("Pega exception: actionId not found for the case " + caseId);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String> ("{}", headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		System.out.println(ModelMapper.javaToJson(entity));
		System.out.println("URL:"+ pegaURL+"/assignments/"+ nextAssignmentId+"?actionID="+"Previous");
		PegaCreateCaseResponse pegaCreateCaseResponse = restTemplate.postForObject(pegaURL+"/assignments/"+ nextAssignmentId+"?actionID="+"Previous", entity, PegaCreateCaseResponse.class);
		System.out.println("responseBody: " + ModelMapper.javaToJson(pegaCreateCaseResponse));
		PegaCase pegaCase = ModelMapper.mapPegaUpdateCaseResponse(caseId, pegaCreateCaseResponse);

		return pegaCase;
	}

	//cancelCase(caseId, nextAssignmentId)
	public PegaCase cancelCase(String caseId, String nextAssignmentId) throws PegaException{
		String actionId="pyChangeStage";
		String pegaCancelCaseRequest = ModelMapper.mapCancelCaseRequest();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String> (pegaCancelCaseRequest, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		System.out.println(ModelMapper.javaToJson(entity));
		System.out.println("URL:"+ pegaURL+"/assignments/"+ nextAssignmentId+"?actionID="+actionId);
		PegaCreateCaseResponse pegaCreateCaseResponse = restTemplate.postForObject(pegaURL+"/assignments/"+ nextAssignmentId+"?actionID="+actionId, entity, PegaCreateCaseResponse.class);
		System.out.println("responseBody: " + ModelMapper.javaToJson(pegaCreateCaseResponse));
		PegaCase pegaCase = ModelMapper.mapPegaUpdateCaseResponse(caseId, pegaCreateCaseResponse);

		return pegaCase;
	}


	private String getPegaCaseActionId(String caseId) {
		String actioId="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//HttpEntity<String> entity = new HttpEntity<String> (pegaUserDisableRequest, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		String pegaCase = restTemplate.getForObject(pegaURL+"/cases/"+ caseId, String.class);
		System.out.println("pegaCase: "+ pegaCase);
		//JSONObject pegaCaseJson = ModelMapper.stringToJson(pegaCase);
		JSONObject pegaCaseJson = new JSONObject(pegaCase);
		if(pegaCaseJson!=null && pegaCaseJson.optJSONArray("assignments")!=null) {
			JSONObject assignment = (JSONObject)pegaCaseJson.getJSONArray("assignments").get(0);
			if(assignment.optJSONArray("actions")!=null) {
				JSONObject action = (JSONObject)assignment.getJSONArray("actions").get(0);
				actioId = action.getString("ID");
			}
		}else {
			System.out.println("pegaCaseJson is null");
		}
		return actioId;
	}

	private String getPegaCaseNextAssignmentId(String caseId) {
		String nextAssignmentId="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//HttpEntity<String> entity = new HttpEntity<String> (pegaUserDisableRequest, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		System.out.println("Call pega for pegaCase: "+ pegaURL+"/cases/"+ caseId);
		String pegaCase = restTemplate.getForObject(pegaURL+"/cases/"+ caseId, String.class);
		System.out.println("pegaCase: "+ pegaCase);
		//JSONObject pegaCaseJson = ModelMapper.stringToJson(pegaCase);
		JSONObject pegaCaseJson = new JSONObject(pegaCase);
		if(pegaCaseJson!=null && pegaCaseJson.optJSONArray("assignments")!=null) {
			JSONObject assignment = (JSONObject)pegaCaseJson.getJSONArray("assignments").get(0);
			nextAssignmentId = assignment.getString("ID");

		}else {
			System.out.println("pegaCaseJson is null");
		}
		return nextAssignmentId;
	}

	public PegaCase updateCaseForReportType1(String caseId, String nextAssignmentId, OnlineReportCaseRequest updateCaseReportTypeRequest) throws PegaException{
		String pegaUpdateCaseForReportTypeRequest="";
		String actionId=getPegaCaseActionId(caseId);
		System.out.println("actionId: "+ actionId);
		if(StringUtils.isEmpty(actionId)) {
			throw new PegaException("Pega exception: actionId not found for the case " + caseId);
		}else if("SelectOptions".equalsIgnoreCase(actionId)){
			System.out.println("mapUpdateCaseReportTypeRequest");
			pegaUpdateCaseForReportTypeRequest = ModelMapper.mapUpdateCaseReportTypeRequest(updateCaseReportTypeRequest);
		}else if("AnswerQuestions".equalsIgnoreCase(actionId)){
			System.out.println("mapUpdateCaseAnswerQuestionsRequest");
			pegaUpdateCaseForReportTypeRequest = ModelMapper.mapUpdateCaseRequest(updateCaseReportTypeRequest).toString();
		}else if("EnterPersonalInfo".equalsIgnoreCase(actionId)){
			System.out.println("mapUpdateCaseEnterPersonalInfoRequest");
			pegaUpdateCaseForReportTypeRequest = ModelMapper.mapUpdateCaseEnterPersonalInfoRequest(updateCaseReportTypeRequest);
		}

		System.out.println("pegaUpdateCaseForReportTypeRequest: "+ pegaUpdateCaseForReportTypeRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String> (pegaUpdateCaseForReportTypeRequest, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(pegaUserId, pegaUserPassword));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		System.out.println(ModelMapper.javaToJson(entity));
		System.out.println("URL:"+ pegaURL+"/assignments/"+ nextAssignmentId+"?actionID="+actionId);
		PegaCreateCaseResponse pegaCreateCaseResponse = restTemplate.postForObject(pegaURL+"/assignments/"+ nextAssignmentId+"?actionID="+actionId, entity, PegaCreateCaseResponse.class);
		System.out.println("responseBody: " + ModelMapper.javaToJson(pegaCreateCaseResponse));
		PegaCase pegaCase = ModelMapper.mapPegaUpdateCaseResponse(caseId, pegaCreateCaseResponse);
//		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(pegaURL+"/assignments/"+ nextAssignmentId)
//                .queryParam("actionID", actionId);
//		ResponseEntity<String> responseEntity = restTemplate.exchange(
//                uriBuilder.toUriString(),
//                HttpMethod.POST,
//                entity,
//                String.class
//        );
//		System.out.println("Response from pega "+ ModelMapper.javaToJson(responseEntity));
//		if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            System.out.println("response received");
//            System.out.println(ModelMapper.javaToJson(responseEntity.getBody()));
//            //PegaCase pegaCase = ModelMapper.mapPegaCreateCaseResponse(responseEntity.getBody());
//    		return null;
//        } else {
//            System.out.println("error occurred");
//            System.out.println(responseEntity.getStatusCode());
//            throw new PegaException("Pega exception: error occurred in updating the case " + caseId);
//        }
		return pegaCase;
	}

	@Override
	public nzp.digital.portal.onlinereportstaticuiservice.model.gen.OnlineReportCaseRequest getCase(String caseId) {
		Map<String, String> params = new HashMap<>();
		params.put("caseId", caseId);

		log.info("Start to get case by id {}", caseId);
		OnlineReportPega pegaReport = restTemplate.getForObject(pegaURL + "/cases/{caseId}", OnlineReportPega.class, params);
		return this.modelConverter.convertToApiModel(pegaReport.getContent());
	}	
	
	public LookUpValue getLookUpValue(String referenceType, String search) {
		log.info("Start Pega lookup for {} and {}", referenceType, search);
		LookUpValue lookUpValue=null;
		switch(referenceType)
		{
		 case "ADDRESS":
			PegaAddressLookup pegaAddressLookup = restTemplate.getForObject(pegaURL + "/data/D_MasterLocationListAdvanced?searchValue="+search, PegaAddressLookup.class);
			System.out.println("Pega address search result " + pegaAddressLookup.getPxResultCount());
			log.info("Pega address lookup result size {}", pegaAddressLookup.getPxResults().size());
			lookUpValue = ModelMapper.mapPegaAddressLookupResponse(pegaAddressLookup);
			break;
		 case "VEHICLE_MAKE":
			PegaVehicleMakeLookup pegaVehicleMakeLookup = restTemplate.getForObject(pegaURL + "/data/D_NIASearchResults?ReferenceType=Make"+"&SearchText=" +search, PegaVehicleMakeLookup.class);
			System.out.println("Pega vehicle make search result " + pegaVehicleMakeLookup.getPxResultCount());
			log.info("Pega vehicle make lookup result size {}", pegaVehicleMakeLookup.getPxResults().size());
			lookUpValue = ModelMapper.mapPegaVehicleMakeLookupResponse(pegaVehicleMakeLookup);
			break;
		 case "DAMAGED_ITEM":
		 case "STOLEN_ITEM":
			 PegaItemLookup pegaItemLookup = restTemplate.getForObject(pegaURL + "/data/D_ItemsListParameterized?ItemName="+search , PegaItemLookup.class);
			System.out.println("Pega item search result " + pegaItemLookup.getPxResultCount());
			log.info("Pega item lookup result size {}", pegaItemLookup.getPxResults().size());
			lookUpValue = ModelMapper.mapPegaItemLookupResponse(pegaItemLookup);
			break;
		 default:
			 log.info("Reference Type {} is not supported", referenceType);
			 throw new InvalidRequestException("Lookup request is invalid. Reference Type "+ referenceType +" is not supported.");
		}		
		
		return lookUpValue;
		
	}
}
