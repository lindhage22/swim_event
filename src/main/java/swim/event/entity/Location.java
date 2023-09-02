package swim.event.entity;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

	@Entity
	@Data
	public class Location {
			
	 @Id	
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 
	 private Long locationId;
	 private String poolName;
	 private String streetAddress;
	 private String city;
	 private String state;
	 private String zip;
	 private String phone;
	 
	 @OneToMany(cascade = CascadeType.ALL, orphanRemoval =true)
	 private Set<Swimmer> swimmers = new HashSet<>();
	 private Set<Event> events = new HashSet<>();

	
	}
	
	