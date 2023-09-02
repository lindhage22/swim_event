package swim.event.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data 

public class Event {
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eventId;
  private Long swimmerId;

  
  private String name;
  
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(mappedBy = "events")
  
  private Set<Swimmer> swimmers= new HashSet<>();


}
 
  




  



 


