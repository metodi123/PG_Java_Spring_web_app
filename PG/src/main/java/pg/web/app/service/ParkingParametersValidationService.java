package pg.web.app.service;

import org.springframework.stereotype.Service;

import pg.web.app.exception.InvalidParkingParametersException;
import pg.web.app.model.Parking;

@Service
public class ParkingParametersValidationService {
	public static final int PARAMETERS_MAX_LENGTH = 70;
	public static final int PARAMETERS_LOCATION_MAX_LENGTH = 25;
	
	public static final int NUMBER_PARAMETERS_MIN_VALUE = 0;
	public static final int NUMBER_PARAMETERS_MAX_VALUE = 1000;
	
	public static void validateParkingParameters(Parking parking) throws InvalidParkingParametersException {
		validateParameter(parking.getNumber(), NUMBER_PARAMETERS_MIN_VALUE, NUMBER_PARAMETERS_MAX_VALUE);
		validateParameter(parking.getAddress(), PARAMETERS_MAX_LENGTH);
		validateParameter(parking.getHourlyTax(), NUMBER_PARAMETERS_MIN_VALUE, NUMBER_PARAMETERS_MAX_VALUE);
		validateParameter(parking.getLocation().getLatitude(), PARAMETERS_LOCATION_MAX_LENGTH);
		validateParameter(parking.getLocation().getLongitude(), PARAMETERS_LOCATION_MAX_LENGTH);
	}

	public static void validateParameter(String parameter, int maxLength) throws InvalidParkingParametersException  {
		if(parameter.length() > maxLength) {
			throw new InvalidParkingParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidParkingParametersException("Empty parameter entered");
		}
	}
	
	public static void validateParameter(int parameter, int minValue, int maxValue) throws InvalidParkingParametersException {
		if(parameter < minValue || parameter > maxValue) {
			throw new InvalidParkingParametersException("Invalid parameter value");
		}
	}
	
	public static void validateParameter(double parameter, int minValue, int maxValue) throws InvalidParkingParametersException {
		if(parameter < minValue || parameter > maxValue) {
			throw new InvalidParkingParametersException("Invalid parameter value");
		}
	}
}
