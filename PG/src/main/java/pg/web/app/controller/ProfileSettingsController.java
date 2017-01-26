package pg.web.app.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.model.UserType;
import pg.web.app.service.PasswordProcessingService;
import pg.web.app.service.UpdateProfileSettingsService;

@Controller
public class ProfileSettingsController {
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(HttpServletRequest request,
		@RequestParam("password") String password,
		@RequestParam("newPassword") String newPassword,
		@RequestParam("newPasswordRepeat") String newPasswordRepeat,
		@RequestParam("username") String username,
		@RequestParam("currentPassword") String currentPassword,
		@RequestParam("userType") String userType,
		RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException {
		
		if(request.getSession(false) == null) {
			return "redirect:/";
		}
		
		String hashedPassword = null;
		
		try {
			hashedPassword = PasswordProcessingService.getHashedPassword(password, username);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(newPassword.equals(newPasswordRepeat) && hashedPassword.equals(currentPassword)) {
			try {
				if(userType.equals(UserType.Employee.toString())) {
					UpdateProfileSettingsService.updateUserPassword(username, newPassword, UserType.Employee);
					return "redirect:/employee";
				}
				else if(userType.equals(UserType.Admin.toString())) {
					UpdateProfileSettingsService.updateUserPassword(username, newPassword, UserType.Admin);
					return "redirect:/admin";
				}
				else {
					return "redirect:/error";
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
		}
		else {
			if(userType.equals(UserType.Employee.toString())) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/employee/changePassword";
			}
			else if(userType.equals(UserType.Admin.toString())) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/admin/changePassword";
			}
		}
		redirectAttributes.addAttribute("message", "DatabaseError");
		return "redirect:/error";
	}
	
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
	public String changeEmail(HttpServletRequest request,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			@RequestParam("username") String username,
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("userType") String userType,
			RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/";
		}
		
		String hashedPassword = null;
		
		try {
			hashedPassword = PasswordProcessingService.getHashedPassword(password, username);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

		if(hashedPassword.equals(currentPassword)) {
			try {
				if(userType.equals(UserType.Employee.toString())) {
					UpdateProfileSettingsService.updateUserEmail(username, email, UserType.Employee);
					return "redirect:/employee";
				}
				else if(userType.equals(UserType.Admin.toString())) {
					UpdateProfileSettingsService.updateUserEmail(username, email, UserType.Admin);
					return "redirect:/employee";
				}
				else {
					return "redirect:/error";
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
		}
		else {
			if(userType.equals(UserType.Employee.toString())) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/employee/changeEmail";
			}
			if(userType.equals(UserType.Admin.toString())) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/admin/changeEmail";
			}
		}
		redirectAttributes.addAttribute("message", "DatabaseError");
		return "redirect:/error";
	}
}
