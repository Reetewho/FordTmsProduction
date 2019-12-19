package co.th.ford.tms.webservice;



import javax.xml.soap.SOAPBody;

import co.th.ford.tms.model.SetStopETA;
import co.th.ford.tms.webservice.Base;


public class ProcessSetStopETA extends Base{
		
	private  final String wsEndpoint = "https://fordswsprd.jdadelivers.com/webservices/services/TransportationManager2";

	private  final String inputXML = 
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cis=\"http://www.i2.com/cis\"> \r\n" + 
					"   <soapenv:Header /> \r\n" + 
					"   <soapenv:Body> \r\n" + 
					"     <cis:processSetStopETA>\r\n" + 
					"		<cis:ApiHeader> \r\n" + 
					"			<cis:OperationName>processSetStopETA</cis:OperationName> \r\n" + 
					"		</cis:ApiHeader>\r\n" + 
					"		<cis:StopUpdateProgressData>\r\n" + 
					"				<cis:SystemLoadID>%s</cis:SystemLoadID>	\r\n" + 
					"				<cis:CarrierCode>%s</cis:CarrierCode>			\r\n" + 
					"				<cis:ShippingLocationCode>%s</cis:ShippingLocationCode>\r\n" + 
					"				<cis:StopEvent>\r\n" + 
					"					<cis:EventCode>RETA</cis:EventCode>\r\n" + 
					"					<cis:MovementDateTime>%s</cis:MovementDateTime>\r\n" + 
					"					<cis:EstimatedDateTimeAtStop>%s</cis:EstimatedDateTimeAtStop>	\r\n" + 
					"					<cis:Latitude>%s</cis:Latitude>\r\n" + 
					"					<cis:Longitude>%s</cis:Longitude>			\r\n" + 
					"				</cis:StopEvent>\r\n" + 
					"		</cis:StopUpdateProgressData>\r\n" + 
					"		<cis:EventSourceEnumVal>EVENTSRC_API</cis:EventSourceEnumVal> \r\n" + 
					"	</cis:processSetStopETA>\r\n" + 
					"   </soapenv:Body> \r\n" + 
					"</soapenv:Envelope>";
	
	public SetStopETA submit(String Authenticationm,String SOAPAction,SetStopETA ssetaModel){		
		try{			
			SOAPBody sb = getResponse(sendRequest(wsEndpoint,Authenticationm,SOAPAction,String.format(inputXML, ""+ssetaModel.getLoadStop().getLoad().getSystemLoadID(),ssetaModel.getLoadStop().getLoad().getCarrier().getCarrierCode(),ssetaModel.getLoadStop().getStopShippingLocation(),ssetaModel.getMovementDateTime(),ssetaModel.getEstimatedDateTime(),ssetaModel.getLatitude(),ssetaModel.getLongitude())));
			ssetaModel.setStatus(extract(sb,"ns1:CompletedSuccessfully"));
			if(!ssetaModel.getStatus().equals("true")) {				
				ssetaModel.setErrorMessage(extract(sb,"ns1:SystemMessage"));
			}					
		}catch(Exception e){
			e.printStackTrace();
		}
		return ssetaModel;
	}
}
