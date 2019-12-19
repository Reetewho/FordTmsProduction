package co.th.ford.tms.dao;

import java.util.List;

import co.th.ford.tms.model.Load;


public interface LoadDao {

	Load findLoadByID(int loadID);

	void saveLoad(Load load);
	
	void deleteLoadByID(int loadID);	
	
	List<Load> findLoadByCarrierID(int carrierID);
	
	List<Load> findAllLoads();

	

}
