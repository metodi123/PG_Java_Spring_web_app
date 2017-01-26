package pg.web.app.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.dao.EmployeeDAO;
import pg.web.app.dao.FineDAO;
import pg.web.app.dao.ParkingDAO;
import pg.web.app.exception.InvalidFineParametersException;
import pg.web.app.exception.InvalidParkingParametersException;
import pg.web.app.exception.InvalidUserParametersException;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.Fine;
import pg.web.app.model.Parking;
import pg.web.app.model.User;
import pg.web.app.service.FineParametersValidationService;
import pg.web.app.service.ParkingParametersValidationService;
import pg.web.app.service.PasswordProcessingService;
import pg.web.app.service.UserParametersValidationService;

@Controller
public class CreateDatabaseRecordsController {
	
	@RequestMapping(value = "/employee/newFine", method = RequestMethod.POST)
	public String newFine(HttpServletRequest request,
		@RequestParam("vehicleRegNum") String vehicleRegNum,
		@RequestParam("parkingNumber") int parkingNumber,
		@RequestParam("kindOfViolation") String kindOfViolation,
		@RequestParam("description") String description,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Fine fine = new Fine();
		
		fine.setVehicleRegNum(vehicleRegNum);
		fine.setParkingNumber(parkingNumber);
		fine.setKindOfViolation(kindOfViolation);
		fine.setDescription(description);
		
		try {
			FineParametersValidationService.validateFineParameters(fine);
		} catch (InvalidFineParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/newFine";
		}
		
		FineDAO fineDAO = new FineDAO();
		
		List<Fine> fines = new ArrayList<Fine>();
		
		fines = fineDAO.getAllFines();
		
		for(Fine f : fines) {
			if(f.getVehicleRegNum().equals(fine.getVehicleRegNum()) && f.isPaid() == false) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/employee/newFine";
			}
		}
		
		fineDAO.newFine(fine);

		return "redirect:/employee";
	}
	
	@RequestMapping(value = "/admin/createEmployee", method = RequestMethod.POST)
	public String createEmployee(HttpServletRequest request,
		@RequestParam("username") String username,
		@RequestParam("firstName") String firstName,
		@RequestParam("lastName") String lastName,
		@RequestParam("email") String email,
		@RequestParam("parkingNumber") int parkingNumber,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Employee employee = new Employee();
		
		String hashedPassword = null;
		
		try {
			hashedPassword = PasswordProcessingService.getHashedPassword(username, username);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		employee.setUsername(username);
		employee.setPassword(hashedPassword);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		employee.setParkingNumber(parkingNumber);
		
		try {
			UserParametersValidationService.validateUserParameters(employee, false);
		} catch (InvalidUserParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/createEmployee";
		}
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employeeDAO.createUser(employee);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/createEmployee";
		}

		return "redirect:/admin/showEmployees";
	}
	
	@RequestMapping(value = "/admin/createParking", method = RequestMethod.POST)
	public String createParking(HttpServletRequest request,
		@RequestParam("number") int number,
		@RequestParam("address") String address,
		@RequestParam("hourlyTax") double hourlyTax,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Parking parking = new Parking();
		
		parking.setNumber(number);
		parking.setAddress(address);
		parking.setHourlyTax(hourlyTax);
		
		try {
			ParkingParametersValidationService.validateParkingParameters(parking);
		} catch (InvalidParkingParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/createParking";
		}
		
		ParkingDAO parkingDAO = new ParkingDAO();
		
		try {
			parkingDAO.createParking(parking);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/createParking";
		}

		return "redirect:/admin/showParkings";
	}
	
}
