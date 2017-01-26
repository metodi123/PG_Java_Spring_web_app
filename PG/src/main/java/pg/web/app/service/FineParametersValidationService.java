package pg.web.app.service;

import org.springframework.stereotype.Service;

import pg.web.app.exception.InvalidFineParametersException;
import pg.web.app.model.Fine;

@Service
public class FineParametersValidationService {
	
	public static final int REG_NUMBER_MAX_LENGTH = 15;
	public static final int KIND_OF_VIOLATION_MAX_LENGTH = 70;
	public static final int DESCRIPTION_MAX_LENGTH = 150;
	
	public static final int NUMBER_PARAMETERS_MIN_VALUE = 0;
	public static final int NUMBER_PARAMETERS_MAX_VALUE = 1000;
	
	public static void validateFineParameters(Fine fine) throws InvalidFineParametersException {
		validateParameter(fine.getVehicleRegNum(), REG_NUMBER_MAX_LENGTH);
		validateParameter(fine.getParkingNumber(), NUMBER_PARAMETERS_MIN_VALUE, NUMBER_PARAMETERS_MAX_VALUE);
		validateParameter(fine.getKindOfViolation(), KIND_OF_VIOLATION_MAX_LENGTH);
		validateParameter(fine.getDescription(), DESCRIPTION_MAX_LENGTH);
	}

	public static void validateParameter(String parameter, int maxLength) throws InvalidFineParametersException {
		if(parameter.length() > maxLength) {
			throw new InvalidFineParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidFineParametersException("Empty parameter entered");
		}
	}
	
	public static void validateParameter(int parameter, int minValue, int maxValue) throws InvalidFineParametersException {
		if(parameter < minValue || parameter > maxValue) {
			throw new InvalidFineParametersException("Invalid parameter value");
		}
	}
}
