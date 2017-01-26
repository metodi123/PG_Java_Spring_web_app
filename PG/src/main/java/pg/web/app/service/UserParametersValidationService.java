package pg.web.app.service;

import org.springframework.stereotype.Service;

import pg.web.app.exception.InvalidUserParametersException;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;

@Service
public class UserParametersValidationService {
	
	public static final int LONG_PARAMETERS_MAX_LENGTH = 35;
	public static final int SHORT_PARAMETERS_MAX_LENGTH = 25;
	
	public static final int NUMBER_PARAMETERS_MIN_VALUE = 0;
	public static final int NUMBER_PARAMETERS_MAX_VALUE = 1000;
	
	public static void validateUserParameters(Employee employee, boolean ignoreUsernameAndPasswordValidation) throws InvalidUserParametersException {
		if(ignoreUsernameAndPasswordValidation == false) {
			validateParameter(employee.getUsername(), LONG_PARAMETERS_MAX_LENGTH);
			validateParameter(employee.getPassword(), LONG_PARAMETERS_MAX_LENGTH);
		}
		validateParameter(employee.getFirstName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(employee.getLastName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(employee.getEmail(), LONG_PARAMETERS_MAX_LENGTH);
		validateParameter(employee.getParkingNumber(), NUMBER_PARAMETERS_MIN_VALUE, NUMBER_PARAMETERS_MAX_VALUE);
	}
	
	public static void validateUserParameters(Admin admin, boolean ignoreUsernameAndPasswordValidation) throws InvalidUserParametersException {
		if(ignoreUsernameAndPasswordValidation == false) {
			validateParameter(admin.getUsername(), LONG_PARAMETERS_MAX_LENGTH);
			validateParameter(admin.getPassword(), LONG_PARAMETERS_MAX_LENGTH);
		}
		validateParameter(admin.getFirstName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(admin.getLastName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(admin.getEmail(), LONG_PARAMETERS_MAX_LENGTH);
	}
	
	public static void validateParameter(String parameter, int maxLength) throws InvalidUserParametersException {
		if(parameter.length() > maxLength) {
			throw new InvalidUserParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidUserParametersException("Empty parameter entered");
		}
	}
	
	public static void validateParameter(int parameter, int minValue, int maxValue) throws InvalidUserParametersException {
		if(parameter < minValue || parameter > maxValue) {
			throw new InvalidUserParametersException("Invalid parameter value");
		}
	}

}
