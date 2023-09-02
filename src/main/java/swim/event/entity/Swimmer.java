package swim.event.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Swimmer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long swimmerId;
	private Long eventId;
	

	@EqualsAndHashCode.Exclude
	private String name;

	@EqualsAndHashCode.Exclude
	private int age;

	@EqualsAndHashCode.Exclude
	private String school;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "location_id, nullable = false")
	private Location location;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "swimmer_event", 
			joinColumns = @JoinColumn(name ="swimmer_id"),
			inverseJoinColumns = @JoinColumn(name = "event_id")
			)
	private Set<Event> events = new HashSet<>();

	
}