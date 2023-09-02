package swim.event.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import swim.event.entity.Event;
import swim.event.entity.Location;
import swim.event.entity.Swimmer;

@Data
@NoArgsConstructor

public class LocationData {
	private Long locationId;

	private String poolName;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private Set<LocationSwimmer> locationSwimmer = new HashSet<>();
	

	public LocationData(Location location) {
		this.locationId = location.getLocationId();
		this.poolName = location.getPoolName();
		this.streetAddress = location.getStreetAddress();
		this.city = location.getCity();
		this.state = location.getState();
		this.zip = location.getZip();
		this.phone = location.getPhone();

		for (Swimmer swimmer : location.getSwimmers()) {
			this.locationSwimmer.add(new LocationSwimmer(swimmer));
		}
		
	}

	@Data
	@NoArgsConstructor
	public static class EventData {
		private Long eventId;
		private String name;
		private Set<Swimmer> swimmer = new HashSet<>();
		
		
		public EventData(Event event) {
			eventId = event.getEventId();
			name = event.getName();
		
		
			
	}
	}
  @Data
  @NoArgsConstructor
  public static class LocationSwimmer {
	private Long swimmerId;
	private String name;
	private int age;
	private String school;
	private Set<Event> event = new HashSet<>();

	public LocationSwimmer(Swimmer swimmer) {
		swimmerId = swimmer.getSwimmerId();
		name = swimmer.getName();
		age = swimmer.getAge();
		school = swimmer.getSchool();

		
		}
	}
  
	

}
