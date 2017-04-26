package pg.web.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

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
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.Fine;
import pg.web.app.model.Parking;
import pg.web.app.model.ParkingStatistics;
import pg.web.app.model.Post;
import pg.web.app.model.User;

@Controller
@RequestMapping(value = "/employee")
public class ShowEmployeePagesController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String homeEmployee(HttpServletRequest request, Model model,
		@RequestParam(name="count", required=false) Integer count,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "home-employee";
		}
		else {
			if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin) {
				return "redirect:/admin";
			}
			
			EmployeeDAO employeeDAO = new EmployeeDAO();
			
			Employee employee = new Employee();
			
			employee = (Employee) request.getSession(false).getAttribute(User.CURRENT_USER);
			
			try {
				employee = employeeDAO.getUser(employee.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			request.getSession().setAttribute(User.CURRENT_USER, employee);
			
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
			
			return "profile-main-employee";
		}
	}
	
	@RequestMapping(value = "/showUnpaidFines", method = RequestMethod.GET)
	public String showUnpaidFines(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		List<Fine> fines = new ArrayList<Fine>();
		
		FineDAO fineDAO = new FineDAO();
		
		try {
			fines = fineDAO.getAllFines();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		List<Fine> unpaidFines = new ArrayList<Fine>();
		
		for(Fine f : fines) {
			if(f.isPaid() == false) {
				unpaidFines.add(f);
			}
		}
		
		model.addAttribute("unpaidFines", unpaidFines);
		
		return "show-unpaid-fines-employee";
	}
	
	@RequestMapping(value = "/showPaidFines", method = RequestMethod.GET)
	public String showPaidFines(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		List<Fine> fines = new ArrayList<Fine>();
		
		FineDAO fineDAO = new FineDAO();
		
		try {
			fines = fineDAO.getAllFines();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		List<Fine> paidFines = new ArrayList<Fine>();
		
		for(Fine f : fines) {
			if(f.isPaid() == true) {
				paidFines.add(f);
			}
		}
		
		model.addAttribute("paidFines", paidFines);
		
		return "show-paid-fines-employee";
	}
	
	@RequestMapping(value = "/newFine", method = RequestMethod.GET)
	public String newFine(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		return "new-fine-employee";
	}
	
	@RequestMapping(value = "/showEmployees", method = RequestMethod.GET)
	public String showEmployees(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
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
		
		return "show-employees-employee";
	}
	
	@RequestMapping(value = "/payFine", method = RequestMethod.GET)
	public String payFine(HttpServletRequest request, Model model,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Fine fine = new Fine();
		
		FineDAO fineDAO = new FineDAO();
		
		fine = fineDAO.getFine(id);

		model.addAttribute("fine", fine);
		
		return "pay-fine-employee";
	}
	
	@RequestMapping(value = "/parkingStatistics", method = RequestMethod.GET)
	public String showParkingStatisticsEmployee(HttpServletRequest request, Model model,
		@RequestParam(name="year", required=false) Integer year,
		@RequestParam(name="month", required=false) String month,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
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
		
		TreeSet<Integer> statisticsYears = new TreeSet<Integer>();
		
		for(Parking parking : parkings) {
			for(ParkingStatistics parkingStatistics : parking.getParkingStatistics()) {
				statisticsYears.add(parkingStatistics.getYear());
			}
		}
		
		model.addAttribute("statisticsYears", statisticsYears);
		
		model.addAttribute("parkings", parkings);
		
		if(year == null) {
			year = LocalDateTime.now().getYear();
		}
		if(month == null) {
			month = LocalDateTime.now().getMonth().toString();
		}
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
			
		return "parking-statistics-employee";
	}
	
	@RequestMapping(value = "/createPost", method = RequestMethod.GET)
	public String createPostEmployee(HttpServletRequest request, Model model,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		return "create-post-employee";
	}
	
	@RequestMapping(value = "/editPost", method = RequestMethod.GET)
	public String editPostEmployee(HttpServletRequest request, Model model,
		@RequestParam(name="id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		PostDAO postDAO = new PostDAO();
		
		post = postDAO.getPost(id);
		
		model.addAttribute("post", post);
		
		return "edit-post-employee";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}

		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}

		return "change-password-employee";
	}
	
	@RequestMapping(value = "/changeEmail", method = RequestMethod.GET)
	public String changeEmail(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		return "change-email-employee";
	}
	
}
