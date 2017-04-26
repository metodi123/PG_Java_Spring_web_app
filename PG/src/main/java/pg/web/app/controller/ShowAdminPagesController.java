package pg.web.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.dao.AdminDAO;
import pg.web.app.dao.AppPropertyDAO;
import pg.web.app.dao.EmployeeDAO;
import pg.web.app.dao.ParkingDAO;
import pg.web.app.dao.PostDAO;
import pg.web.app.model.Admin;
import pg.web.app.model.AppProperty;
import pg.web.app.model.Employee;
import pg.web.app.model.Parking;
import pg.web.app.model.Post;
import pg.web.app.model.User;

@Controller
@RequestMapping(value = "/admin")
public class ShowAdminPagesController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String homeAdmin(HttpServletRequest request, Model model,
		@RequestParam(name="count", required=false) Integer count,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "home-admin";
		}
		else {
			if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee) {
				return "redirect:/employee";
			}
			
			AdminDAO adminDAO = new AdminDAO();
			
			Admin admin = new Admin();
			
			admin = (Admin) request.getSession(false).getAttribute(User.CURRENT_USER);
			
			try {
				admin = adminDAO.getUser(admin.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			request.getSession().setAttribute(User.CURRENT_USER, admin);
			
			PostDAO postDAO = new PostDAO();
			
			List<Post> posts = new ArrayList<Post>();
			
			posts = postDAO.getAllPosts();
			
			List<Post> filteredPosts = new ArrayList<Post>();
			
			if(count == null) {
				count=5;
			}
			
			Iterator<Post> iterator = posts.iterator();
			
			int i=0;
			
			while(iterator.hasNext() && i<count) {
				filteredPosts.add(iterator.next());
				i++;
			}
			
			model.addAttribute("posts", filteredPosts);
			
			model.addAttribute("count", count);
			
			return "profile-main-admin";
		}
	}
	
	@RequestMapping(value = "/showEmployees", method = RequestMethod.GET)
	public String showEmployees(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<Employee> employees = new ArrayList<Employee>();
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employees = employeeDAO.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("employees", employees);
		
		return "show-employees-admin";
	}
	
	@RequestMapping(value = "/showAdmins", method = RequestMethod.GET)
	public String showAdmins(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<Admin> admins = new ArrayList<Admin>();
		
		AdminDAO adminDAO = new AdminDAO();
		
		try {
			admins = adminDAO.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("admins", admins);
		
		return "show-admins-admin";
	}
	
	@RequestMapping(value = "/showParkings", method = RequestMethod.GET)
	public String showParkings(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<Parking> parkings = new ArrayList<Parking>();
		
		ParkingDAO parkingDAO = new ParkingDAO();
		
		try {
			parkings = parkingDAO.getAllParkings();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("parkings", parkings);
		
		return "show-parkings-admin";
	}
	
	@RequestMapping(value = "/createEmployee", method = RequestMethod.GET)
	public String createEmployee(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "create-employee-data-admin";
	}
	
	@RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
	public String editEmployee(HttpServletRequest request, Model model,
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
		} catch (NoResultException e) {
			return "redirect:/error404";
		}
		
		model.addAttribute("employee", employee);
		
		return "edit-employee-data-admin";
	}
	
	@RequestMapping(value = "/createParking", method = RequestMethod.GET)
	public String createParking(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "create-parking-data-admin";
	}
	
	@RequestMapping(value = "/editParking", method = RequestMethod.GET)
	public String editParking(HttpServletRequest request, Model model,
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
		} catch (NoResultException e) {
			return "redirect:/error404";
		}
		
		model.addAttribute("parking", parking);
		
		List<Parking> parkings = new ArrayList<Parking>();
		
		try {
			parkings = parkingDAO.getAllParkings();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("parkings", parkings);
		
		AppPropertyDAO appPropertyDAO = new AppPropertyDAO();
		
		String mapsApiKey = appPropertyDAO.getAppProperty(AppProperty.MAPS_API_KEY).getValue();
		
		model.addAttribute("mapsApiKey", mapsApiKey);
		
		return "edit-parking-data-admin";
	}
	
	@RequestMapping(value = "/createPost", method = RequestMethod.GET)
	public String createPostAdmin(HttpServletRequest request, Model model,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "create-post-admin";
	}
	
	@RequestMapping(value = "/editPost", method = RequestMethod.GET)
	public String editPostAdmin(HttpServletRequest request, Model model,
		@RequestParam(name="id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		PostDAO postDAO = new PostDAO();
		
		post = postDAO.getPost(id);
		
		model.addAttribute("post", post);
		
		return "edit-post-admin";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}

		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}

		return "change-password-admin";
	}
	
	@RequestMapping(value = "/changeEmail", method = RequestMethod.GET)
	public String changeEmail(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "change-email-admin";
	}
	
}
