package co.th.ford.tms.controller;


import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.th.ford.tms.model.Carrier;
import co.th.ford.tms.model.Load;
import co.th.ford.tms.model.LoadStop;
import co.th.ford.tms.model.SetStopETA;
import co.th.ford.tms.service.CarrierService;
import co.th.ford.tms.service.LoadService;
import co.th.ford.tms.service.LoadStopService;
import co.th.ford.tms.service.SetStopETAService;
import co.th.ford.tms.webservice.FindEntities;
import co.th.ford.tms.webservice.ProcessLoadRetrieve;
import co.th.ford.tms.webservice.ProcessLoadStatusUpdate;
import co.th.ford.tms.webservice.ProcessSetStopETA;


@Controller
@RequestMapping("/")
public class CarrierController {

	
	
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
	/*
	 * This method will list all existing Carrier.
	 */
	@RequestMapping(value = {"/carrier-list" }, method = RequestMethod.GET)
	public String listCarrier(HttpSession session,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		List<Carrier> carriers = cservice.findAllCarriers();
		model.addAttribute("carriers", carriers);
		return "carrierList";
	}
	
	public boolean checkAuthorization(HttpSession session) {
		if(session.getAttribute("S_FordUser") == null){			
			return false;
		}else {
			return true;
		}
	}
	
	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/add-carrier" }, method = RequestMethod.GET)
	public String addCarrier(HttpSession session,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		Carrier  carrier = new Carrier();
		carrier.setStatus("N/A");
		carrier.setCarrierCode(environment.getRequiredProperty("default.carrierID"));
		carrier.setLoadDate(LocalDateTime.now());
		model.addAttribute("carrier",  carrier);
		model.addAttribute("edit", false);
		return "carrierForm";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/add-carrier" }, method = RequestMethod.POST)
	public String saveEmployee(HttpSession session,@Valid Carrier carrier, BindingResult result, ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		if (result.hasErrors()) {
			return "carrierForm";
		}		
		
		cservice.saveCarrier(carrier);

		model.addAttribute("success", " Carrier : " + carrier.getCarrierCode()+ ", Date :"+carrier.getLoadDate()+" added successfully");
		model.addAttribute("backToPage", "List Of All Carriers");
		model.addAttribute("urlToPage", "carrier-list");
		return "success";
	}


	/*
	 * This method will provide the medium to update an existing employee.
	 */
	@RequestMapping(value = { "/edit-{carrierID}-carrier" }, method = RequestMethod.GET)
	public String editEmployee(HttpSession session,@PathVariable int carrierID, ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		Carrier carrier = cservice.findCarrierByID(carrierID);
		carrier.setStatus("N/A");
		model.addAttribute("carrier", carrier);
		model.addAttribute("edit", true);
		return "carrierForm";
	}
	
	/*
	 * This method will be called on form submission, handling POST request for
	 * updating employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{carrierID}-carrier" }, method = RequestMethod.POST)
	public String updateEmployee(HttpSession session,@Valid Carrier carrier, BindingResult result,
			ModelMap model, @PathVariable String carrierID) {
		if(!checkAuthorization(session))return "redirect:/login";

		if (result.hasErrors()) {
			return "carrierForm";
		}		
		
		cservice.updateCarrier(carrier);

		model.addAttribute("success", "Carrier : " +  carrier.getCarrierCode()+ ", Date :"+carrier.getLoadDate()	+ " updated successfully");
		model.addAttribute("backToPage", "List Of All Carriers");
		model.addAttribute("urlToPage", "carrier-list");
		return "success";
	}

	
	/*
	 * This method will delete an employee by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{carrierID}-carrier" }, method = RequestMethod.GET)
	public String deleteCarrier(HttpSession session,@PathVariable int carrierID) {
		if(!checkAuthorization(session))return "redirect:/login";
		cservice.deleteCarrierByID(carrierID);
		return "redirect:/carrier-list";
	}
	
	@RequestMapping(value = { "/load-list/{carrierCode}-{carrierID}" }, method = RequestMethod.GET)
	public String loadList(HttpSession session,@PathVariable int carrierID,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		Carrier carrier = cservice.findCarrierByID(carrierID);		
		if(!carrier.getStatus().equalsIgnoreCase("true")) {			
			FindEntities fin=new FindEntities();
			List<Load> loadList=fin.submit(environment.getRequiredProperty("webservice.Authorization"), environment.getRequiredProperty("webservice.SOAPAction"), carrier).getLoadList();
			if(carrier.getStatus().equalsIgnoreCase("true")) {
				for(Load load : loadList){
					lservice.saveLoad(load);
				}
				cservice.updateCarrier(carrier);
			}else {
				model.addAttribute("success", "Find Entities  :"+carrier.getCarrierCode()+" Error : "+carrier.getErrorMessage());
				model.addAttribute("backToPage", "List Of All Carrier " );
				model.addAttribute("urlToPage", "carrier-list");
				return "success";
			}
		}
		List<Load> loads= lservice.findLoadByCarrierID(carrierID);
		model.addAttribute("carrierCode", carrier.getCarrierCode());
		model.addAttribute("loads", loads);
		return "loadList";
	}
	
	@RequestMapping(value = { "/loadStop-list/{carrierCode}-{systemLoadID}-{loadID}" }, method = RequestMethod.GET)
	public String loadStopList(HttpSession session,@PathVariable int loadID,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		Load load =lservice.findLoadByID(loadID);
		Carrier carrier = cservice.findCarrierByID(load.getCarrierID());	
		load.setCarrier(carrier);		
		if(!load.getStatus().equalsIgnoreCase("true")) {			
			ProcessLoadRetrieve loadRetrieve=new ProcessLoadRetrieve(); 
			List<LoadStop> loadStopList=loadRetrieve.submit(environment.getRequiredProperty("webservice.Authorization"), environment.getRequiredProperty("webservice.SOAPAction"), load).getLoadStopList();
			
			if(load.getStatus().equalsIgnoreCase("true")) {
				for(LoadStop loadStop : loadStopList){
					lsservice.saveLoadStop(loadStop);
				}
				lservice.updateLoad(load);
			}else {
				model.addAttribute("success", "Load Retrieve  :"+carrier.getCarrierCode()+" => Load ID : "+load.getSystemLoadID()+" Error : "+load.getErrorMessage());
				model.addAttribute("backToPage", "List Of All Carrier  :"+carrier.getCarrierCode() );
				model.addAttribute("urlToPage", "load-list/"+carrier.getCarrierCode()+"-"+carrier.getCarrierID());
				return "success";
			}
			
		}
		List<LoadStop> loadStops= lsservice.findLoadStopByLoadID(loadID);
		model.addAttribute("load", load);
		model.addAttribute("loadStops", loadStops);
		return "loadStopList";
	}
	
	@RequestMapping(value = { "/setStopETA/{carrierCode}-{systemLoadID}-{shippingLocation}-{loadStopID}" }, method = RequestMethod.GET)
	public String setStopETA(HttpSession session,@PathVariable int loadStopID,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		LoadStop loadStop=lsservice.findLoadStopByID(loadStopID);
		Load load =lservice.findLoadByID(loadStop.getLoadID());
		Carrier carrier = cservice.findCarrierByID(load.getCarrierID());	
		load.setCarrier(carrier);		
		loadStop.setLoad(load);
		if(loadStop.getStatus().equalsIgnoreCase("update")) {			
			return "redirect:/loadStop-list/"+carrier.getCarrierCode()+"-"+load.getSystemLoadID()+"-"+loadStopID;
		}
		SetStopETA setStopETA= sseservice.findSetStopETAByLoadStopID(loadStopID);
		if(setStopETA==null) {
			setStopETA =new SetStopETA();
			setStopETA.setLoadStopID(loadStopID);
			setStopETA.setEstimatedDateTime(loadStop.getArriveTime());
			setStopETA.setMovementDateTime(loadStop.getArriveTime());
			setStopETA.setStatus("N/A");
		}
		model.addAttribute("loadStop", loadStop);	
		model.addAttribute("setStopETA", setStopETA);
		return "setStopETA";
	}
	
	@RequestMapping(value = { "/setStopETA/{carrierCode}-{systemLoadID}-{shippingLocation}-{loadStopID}" }, method = RequestMethod.POST)	
	public String processSetStopETA(HttpSession session,@Valid SetStopETA setStopETA, BindingResult result,
			ModelMap model, @PathVariable int loadStopID) {
		if(!checkAuthorization(session))return "redirect:/login";
		LoadStop loadStop=lsservice.findLoadStopByID(loadStopID);
		Load load =lservice.findLoadByID(loadStop.getLoadID());
		Carrier carrier = cservice.findCarrierByID(load.getCarrierID());	
		load.setCarrier(carrier);		
		loadStop.setLoad(load);
		setStopETA.setLoadStop(loadStop);
		
		if (result.hasErrors()) {
			model.addAttribute("loadStop", loadStop);	
			model.addAttribute("setStopETA", setStopETA);
			return "setStopETA";
		}
		
		ProcessSetStopETA pSetStopETA=new ProcessSetStopETA();
		pSetStopETA.submit(environment.getRequiredProperty("webservice.Authorization"), environment.getRequiredProperty("webservice.SOAPAction"), setStopETA);
		if(setStopETA.getId()>0)sseservice.updateSetStopETA(setStopETA);
		else sseservice.saveSetStopETA(setStopETA);
		//cservice.updateCarrier(carrier);
		if(setStopETA.getStatus().equals("true")) {
			setStopETA.setErrorMessage("");
			loadStop.setStatus("setStop");
			lsservice.updateLoadStop(loadStop);
			model.addAttribute("success", "Set Stop ETA of Carrier :"+carrier.getCarrierCode()+" => Load ID : "+load.getSystemLoadID()+" =>  Shipping Location :"+loadStop.getStopShippingLocation()+"  processed successfully");
			model.addAttribute("backToPage", "List Of All Load Stop :"+load.getSystemLoadID() );
			model.addAttribute("urlToPage", "loadStop-list/"+carrier.getCarrierCode()+"-"+load.getSystemLoadID()+"-"+load.getLoadID());
			return "success";
		}else {
			model.addAttribute("success", "Process Set Stop ETA of Carrier :"+carrier.getCarrierCode()+" => Load ID : "+load.getSystemLoadID()+" =>  Shipping Location :"+loadStop.getStopShippingLocation()+" Error :"+setStopETA.getErrorMessage());
			model.addAttribute("backToPage", "List Of All Load Stop :"+load.getSystemLoadID());
			model.addAttribute("urlToPage", "loadStop-list/"+carrier.getCarrierCode()+"-"+load.getSystemLoadID()+"-"+load.getLoadID());
			return "success";
		}
	}
	
	
	@RequestMapping(value = { "/loadStatusUpdate/{carrierCode}-{systemLoadID}-{shippingLocation}-{loadStopID}" }, method = RequestMethod.GET)
	public String loadStatusUpdate(HttpSession session,@PathVariable int loadStopID,ModelMap model) {
		if(!checkAuthorization(session))return "redirect:/login";
		LoadStop loadStop=lsservice.findLoadStopByID(loadStopID);
		Load load =lservice.findLoadByID(loadStop.getLoadID());
		Carrier carrier = cservice.findCarrierByID(load.getCarrierID());	
		load.setCarrier(carrier);
		
		model.addAttribute("load", load);	
		model.addAttribute("loadStop", loadStop);
		return "loadStatusUpdate";
	}
	
	@RequestMapping(value = { "/loadStatusUpdate/{carrierCode}-{systemLoadID}-{shippingLocation}-{loadStopID}" }, method = RequestMethod.POST)	
	public String processLoadStatusUpdate(HttpSession session,@Valid LoadStop loadStop1, BindingResult result,
			ModelMap model, @PathVariable int loadStopID) {
		if(!checkAuthorization(session))return "redirect:/login";
		LoadStop loadStop=lsservice.findLoadStopByID(loadStopID);		
		Load load =lservice.findLoadByID(loadStop.getLoadID());
		Carrier carrier = cservice.findCarrierByID(load.getCarrierID());	
		load.setCarrier(carrier);		
		loadStop.setLoad(load);
		
		if (result.hasErrors()) {
			model.addAttribute("load", load);	
			model.addAttribute("loadStop", loadStop1);
			return "loadStatusUpdate";
		}
		
		loadStop.setTruckNumber(loadStop1.getTruckNumber());
		loadStop.setArriveTime(loadStop1.getArriveTime());
		loadStop.setDepartureTime(loadStop1.getDepartureTime());
		ProcessLoadStatusUpdate pLoadStatusUpdate=new ProcessLoadStatusUpdate();
		pLoadStatusUpdate.submit(environment.getRequiredProperty("webservice.Authorization"), environment.getRequiredProperty("webservice.SOAPAction"), loadStop);
		
		if(loadStop.getStatus().equals("true")) {
			loadStop.setErrorMessage("");
			loadStop.setStatus("update");
			lsservice.updateLoadStop(loadStop);
			model.addAttribute("success", "Load Status Update of Carrier :"+carrier.getCarrierCode()+" => Load ID : "+load.getSystemLoadID()+" =>  Shipping Location :"+loadStop.getStopShippingLocation()+"  processed successfully");
			model.addAttribute("backToPage", "List Of All Load Stop :"+load.getSystemLoadID() );
			model.addAttribute("urlToPage", "loadStop-list/"+carrier.getCarrierCode()+"-"+load.getSystemLoadID()+"-"+load.getLoadID());
			return "success";
		}else {
			model.addAttribute("success", "Process Load Status Update of Carrier :"+carrier.getCarrierCode()+" => Load ID : "+load.getSystemLoadID()+" =>  Shipping Location :"+loadStop.getStopShippingLocation()+" Error :"+loadStop.getErrorMessage());
			model.addAttribute("backToPage", "List Of All Load Stop :"+load.getSystemLoadID());
			model.addAttribute("urlToPage", "loadStop-list/"+carrier.getCarrierCode()+"-"+load.getSystemLoadID()+"-"+load.getLoadID());
			return "success";
		}
	}
}
