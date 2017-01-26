package pg.web.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pg.web.app.dao.FineDAO;
import pg.web.app.dao.ParkingDAO;
import pg.web.app.exception.InvalidFineParametersException;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;
import pg.web.app.model.Fine;
import pg.web.app.model.Parking;
import pg.web.app.model.User;
import pg.web.app.service.FineParametersValidationService;

@Controller
public class ShowPagesController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

		if(request.getSession(false) == null) {
			return "home";
		}
		else {
			if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Employee) {
				return "redirect:/employee";
			}
			else if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin) {
				return "redirect:/admin";
			}
			else {
				request.getSession().invalidate();
				return "home";
			}
		}
	}
	
	@RequestMapping(value = "/showFineInfo", method = RequestMethod.GET)
	public String showFineInfo(HttpServletRequest request, Model model,
		@RequestParam("regNum") String regNum,
		RedirectAttributes redirectAttributes) {
		
		try {
			FineParametersValidationService.validateParameter(regNum, FineParametersValidationService.REG_NUMBER_MAX_LENGTH);
		} catch (InvalidFineParametersException e) {
			return "redirect:/error404";
		}
		
		List<Fine> fines = new ArrayList<Fine>();
		
		FineDAO fineDAO = new FineDAO();
		
		fines = fineDAO.getAllFines();
		
		Fine fine = new Fine();
		
		for(Fine f : fines) {
			if(f.getVehicleRegNum().equals(regNum) && f.isPaid() == false) {
				fine = f;
			}
		}
		
		if(fine.getId() == 0) {
			redirectAttributes.addAttribute("message", "InvalidData");
			return "redirect:/";
		}
		
		Parking parking = new Parking();
		
		ParkingDAO parkingDAO = new ParkingDAO();
		
		parking = parkingDAO.getParking(fine.getParkingNumber());
		
		model.addAttribute("fine", fine);
		model.addAttribute("parking", parking);
		
		return "show-fine-info";
	}
	
}
