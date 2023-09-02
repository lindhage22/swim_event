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
	private Set<SwimmerData> swimmers = new HashSet<>();

	public LocationData (Location location) {
		this.locationId = location.getLocationId();
		this.poolName= location.getPoolName();
		this.streetAddress = location.getStreetAddress();
		this.city = location.getCity();
		this.state = location.getState();
		this.zip = location.getZip();
		this.phone = location.getPhone();
		
		
		for(Swimmer swimmer: location.getSwimmers()) {
			this.swimmers.add(new SwimmerData(swimmer));
	}
}

	public LocationData(Long locationId, String poolName, 
			String streetAddress, String city,
			String state, String zip, String phone) {
		this.locationId = locationId;
		this.poolName = poolName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
	}

	public Location toLocation() {
		Location location = new Location();

		location.setLocationId(locationId);
		location.setPoolName(poolName);
		location.setStreetAddress(streetAddress);
		location.setCity(city);
		location.setState(state);
		location.setZip(zip);
		location.setPhone(phone);

		for(SwimmerData swimmerData : swimmers) {
			location.getSwimmers().add(swimmerData.toSwimmer());
		}

		return location;
	}

  @Data
  @NoArgsConstructor
  public class SwimmerData {
	private Long swimmerId;
	private String name;
	private int age;
	private String school;
	private Set<EventData> events = new HashSet<>();

	public SwimmerData(Swimmer swimmer) {
		this.swimmerId = swimmer.getSwimmerId();
		this.name = swimmer.getName();
		this.age = swimmer.getAge();
		this.school = swimmer.getSchool();

		for (Event event : swimmer.getEvents()) {
			this.events.add(new EventData(event));
		}
	}
  
	public Swimmer toSwimmer() {
		Swimmer swimmer = new Swimmer();

		swimmer.setSwimmerId(swimmerId);
		swimmer.setName(name);
		swimmer.setAge(age);
		swimmer.setSchool(school);

		for (EventData eventData : events) {
			swimmer.getEvents().add(eventData.toEvent());
					
		}	
		return swimmer;
	}

}
	@Data
	@NoArgsConstructor
	public class EventData {
		private Long eventId;
		private String name;

		public EventData(Event event) {
			this.eventId = event.getEventId();
			this.name = event.getName();
		}
	
	public Event toEvent() {
			Event event = new Event();
			
			event.setEventId(eventId);
			event.setName(name);
			
			for(SwimmerData swimmerData : swimmers) {
				event.getSwimmers().add(swimmerData.toSwimmer());
			}
			return event;
				
			
			
		}

	}
	public Set<Swimmer> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}