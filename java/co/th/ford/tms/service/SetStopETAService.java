package co.th.ford.tms.service;

import java.util.List;

import co.th.ford.tms.model.SetStopETA;



public interface SetStopETAService {	
	
	void saveSetStopETA(SetStopETA setStopETA);
	
	void updateSetStopETA(SetStopETA setStopETA);
	
	void deleteSetStopETAByID(int setStopETAID);

	List<SetStopETA> findAllSetStopETAs(); 
	
	SetStopETA findSetStopETAByID(int setStopETAID);
	
	SetStopETA findSetStopETAByLoadStopID(int loadStopID);

	
	
}
