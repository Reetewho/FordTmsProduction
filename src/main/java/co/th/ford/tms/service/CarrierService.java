package co.th.ford.tms.service;

import java.util.List;

import co.th.ford.tms.model.Carrier;
import co.th.ford.tms.model.LoadListReport;
import co.th.ford.tms.model.Report1;
import co.th.ford.tms.model.PaymentReport1;



public interface CarrierService {	
	
	void saveCarrier(Carrier carrier);
	
	void updateCarrier(Carrier carrier);
	
	void deleteCarrierByID(int carrierID);

	List<Carrier> findAllCarriers(); 
	
	Carrier findCarrierByID(int carrierID);
	
	Carrier findCarrierByDate(String date);
	
	List<Report1> findAll(String startDate ,String endDate);
	
	List<Carrier> findListCarriersByDate(String startDate,String endDate);
	
	List<PaymentReport1> findPaymentByCompleted(String startDate ,String endDate);
	
	List<LoadListReport> findbyLoadID(String strLoadID);
}
