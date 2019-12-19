package co.th.ford.tms.model;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="tb_load")
public class Load {
	

	public Load(){}
	
	@Id	
	@Column(name = "loadID", nullable = false)	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loadID;
	
	@Column(name = "carrierID", nullable = false)
	private int carrierID;
	
	@Column(name = "systemLoadID", nullable = false)
	private int systemLoadID;	
	
	@Column(name = "alertTypeCode", nullable = false)
	private String alertTypeCode;
	
	@Column(name = "loadDescription")
	private String loadDescription;
	
	@Size(min=3, max=5)
	@Column(name = "completedFlag", nullable = false)
	private String status;	
		
	@Column(name = "errorMessage", nullable = true)
	private String errorMessage;
	
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "lastUpdateDate", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime lastUpdateDate;	
	
	@Column(name = "lastUpdateUser", nullable = true)
	private String lastUpdateUser;
	
	@Transient
	private Carrier carrier;
	
	@Transient
	private List<LoadStop> loadStopList;
	
	@Override
	public int hashCode() {		
		final int prime = 31;
		int result = 1;
		result = prime * result + loadID;
		result = prime * result + carrierID;
		result = prime * result +systemLoadID;
		return result;
	}	
	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Load))
			return false;
		Load other = (Load) obj;
		if (loadID != other.loadID)
			return false;
			
		
		return true;
	}

	@Override
	public String toString() {
		return "Load [Load ID=" + loadID + ", Carrier ID=" + carrierID + ", System Load ID="
				+ systemLoadID +", Completed Flag "+status +", Error Message= "+errorMessage
				+", Last Update Date ="+lastUpdateDate+", Last Update User ="+lastUpdateUser+"  ]";
	}
		
	
}
