package nzp.digital.portal.onlinereportstaticuiservice.exception;

public class UnknownExceptionResponse {
	private String error;
	public UnknownExceptionResponse(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	

}
