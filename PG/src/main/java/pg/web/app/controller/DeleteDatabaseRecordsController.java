package pg.web.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.dao.EmployeeDAO;
import pg.web.app.dao.ParkingDAO;
import pg.web.app.model.Admin;
import pg.web.app.model.User;

@Controller
public class DeleteDatabaseRecordsController {

	@RequestMapping(value = "/admin/deleteEmployee", method = RequestMethod.POST)
	public String deleteEmployee(HttpServletRequest request,
		@RequestParam("username") String username,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employeeDAO.deleteUser(username);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editEmployee";
		}

		return "redirect:/admin/showEmployees";
	}
	
	@RequestMapping(value = "/admin/deleteParking", method = RequestMethod.POST)
	public String deleteParking(HttpServletRequest request,
		@RequestParam("number") int number,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		ParkingDAO parkingDAO = new ParkingDAO();
		
		try {
			parkingDAO.deleteParking(number);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editParking";
		}

		return "redirect:/admin/showParkings";
	}
}
