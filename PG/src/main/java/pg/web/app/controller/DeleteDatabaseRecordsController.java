package pg.web.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.dao.EmployeeDAO;
import pg.web.app.dao.ParkingDAO;
import pg.web.app.dao.PostDAO;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.Parking;
import pg.web.app.model.Post;
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
		
		Parking parking = new Parking();
		
		ParkingDAO parkingDAO = new ParkingDAO();
		
		try {
			parking = parkingDAO.getParking(number);
		} catch (Exception e1) {
			return "redirect:/error404";
		}
		
		try {
			parkingDAO.deleteParking(parking);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editParking";
		}

		return "redirect:/admin/showParkings";
	}
	
	@RequestMapping(value = "/employee/deletePost", method = RequestMethod.POST)
	public String editPostEmployee(Model model, HttpServletRequest request,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		PostDAO postDAO = new PostDAO();
		
		post.setId(id);
		
		try {
			postDAO.deletePost(post);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/editPost";
		}

		return "redirect:/employee";
	}
	
	@RequestMapping(value = "/admin/deletePost", method = RequestMethod.POST)
	public String editPostAdmin(Model model, HttpServletRequest request,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		PostDAO postDAO = new PostDAO();
		
		post.setId(id);
		
		try {
			postDAO.deletePost(post);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/editPost";
		}
		
		return "redirect:/admin";
	}
	
}
