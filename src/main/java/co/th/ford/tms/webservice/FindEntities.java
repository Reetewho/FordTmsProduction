package co.th.ford.tms.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;

import co.th.ford.tms.model.Carrier;
import co.th.ford.tms.model.Load;
import co.th.ford.tms.webservice.Base;


public class FindEntities extends Base{
		
	private  final String wsEndpoint = "https://fordswsprd.jdadelivers.com/webservices/services/TransportationManager2";

	private  final String inputXML = 
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cis=\"http://www.i2.com/cis\">" + 
					"   <soapenv:Header /> " +
					"    <soapenv:Body>" +
					"   	<cis:findEntities>" +
					"   		<EntityType>AlertType</EntityType>\r\n" + 
					"         <Select>\r\n" + 
					"           <Name>SystemLoadID</Name> \r\n" + 
					"           <Name>AlertTypeCode</Name> \r\n" + 
					"         </Select>\r\n" + 
					"         <Filter>\r\n" + 
					"           <Name>CarrierCode</Name>\r\n" + 
					"           <Op>Eq</Op>\r\n" + 
					"           <Value>%s</Value>\r\n" + 
					"         </Filter>   \r\n" + 
					"         <Condition>\r\n" + 
					"           <O>\r\n" + 
					"           	<Or>\r\n" + 
					"           		<O>\r\n" + 
					"           			<Eq>\r\n" + 
					"           				<O>\r\n" + 
					"           					<Name>AlertTypeCode</Name>\r\n" + 
					"           				</O>\r\n" + 
					"           				<O>\r\n" + 
					"           					<Value>LOAD_TENDER_UPDATED</Value>\r\n" + 
					"           				</O>\r\n" + 
					"           			</Eq> \r\n" + 
					"           		</O>\r\n" + 					          		
					"           		<O>\r\n" + 
					"           			<Eq>\r\n" + 
					"           				<O>\r\n" + 
					"           					<Name>AlertTypeCode</Name>\r\n" + 
					"                   		</O>\r\n" + 
					"            				<O>\r\n" + 
					"             					<Value>LOAD_TENDER_NEW</Value>\r\n" + 
					"            				</O>\r\n" + 
					"               		</Eq> \r\n" + 
					"           		</O>\r\n" + 
					"           	</Or>\r\n" + 
					"             </O>\r\n" + 
					"          </Condition>       \r\n" + 
					"          <Filter>  \r\n" + 
					"           	<Name>CreatedDateTime</Name>\r\n" + 
					"           	<Op>Ge</Op>\r\n" + 
					"           <Value>%s</Value>\r\n" + 
					"         	</Filter>" +
					" 			<Page>\r\n" + 
					"  				<MaxRows>9999999</MaxRows> \r\n" + 
					" 			</Page>		   \r\n" + 
					"   </cis:findEntities>" +		
					"  </soapenv:Body>" +
					"</soapenv:Envelope>";
	
	public  Carrier submit(String Authenticationm,String SOAPAction,Carrier carrier){
		
		try{			
			SOAPBody sb = getResponse(sendRequest(wsEndpoint,Authenticationm,SOAPAction,String.format(inputXML, carrier.getCarrierCode(), carrier.getLoadDate())));
			System.out.println(sb.toString());
			System.out.println(sb.getValue());
			System.out.println(sb.getTagName());
			System.out.println(extract(sb,"ns1:CompletedSuccessfully"));
			carrier.setStatus(extract(sb,"ns1:CompletedSuccessfully"));
			if(carrier.getStatus().equals("true")) {
				List<Load> loadList=new ArrayList<Load>();			
				if (sb.getElementsByTagName("ns1:Entity").getLength() > 0) {
					for(int i =0;i<sb.getElementsByTagName("ns1:Entity").getLength();i++ ){
						Load load=new Load();
						load.setCarrierID(carrier.getCarrierID());
						load.setCarrier(carrier);
						load.setSystemLoadID(Integer.parseInt(sb.getElementsByTagName("ns1:SystemLoadID").item(i).getFirstChild().getNodeValue()));
						load.setAlertTypeCode(sb.getElementsByTagName("ns1:AlertTypeCode").item(i).getFirstChild().getNodeValue());
						load.setStatus("N/A");
//						load.setStatusFlag(1);
						loadList.add(load);
					}
						
				}
				carrier.setLoadList(loadList);
			}else {
				carrier.setErrorMessage(extract(sb,"ns1:SystemMessage"));			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return carrier;
	}
}
