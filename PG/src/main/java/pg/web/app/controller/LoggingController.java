package pg.web.app.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.User;
import pg.web.app.model.UserType;
import pg.web.app.service.PasswordProcessingService;
import pg.web.app.service.UserValidationService;

@Controller
public class LoggingController {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("userType") String userType) {
		
		if(userType.equals(UserType.Employee.toString())) {
			Employee employee = new Employee();
			
			String hashedPassword = null;
			
			try {
				hashedPassword = PasswordProcessingService.getHashedPassword(password, username);
			} catch (Exception e1) {
				e1.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			employee.setUsername(username);
			employee.setPassword(hashedPassword);
			
			try {
				if(UserValidationService.isUserValid(employee)) {
					if(request.getSession(false) == null) {
						request.getSession().setAttribute(User.CURRENT_USER, employee);
						return "redirect:/employee";
					}
					else {
						redirectAttributes.addAttribute("message", "DatabaseError");
						return "redirect:/error";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "InvalidUser");
				return "home-employee";
			}
			
		}
		else if(userType.equals(UserType.Admin.toString())) {
			Admin àdmin = new Admin();
			
			String hashedPassword = null;
			
			try {
				hashedPassword = PasswordProcessingService.getHashedPassword(password, username);
			} catch (Exception e1) {
				e1.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			àdmin.setUsername(username);
			àdmin.setPassword(hashedPassword);
			
			try {
				if(UserValidationService.isUserValid(àdmin)) {
					if(request.getSession(false) == null) {
						request.getSession().setAttribute(User.CURRENT_USER, àdmin);
						return "redirect:/admin";
					}
					else {
						redirectAttributes.addAttribute("message", "DatabaseError");
						return "redirect:/error";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "InvalidUser");
				return "home-admin";
			} 
		}
		
		if(userType.equals(UserType.Employee.toString())) {
			model.addAttribute("message", "InvalidUser");
			return "home-employee";
		}
		else if(userType.equals(UserType.Admin.toString())) {
			model.addAttribute("message", "InvalidUser");
			return "home-admin";
		}
		else {
			model.addAttribute("message", "InvalidUser");
			return "home";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee) {
			request.getSession().invalidate();
			return "redirect:/employee";
		}
		if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin) {
			request.getSession().invalidate();
			return "redirect:/admin";
		}
		else {
			request.getSession().invalidate();
			return "redirect:/";
		}
	}

}
