package co.th.ford.tms.dao;

import java.util.List;

import co.th.ford.tms.model.Carrier;


public interface CarrierDao {

	Carrier findCarrierByID(int carrierID);

	void saveCarrier(Carrier carrier);
	
	void deleteCarrierByID(int carrierID);
	
	List<Carrier> findAllCarriers();

	

}
