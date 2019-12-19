package co.th.ford.tms.service;

import java.util.List;

import co.th.ford.tms.model.Carrier;



public interface CarrierService {	
	
	void saveCarrier(Carrier carrier);
	
	void updateCarrier(Carrier carrier);
	
	void deleteCarrierByID(int carrierID);

	List<Carrier> findAllCarriers(); 
	
	Carrier findCarrierByID(int carrierID);

	
	
}
