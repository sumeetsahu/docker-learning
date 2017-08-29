package hello;

enum ERRORCODE
{
	SUCCESS,
	ERROR,
	EXCEPTION	
};

public class Response {


	
	private ERRORCODE ErrorCode;
	
	private String ErrorMessage;
	
	public Response(ERRORCODE errorCode)
	{
		
		setErrorCode(errorCode);
		
		switch (errorCode) {
		case SUCCESS:
			setErrorMessage("Success");
			break;
		
		case ERROR:
			setErrorMessage("Unknown ERROR");
			break;
			
		case EXCEPTION:
			setErrorMessage("Unknown Exception");
			break;

		default:
			setErrorMessage("");
			break;
		}
		
		
		
	}
	
	public Response(ERRORCODE errorCode, String errorMessage) {
		setErrorCode(errorCode);
		setErrorMessage(errorMessage);
	}

	public ERRORCODE getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(ERRORCODE errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	
}
