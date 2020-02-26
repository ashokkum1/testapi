package nzp.digital.portal.onlinereportstaticuiservice.payload;

public class BusinessExceptionResponse {
	private String message;
	private String error;
	public BusinessExceptionResponse(String message, String error) {
		super();
		this.message = message;
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
