package co.th.ford.tms.model;


import lombok.Data;

@Data
public class Report1 {
	
	private String systemLoadID;
	private String alertTypeCode;	
	private String loadDescription;	
	private String stopSequence;	
	private String stopShippingLocation;	
	private String stopShippingLocationName;	
	private String truckNumber;	
	private String departureTime;	
	private String arriveTime;
	private String loadStartDateTime;	
	private String loadEndDateTime;
	private String completedFlag;
	private String latitude;
	private String longitude;
	private String movementDateTime;
	private String estimatedDateTime;

}
