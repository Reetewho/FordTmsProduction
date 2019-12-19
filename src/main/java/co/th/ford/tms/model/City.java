package co.th.ford.tms.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="tb_city")
public class City {
	public City(){}
	
	@Id	
	@Column(name = "cityID", nullable = false)	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cityID;	
	
	
	@Column(name = "cityNameEn", nullable = true)
	private String cityNameEn;	
	

	
	@Column(name = "cityNameTh", nullable = true)
	private String cityNameTh;	
	
	
	@Column(name = "status", nullable = true)	
	private int status;
		
	
	 
	
		
}
