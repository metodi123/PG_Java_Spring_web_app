package pg.web.app.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import pg.web.app.dao.AdminDAO;
import pg.web.app.dao.EmployeeDAO;
import pg.web.app.exception.InvalidUserParametersException;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.UserType;

@Service
public class UpdateProfileSettingsService {
	
	public static void updateUserPassword(String username, String password, UserType userType) throws InvalidUserParametersException, NoSuchAlgorithmException {
		
		UserParametersValidationService.validateParameter(password, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		
		String hashedPassword = null;
		
		hashedPassword = PasswordProcessingService.getHashedPassword(password, username);
		
		if(userType == UserType.Employee) {
			Employee employee = new Employee();
			
			EmployeeDAO employeeDAO = new EmployeeDAO();
			
			employee = employeeDAO.getUser(username);

			employee.setPassword(hashedPassword);
			
			employeeDAO.updateUser(employee);
		}
		else if(userType == UserType.Admin) {
			Admin admin = new Admin();
			
			AdminDAO adminDAO = new AdminDAO();
			
			admin = adminDAO.getUser(username);

			admin.setPassword(hashedPassword);
			
			adminDAO.updateUser(admin);
		}
	}
	
	public static void updateUserEmail(String username, String email, UserType userType) throws InvalidUserParametersException {
		
		UserParametersValidationService.validateParameter(email, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
	
		if(userType == UserType.Employee) {
			Employee employee = new Employee();
			
			EmployeeDAO employeeDAO = new EmployeeDAO();
			
			employee = employeeDAO.getUser(username);

			employee.setEmail(email);
			
			employeeDAO.updateUser(employee);
		}
		else if(userType == UserType.Admin) {
			Admin admin = new Admin();
			
			AdminDAO adminDAO = new AdminDAO();
			
			admin = adminDAO.getUser(username);

			admin.setEmail(email);
			
			adminDAO.updateUser(admin);
		}
	}

}
