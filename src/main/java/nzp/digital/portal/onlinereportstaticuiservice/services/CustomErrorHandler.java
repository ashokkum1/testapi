package nzp.digital.portal.onlinereportstaticuiservice.services;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import nzp.digital.portal.onlinereportstaticuiservice.exception.PegaException;
import nzp.digital.portal.onlinereportstaticuiservice.exception.UserNotFoundException;
import nzp.digital.portal.onlinereportstaticuiservice.exception.UserUnauthorizedException;

public class CustomErrorHandler implements ResponseErrorHandler{
	
	 @Override
	  public void handleError(ClientHttpResponse response) throws IOException {
		 if(response.getStatusCode()!=null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
			 throw new UserUnauthorizedException("User unauthorized");
		 }
		 System.out.println("pega status code:"+response.getStatusCode());
		 throw new PegaException("Pega exception: error occurred in updating the case");
	    // your error handling here
	  }

	  @Override
	  public boolean hasError(ClientHttpResponse response) throws IOException {		  
		  return (
				  response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR 
		          || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	  }

}
