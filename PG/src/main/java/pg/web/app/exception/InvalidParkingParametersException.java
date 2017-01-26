package pg.web.app.exception;

public class InvalidParkingParametersException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidParkingParametersException(){  
		
	} 
	
	public InvalidParkingParametersException(String message){  
		super(message);
	} 
}
