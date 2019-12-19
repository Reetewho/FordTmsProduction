package co.th.ford.tms.service;

import java.util.List;
import co.th.ford.tms.model.Load;



public interface LoadService {	
	
	void saveLoad(Load load);
	
	void updateLoad(Load load);
	
	void deleteLoadByID(int loadID);

	List<Load> findAllLoads(); 
	
	Load findLoadByID(int loadID);
	
	List<Load> findLoadByCarrierID(int carrierID);

	
	
}
