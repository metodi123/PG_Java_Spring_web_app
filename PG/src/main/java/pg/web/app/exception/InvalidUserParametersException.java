package pg.web.app.exception;

public class InvalidUserParametersException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidUserParametersException(){  
		
	} 
	
	public InvalidUserParametersException(String message){  
		super(message);
	}
}
