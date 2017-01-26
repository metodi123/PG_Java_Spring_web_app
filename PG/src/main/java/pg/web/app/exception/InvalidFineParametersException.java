package pg.web.app.exception;

public class InvalidFineParametersException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidFineParametersException(){  
		
	} 
	
	public InvalidFineParametersException(String message){  
		super(message);
	} 
}
