package nzp.digital.portal.onlinereportstaticuiservice.controller;

import lombok.extern.log4j.Log4j2;
import nzp.digital.portal.onlinereportstaticuiservice.exception.InvalidRequestException;
import nzp.digital.portal.onlinereportstaticuiservice.exception.PegaException;
import nzp.digital.portal.onlinereportstaticuiservice.exception.UnknownExceptionResponse;
import nzp.digital.portal.onlinereportstaticuiservice.payload.BusinessExceptionResponse;
import nzp.digital.portal.onlinereportstaticuiservice.payload.OnlineReportCaseRequest;
import nzp.digital.portal.onlinereportstaticuiservice.services.ManageCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
@CrossOrigin
@RestController
@Validated
@RequestMapping(value="/api")
public class OnlineReportServiceController {
	private ManageCaseService manageCaseService;

	@Autowired
	public OnlineReportServiceController(ManageCaseService manageCaseService) {
		this.manageCaseService = manageCaseService;
	}

	@PostMapping("/onlinereport")
	public ResponseEntity<?> createOnlineReport(@RequestHeader MultiValueMap<String, String> headers, @Valid @RequestBody OnlineReportCaseRequest createOnlineReportCaseRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			List<String> message = new ArrayList<>();
			for (FieldError e : errors){
				message.add(e.getField().toUpperCase() + ":" + e.getDefaultMessage());
	        }
			return ResponseEntity.status(400).body(new BusinessExceptionResponse(message.toString(), "Request Validation Error"));
		}else {
			try {					
					return ResponseEntity.ok().body(manageCaseService.createCase(createOnlineReportCaseRequest));

			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body(new UnknownExceptionResponse(e.getMessage()));
			}
		}
	}

	@PutMapping("/onlinereport/{caseid}")
	public ResponseEntity<?> updateOnlineFormCase(@PathVariable("caseid") @NotBlank @Size(min = 5, max = 100) String caseId, @Valid @RequestBody OnlineReportCaseRequest updateCaseReportTypeRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			List<String> message = new ArrayList<>();
			for (FieldError e : errors){
				message.add(e.getField().toUpperCase() + ":" + e.getDefaultMessage());
	        }
			return ResponseEntity.status(400).body(new BusinessExceptionResponse(message.toString(), "Request Validation Error"));
		}else {
			try {

				return ResponseEntity.ok(manageCaseService.updateOnlineFormCase(caseId, updateCaseReportTypeRequest));
			}catch(PegaException e) {
				return ResponseEntity.status(500).body(new BusinessExceptionResponse(e.getMessage(),"Pega API error"));
			}catch(Exception e) {
				return ResponseEntity.status(500).body(new UnknownExceptionResponse(e.getMessage()));
			}
		}
	}

	@PostMapping("/onlinereport/{caseid}/submit")
	public ResponseEntity<?> commitOnlineFormCase(@PathVariable("caseid") @NotBlank @Size(min = 5, max = 100) String caseId, @Valid @RequestBody OnlineReportCaseRequest updateCaseReportTypeRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			List<String> message = new ArrayList<>();
			for (FieldError e : errors){
				message.add(e.getField().toUpperCase() + ":" + e.getDefaultMessage());
	        }
			return ResponseEntity.status(400).body(new BusinessExceptionResponse(message.toString(), "Request Validation Error"));
		}else {
			try {

				return ResponseEntity.ok(manageCaseService.commitOnlineFormCase(caseId));
			}catch(PegaException e) {
				return ResponseEntity.status(500).body(new BusinessExceptionResponse(e.getMessage(),"Pega API error"));
			}catch(Exception e) {
				return ResponseEntity.status(500).body(new UnknownExceptionResponse(e.getMessage()));
			}
		}
	}

	@PutMapping("/onlinereport/{caseid}/{nextassignmentid}/previousstate")
	public ResponseEntity<?> moveCaseToPreviousState(@PathVariable("caseid") @NotBlank @Size(min = 5, max = 100) String caseId, @PathVariable("nextassignmentid") @NotBlank @Size(min = 5, max = 100) String nextAssignmentId,@Valid @RequestBody OnlineReportCaseRequest updateCaseReportTypeRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			List<String> message = new ArrayList<>();
			for (FieldError e : errors){
				message.add(e.getField().toUpperCase() + ":" + e.getDefaultMessage());
	        }
			return ResponseEntity.status(400).body(new BusinessExceptionResponse(message.toString(), "Request Validation Error"));
		}else {
			try {

				return ResponseEntity.ok(manageCaseService.moveCaseToPreviousState(caseId, nextAssignmentId));
			}catch(PegaException e) {
				return ResponseEntity.status(500).body(new BusinessExceptionResponse(e.getMessage(),"Pega API error"));
			}catch(Exception e) {
				return ResponseEntity.status(500).body(new UnknownExceptionResponse(e.getMessage()));
			}
		}
	}

	@DeleteMapping("/onlinereport/{caseid}/{nextassignmentid}")
	public ResponseEntity<?> cancelCase(@PathVariable("caseid") @NotBlank @Size(min = 5, max = 100) String caseId, @PathVariable("nextassignmentid") @NotBlank @Size(min = 5, max = 100) String nextAssignmentId) {
		 	try {

				return ResponseEntity.ok(manageCaseService.cancelCase(caseId, nextAssignmentId));
			}catch(PegaException e) {
				return ResponseEntity.status(500).body(new BusinessExceptionResponse(e.getMessage(),"Pega API error"));
			}catch(Exception e) {
				return ResponseEntity.status(500).body(new UnknownExceptionResponse(e.getMessage()));
			}

	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleResourceNotFoundException(ConstraintViolationException e) {
         Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
         StringBuilder strBuilder = new StringBuilder();
         for (ConstraintViolation<?> violation : violations ) {
              strBuilder.append(violation.getMessage() + "\n");
         }
         return ResponseEntity.status(400).body(new BusinessExceptionResponse(strBuilder.toString(), "Request Validation Error"));

    }

	@GetMapping("/onlinereport/{caseid}")
	public ResponseEntity<nzp.digital.portal.onlinereportstaticuiservice.model.gen.OnlineReportCaseRequest> getCase(@PathVariable("caseid") @NotBlank @Size(min = 5, max = 100) String caseId) {
		try {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(manageCaseService.getCase(caseId));
		} catch (HttpStatusCodeException httpException) {
			log.error("Http Error {} happened when getting case by id {}: {}", httpException.getStatusCode(), caseId, httpException.getMessage());
			throw new ResponseStatusException(httpException.getStatusCode(), httpException.getMessage(), httpException);
		} catch(Exception e) {
			log.error("Internal Error happened when getting case by id {}: {}", caseId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/onlinereport/lookup")
	public ResponseEntity<?> getLookUpValue(@RequestParam("referenceType")  @NotBlank @Size(min = 5, max = 100) String referenceType, @RequestParam("search")  @NotBlank @Size(min = 1, max = 150) String search) {
		
			try {
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(manageCaseService.getLookUpValue(referenceType, search));
			} catch (InvalidRequestException invalidRequestException) {
				log.error("Error when getting lookUpValue for referenceType {} and search {} : {}",  referenceType, search, invalidRequestException.getMessage());
				//throw new ResponseStatusException(HttpStatus.BAD_REQUEST, invalidRequestException.getMessage(), invalidRequestException);
				return ResponseEntity.status(400).body(new BusinessExceptionResponse(invalidRequestException.getMessage(), "Request Validation Error"));
			}catch (HttpStatusCodeException httpException) {
				log.error("Http Error {} happened when getting lookUpValue for referenceType {} and search {} : {}", httpException.getStatusCode(), referenceType, search, httpException.getMessage());
				throw new ResponseStatusException(httpException.getStatusCode(), httpException.getMessage(), httpException);
			} catch(Exception e) {
				log.error("Internal Error happened when getting lookUpValue for referenceType {} and search {}: {}", referenceType, search, e.getMessage());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
			}
		
	}
}
