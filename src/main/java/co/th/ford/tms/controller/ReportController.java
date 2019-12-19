package co.th.ford.tms.controller;



import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import co.th.ford.tms.model.Report1;
import co.th.ford.tms.service.CarrierService;


@Controller
@RequestMapping("/")
public class ReportController {

	
	
	@Autowired
	CarrierService cservice;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	Environment environment;
		
	/*
	 * This method will list all existing Carrier.
	 */
	@RequestMapping(value = {"/report" }, method = RequestMethod.GET)
	public String login(HttpSession session,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		LocalDate today = LocalDate.now();	   
	    LocalDate yesterday = today.minusDays(1);
		//List<Employee> employees = service.findAllEmployees();
		//model.addAttribute("employees", employees);
	    List<Report1> report = cservice.findAll(getThaiDate(yesterday),getThaiDate(yesterday));
		model.addAttribute("report", report);
	    model.addAttribute("startDate",  yesterday);
		model.addAttribute("endDate", today);
		return "report1";
	}
	
	/*
	 * This method will provide the medium to update an existing employee.
	 */
	@RequestMapping(value = { "/report" }, method = RequestMethod.POST)
	public String login(HttpSession session,@RequestParam String startDate,@RequestParam String endDate, ModelMap model){
		if(!checkAuthorization(session))return "redirect:/login";
		String nextPage="report1";
		model.addAttribute("startDate",  startDate);
		model.addAttribute("endDate", endDate);
		if(startDate.trim().equals("") ) {			
		    model.addAttribute("errorMsg",  messageSource.getMessage("NotEmpty.report.startDate", new String[]{startDate}, Locale.getDefault()));
		}else if(endDate.trim().equals("")) {			
		    model.addAttribute("errorMsg",  messageSource.getMessage("NotEmpty.report.endDate", new String[]{endDate}, Locale.getDefault()));
		}else {
			LocalDate startlocalDate =LocalDate.now();
			try {
				
				startlocalDate = LocalDate.parse(startDate);				
			}catch(Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg",  messageSource.getMessage("InvalidFormatDate.report.startDate", new String[]{startDate}, Locale.getDefault()));
			}
			if(model.get("errorMsg") ==null) {
				try {
					
					LocalDate endlocalDate = LocalDate.parse(endDate);						
					List<Report1> report = cservice.findAll(getThaiDate(startlocalDate), getThaiDate(endlocalDate));
					//List<Report1> report = cservice.findAll(startlocalDate.toString( DateTimeFormat.forPattern("yyyy-MM-dd")), endlocalDate.toString( DateTimeFormat.forPattern("yyyy-MM-dd")));
					model.addAttribute("report", report);
				}catch(Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg",  messageSource.getMessage("InvalidFormatDate.report.endDate", new String[]{endDate}, Locale.getDefault()));
				}
			}
		}
			
		return nextPage;
	}
	
	public boolean checkAuthorization(HttpSession session) {
		if(session.getAttribute("S_FordUser") == null){			
			return false;
		}else {
			return true;
		}
	}
	
	public String getThaiDate(LocalDate date) {
		int plus543=0;
		if(environment.getRequiredProperty("local.datetime").equals("th"))plus543=543;
		return (date.getYear()+plus543)+"-"+date.getMonthOfYear()+"-"+date.getDayOfMonth();
	}
}
