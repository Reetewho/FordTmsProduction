package co.th.ford.tms.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="tb_carrier")
public class Carrier {
	
	public Carrier(){}
	
	@Id	
	@Column(name = "carrierID", nullable = false)	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int carrierID;	
	
	@Size(min=3, max=10)
	@Column(name = "carrierCode", nullable = false)
	private String carrierCode;		
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "loadDate", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime loadDate;

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
	
//	@Column(name = "statusFlag", nullable = true)
//	private int statusFlag;
//		
	@Transient
	private List<Load> loadList;
	
	@Transient
	private int numOfLoad;
	
	@Override
	public int hashCode() {		
		final int prime = 31;
		int result = 1;
		result = prime * result + carrierID;
		result = prime * result + ((carrierCode == null) ? 0 : carrierCode.hashCode());
		return result;
	}	
	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Carrier))
			return false;
		Carrier other = (Carrier) obj;
		if (carrierID != other.carrierID)
			return false;
			
		
		return true;
	}

	@Override
	public String toString() {
		return "Carrier [Carrier ID=" + carrierID + ", Carrier Code=" + carrierCode + ", Load Date=" + loadDate
				 + ", get Load ID Status =" + status + ", Last Update Date=" + lastUpdateDate
				+ ", Last Update User=" + lastUpdateUser + "]";
//				+ ", Status Flag=" + statusFlag  + "]";
	}
			
}
