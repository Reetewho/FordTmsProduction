package co.th.ford.tms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.th.ford.tms.model.Carrier;
import co.th.ford.tms.model.Load;
import co.th.ford.tms.model.LoadStop;
import co.th.ford.tms.model.PaymentReport1;
import co.th.ford.tms.model.User;
import co.th.ford.tms.service.CarrierService;
import co.th.ford.tms.service.LoadService;
import co.th.ford.tms.service.LoadStopService;
import co.th.ford.tms.service.SetStopETAService;
import co.th.ford.tms.webservice.FindEntities;
import co.th.ford.tms.webservice.ProcessLoadRetrieve;

@Controller
@RequestMapping("/")
public class SearchLoadIDController {
	@Autowired
	CarrierService cservice;
	
	@Autowired
	LoadService lservice;
	
	@Autowired
	LoadStopService lsservice;
	
	@Autowired
	SetStopETAService sseservice;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	Environment environment;
	
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	
	/*
	 * This method will check that user have already login .
	 */
	public boolean checkAuthorization(HttpSession session) {
		if(session.getAttribute("S_FordUser") == null){			
			return false;
		}else {
			return true;
		}
	}
	
	/*
	 * This method will provide find by LoadID for process loadretrieve.
	 */
	@RequestMapping(value = { "/searchby-loadid-processloadRetrieve" }, method = RequestMethod.GET)
	public String findbyLoadIDProcessLoadRetrieve(HttpSession session,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		LocalDate today = LocalDate.now();	   
	    model.addAttribute("selectDate",  today);
		return "search-by-loadid-loadretrieve";
	}
	
	@RequestMapping(value = { "/searchby-loadid-processloadRetrieve" }, method = RequestMethod.POST)
	public String findbyLoadIDProcessLoadRetrieve(HttpSession session, @RequestParam String loadID, @RequestParam String selectDate, ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		Carrier carrier_1 = cservice.findCarrierByDate(getThaiDate(LocalDate.parse(selectDate, DateTimeFormat.forPattern("yyyy-MM-dd"))));	
		if(carrier_1==null) {
			carrier_1 = new Carrier();
			carrier_1.setStatus("N/A");			
			carrier_1.setCarrierCode("APTH");
			carrier_1.setLastUpdateUser(((User)session.getAttribute("S_FordUser")).getUsername());
			carrier_1.setLastUpdateDate(LocalDateTime.now());
			cservice.saveCarrier(carrier_1);
			carrier_1 = cservice.findCarrierByDate(selectDate);	
		}			
		if(!carrier_1.getStatus().equalsIgnoreCase("true")) {			
			FindEntities fin=new FindEntities();
			List<Load> loadList=fin.submit(environment.getRequiredProperty("webservice.Authorization"), environment.getRequiredProperty("webservice.SOAPAction"), carrier_1).getLoadList();
			if(carrier_1.getStatus().equalsIgnoreCase("true")) {				
				for(Load load : loadList){
					load.setLastUpdateUser(((User)session.getAttribute("S_FordUser")).getUsername());
					load.setLastUpdateDate(LocalDateTime.now());
					lservice.saveLoad(load);
				
				}
				if(loadList.size() >0) {
					carrier_1.setLastUpdateUser(((User)session.getAttribute("S_FordUser")).getUsername());
					carrier_1.setLastUpdateDate(LocalDateTime.now());
					cservice.updateCarrier(carrier_1);
				}
			}else {
				model.addAttribute("Error", "Find Entities  :" + carrier_1.getCarrierCode() + " Error : " + carrier_1.getErrorMessage());					
			}
		}
		
		
//		model.addAttribute("loadID", loadID);
//		model.addAttribute("selectDate", carrier_1.getLoadDate().toString( DateTimeFormat.forPattern("yyyy-MM-dd")));
//		List<Load> loads= lservice.findLoadByCarrierID(carrier_1.getCarrierID());
//		model.addAttribute("loads", loads);
//		return "search-by-loadid-loadretrieve";
		
//		List<Load> loads= lservice.findLoadByCarrierID(carrier.getCarrierID());
//		model.addAttribute("loadDate", carrier.getLoadDate().toString( DateTimeFormat.forPattern("yyyy-MM-dd") ));
//		model.addAttribute("loads", loads);
//		return "load-list";
		
		
		int getLoadID;
		getLoadID = Integer.parseInt(loadID);
		Load load = lservice.findLoadByID(getLoadID);
		Carrier carrier_2 = cservice.findCarrierByID(load.getCarrierID());	
		load.setCarrier(carrier_2);		
		if(load.getStatus().equalsIgnoreCase("N/A")) {			
			ProcessLoadRetrieve loadRetrieve=new ProcessLoadRetrieve(); 
			List<LoadStop> loadStopList=loadRetrieve.submit(environment.getRequiredProperty("webservice.Authorization"), environment.getRequiredProperty("webservice.SOAPAction"), load).getLoadStopList();
			
			if(load.getStatus().equalsIgnoreCase("true")) {
				for(LoadStop loadStop : loadStopList){
					loadStop.setLastUpdateUser(((User)session.getAttribute("S_FordUser")).getUsername());
					loadStop.setLastUpdateDate(LocalDateTime.now());
					lsservice.saveLoadStop(loadStop);
				}
				load.setStatus("Load");
				load.setLastUpdateUser(((User)session.getAttribute("S_FordUser")).getUsername());
				load.setLastUpdateDate(LocalDateTime.now());
				lservice.updateLoad(load);
			}else {
				model.addAttribute("Error", "Load Retrieve  :" + carrier_2.getCarrierCode()+" , Load ID : "+load.getSystemLoadID()+" Error : "+load.getErrorMessage());	
				
			}
			
		}
		List<LoadStop> loadStops= lsservice.findLoadStopByLoadID(getLoadID);
//		model.addAttribute("loadDate", selectDate);
		model.addAttribute("loadDate", selectDate);
		model.addAttribute("load", load);
		model.addAttribute("loadStops", loadStops);
//		return "search-by-loadid-loadretrieve";
		return "loadStop-list";
		
	}	
	
	
	/*
	 * This method will provide find by LoadID for process loadretrieve.
	 */
	@RequestMapping(value = { "/showtest" }, method = RequestMethod.GET)
	public String showtest(HttpSession session,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		LocalDate today = LocalDate.now();	   
	    model.addAttribute("selectDate",  today);
		return "test_post";
	}
	
	@RequestMapping(value = { "/showtest" }, method = RequestMethod.POST)
	public String showtest(HttpSession session, @RequestParam String loadID, @RequestParam String selectDate, ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		model.addAttribute("loadID",  loadID);
		LocalDate local_date;
		local_date =  LocalDate.parse(selectDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
		model.addAttribute("selectDate", local_date.toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		return "test_post";
	}
	
	public String getThaiDate(LocalDate date) {
		int plus543=0;
		if(environment.getRequiredProperty("local.datetime").equals("th"))plus543=543;
		return (date.getYear()+plus543)+"-"+date.getMonthOfYear()+"-"+date.getDayOfMonth();
	}
}
