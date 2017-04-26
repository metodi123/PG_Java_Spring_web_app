package pg.web.app.controller;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.dao.EmployeeDAO;
import pg.web.app.dao.FineDAO;
import pg.web.app.dao.ParkingDAO;
import pg.web.app.dao.PostDAO;
import pg.web.app.exception.InvalidFineParametersException;
import pg.web.app.exception.InvalidParkingParametersException;
import pg.web.app.exception.InvalidPostParametersException;
import pg.web.app.exception.InvalidUserParametersException;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.Fine;
import pg.web.app.model.Location;
import pg.web.app.model.Parking;
import pg.web.app.model.ParkingStatistics;
import pg.web.app.model.Post;
import pg.web.app.model.User;
import pg.web.app.service.FineParametersValidationService;
import pg.web.app.service.ParkingParametersValidationService;
import pg.web.app.service.PasswordProcessingService;
import pg.web.app.service.PostParametersValidationService;
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
		
		Parking parking = new Parking();
		
		ParkingDAO parkingDAO = new ParkingDAO();
		
		parking = parkingDAO.getParking(parkingNumber);
		
		int currentYear = LocalDateTime.now().getYear();
		Month currentMonth = LocalDateTime.now().getMonth();
		boolean recordIsFound = false;
		
		int currentVehicleCount = 0;
		
		for(ParkingStatistics parkingStatistics : parking.getParkingStatistics()) {
			if(parkingStatistics.getYear() == currentYear && parkingStatistics.getMonth().equalsIgnoreCase(currentMonth.toString())) {
				recordIsFound = true;
				parkingStatistics.setCurrentVehicleCount(parkingStatistics.getCurrentVehicleCount() + 1);
			}
		}
	    
	    if(recordIsFound == false) {
	    	for(ParkingStatistics parkingStatistics : parking.getParkingStatistics()) {
	    		if(parkingStatistics.getMonth().equalsIgnoreCase(Month.of(currentMonth.getValue()-1).toString())) {
					currentVehicleCount = parkingStatistics.getCurrentVehicleCount() + 1;
				}
			}
	    	ParkingStatistics parkingStatistics = new ParkingStatistics();
			parkingStatistics.setParkingNumber(parkingNumber);
			parkingStatistics.setCurrentVehicleCount(currentVehicleCount);
			parkingStatistics.setGainings(0);
			parkingStatistics.setPaidFinesCount(0);
			parkingStatistics.setYear(currentYear);
			parkingStatistics.setMonth(currentMonth.toString());
			
			parking.getParkingStatistics().add(parkingStatistics);
		}

	    parkingDAO.updateParking(parking);
	    
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
		@RequestParam("latitude") String latitude,
		@RequestParam("longitude") String longitude,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Parking parking = new Parking();
		
		Location location = new Location();
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		
		parking.setNumber(number);
		parking.setAddress(address);
		parking.setHourlyTax(hourlyTax);
		parking.setLocation(location);
		
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
	
	@RequestMapping(value = "/employee/createPost", method = RequestMethod.POST)
	public String createPostEmployee(Model model, HttpServletRequest request,
		@RequestParam("title") String title,
		@RequestParam("postText") String postText,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		post.setTitle(title);
		post.setText(postText);
		post.setAuthor((User)request.getSession(false).getAttribute(User.CURRENT_USER));
		
		try {
			PostParametersValidationService.validatePostParameters(post);
		} catch (InvalidPostParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createPost";
		}
		
		PostDAO postDAO = new PostDAO();
		
		try {
			postDAO.createPost(post);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createPost";
		}
		
		return "redirect:/employee";
	}
	
	@RequestMapping(value = "/admin/createPost", method = RequestMethod.POST)
	public String createPostAdmin(Model model, HttpServletRequest request,
		@RequestParam("title") String title,
		@RequestParam("postText") String postText,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		post.setTitle(title);
		post.setText(postText);
		post.setAuthor((User)request.getSession(false).getAttribute(User.CURRENT_USER));
		
		try {
			PostParametersValidationService.validatePostParameters(post);
		} catch (InvalidPostParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createPost";
		}
		
		PostDAO postDAO = new PostDAO();
		
		try {
			postDAO.createPost(post);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/createPost";
		}
		
		return "redirect:/admin";
	}
	
}
