package pg.web.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.dao.EmployeeDAO;
import pg.web.app.dao.FineDAO;
import pg.web.app.dao.ParkingDAO;
import pg.web.app.exception.InvalidParkingParametersException;
import pg.web.app.exception.InvalidUserParametersException;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.Fine;
import pg.web.app.model.Parking;
import pg.web.app.model.User;
import pg.web.app.service.ParkingParametersValidationService;
import pg.web.app.service.UserParametersValidationService;

@Controller
public class UpdateDatabaseRecordsController {

	@RequestMapping(value = "/employee/payFine", method = RequestMethod.POST)
	public String payFine(HttpServletRequest request,
		@RequestParam("id") int id,
		@RequestParam("amount") double amount,
		@RequestParam("paidBy") String paidBy,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(pg.web.app.model.User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Fine fine = new Fine();
		
		FineDAO fineDAO = new FineDAO();
		
		fine = fineDAO.getFine(id);
		
		if(fine.isPaid() == false){
			fine.setPaid(true);
			fine.getFinePaymentInfo().setPaidAmount(amount);
			fine.getFinePaymentInfo().setPaidBy(paidBy);
			
			fineDAO.payFine(fine);
		}
		else {
			return "redirect:/employee/";
		}
		
		return "redirect:/employee/";
	}
	
	@RequestMapping(value = "/admin/editEmployee", method = RequestMethod.POST)
	public String editEmployee(HttpServletRequest request,
		@RequestParam("firstName") String firstName,
		@RequestParam("lastName") String lastName,
		@RequestParam("email") String email,
		@RequestParam("parkingNumber") int parkingNumber,
		@RequestParam("username") String username,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Employee employee = new Employee();
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employee = employeeDAO.getUser(username);
		} catch (Exception e1) {
			return "redirect:/error404";
		}
	
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		employee.setParkingNumber(parkingNumber);
		
		try {
			UserParametersValidationService.validateUserParameters(employee, true);
		} catch (InvalidUserParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editEmployee";
		}
		
		try {
			employeeDAO.updateUser(employee);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editEmployee";
		}

		return "redirect:/admin/showEmployees";
	}
	
	@RequestMapping(value = "/admin/editParking", method = RequestMethod.POST)
	public String editParking(HttpServletRequest request,
		@RequestParam("address") String address,
		@RequestParam("hourlyTax") double hourlyTax,
		@RequestParam("number") int number,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Parking parking = new Parking();
		
		ParkingDAO parkingDAO = new ParkingDAO();
		
		try {
			parking = parkingDAO.getParking(number);
		} catch (Exception e1) {
			return "redirect:/error404";
		}
		
		parking.setAddress(address);
		parking.setHourlyTax(hourlyTax);
		
		try {
			ParkingParametersValidationService.validateParkingParameters(parking);
		} catch (InvalidParkingParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editParking";
		}
		
		try {
			parkingDAO.updateParking(parking);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editParking";
		}

		return "redirect:/admin/showParkings";
	}
	
}
