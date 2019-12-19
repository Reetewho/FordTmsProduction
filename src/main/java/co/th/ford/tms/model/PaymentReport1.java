package co.th.ford.tms.model;

import lombok.Data;

@Data
public class PaymentReport1 {
	private String systemLoadID;
	private String loadDescription;	
	private String truckNumber;	
	private String loadStartDateTime;	
	private String loadEndDateTime;
	private String completedFlag;
}
