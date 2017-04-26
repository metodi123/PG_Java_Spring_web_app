package pg.web.app.exception;

public class InvalidPostParametersException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidPostParametersException(){  
		
	} 
	
	public InvalidPostParametersException(String message){  
		super(message);
	} 
}