package swim.event.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swim.event.controller.model.LocationData;
import swim.event.controller.model.LocationData.EventData;
import swim.event.controller.model.LocationData.LocationSwimmer;
import swim.event.dao.EventDao;
import swim.event.dao.LocationDao;
import swim.event.dao.SwimmerDao;
import swim.event.entity.Event;
import swim.event.entity.Location;
import swim.event.entity.Swimmer;

@Service
public class EventService {

	@Autowired
	private LocationDao locationDao;

	
	public LocationData saveLocation(LocationData locationData) {
		Long locationId = locationData.getLocationId();
		Location location = findOrCreateLocation(locationId);
		
		copyLocationFields(location, locationData);

		
		return new LocationData(locationDao.save(location));
	

	}	
	private Location findOrCreateLocation(Long locationId) {
		Location location;

		if (Objects.isNull(locationId)) {
			location = new Location();
		} else {
			location = findLocationById(locationId);
		}

		return location;	
		
					
	}	
	private Location findLocationById(Long locationId) {
		return locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException(
						"Location with ID=" + locationId + " does not exist."));	
		
	}	
	
	private void copyLocationFields(Location location, LocationData locationData) {
		location.setLocationId(locationData.getLocationId());
		
		location.setPoolName(locationData.getPoolName());
		location.setStreetAddress(locationData.getStreetAddress());
		location.setCity(locationData.getCity());
		location.setState(locationData.getState());
		location.setZip(locationData.getZip());
		location.setPhone(locationData.getPhone());	
	}
	
	
	@Autowired
	private SwimmerDao swimmerDao;

	@Transactional(readOnly = false)
	public LocationSwimmer saveSwimmer(Long locationId, LocationSwimmer locationSwimmer) {
		Location location = findLocationById(locationId);
		
		Long swimmerId = locationSwimmer.getSwimmerId();
		Swimmer swimmer = findOrCreateSwimmer(locationId, swimmerId);
		
		copySwimmerFields(swimmer, locationSwimmer);
		swimmer.setLocation(location);
		
	
		
		return new LocationSwimmer(swimmerDao.save(swimmer));
		
	}	
	
	private Swimmer findOrCreateSwimmer(Long locationId, Long swimmerId) {
		Swimmer swimmer;

		if (Objects.isNull(swimmerId)) {
			swimmer = new Swimmer();
		} else {
			swimmer = findSwimmerById(locationId, swimmerId);
		}
		return swimmer;
	
	
	}
	private Swimmer findSwimmerById(Long locationId, Long swimmerId) {
		Swimmer swimmer = swimmerDao.findById(swimmerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Swimmer with ID=" + swimmerId + " does not exist."));
		
		//checking for pool location match

		if (swimmer.getLocation().getLocationId() != locationId) {
			throw new IllegalArgumentException(
					"Swimmer with ID=" + swimmerId + 
					" is not an swimmer at pool location with ID=" + locationId);
		} 
			return swimmer;
		
	}
	
	private void copySwimmerFields(Swimmer swimmer, LocationSwimmer locationSwimmer) {
		swimmer.setSwimmerId(locationSwimmer.getSwimmerId());
		swimmer.setName(locationSwimmer.getName());
		swimmer.setAge(locationSwimmer.getAge());
		swimmer.setSchool(locationSwimmer.getSchool());
		
	}
	@Autowired
	private EventDao eventDao;

	@Transactional(readOnly = false)
	public EventData saveEvent(Long swimmerId, EventData eventData) {
		Swimmer swimmer = findSwimmerEventById(swimmerId);
		
		Long eventId = eventData.getEventId();
		Event event = findOrCreateEvent(swimmerId, eventId);
		
		copyEventFields(event, eventData);
		event.getSwimmers().add(swimmer);
		
		//add event to pool location
		
		swimmer.getEvents().add(event);
		
		
	
		return new EventData(eventDao.save(event));
		
	}
	private Swimmer findSwimmerEventById(Long swimmerId) {
			
		    return  swimmerDao.findById(swimmerId)
					.orElseThrow(() -> new NoSuchElementException(
							"Swimmer with ID=" + swimmerId + " does not exist."));
			
			} 
	
	
	private Event findOrCreateEvent(Long locaitonId, Long eventId) {
		Event event;

		if (Objects.isNull(eventId)) {
			event = new Event();
		} else {
			event = findEventById(locaitonId, eventId);
		}
		return event;
	}
	
	
	private Event findEventById(Long locationId, Long eventId) {
		Event event = eventDao.findById(eventId)
				.orElseThrow(() -> new NoSuchElementException(
						"Event with ID=" + eventId + " does not exist."));
		
		// Checking for swim event ID match with a location for event should not throw an exception,
		// if the location ID exists, but is not a part of the event data
		// it simply means that the event should be added to the location via the join table.
			
		
		return event;
	}
	
	private void copyEventFields(Event event, EventData eventData) {
		event.setEventId(eventData.getEventId());
		event.setName(eventData.getName());
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveAllLocations() {
		List<Location> locations = locationDao.findAll();
		List<LocationData> locationDataResult = new LinkedList<>();

		for (Location location : locations) {
			LocationData psd = new LocationData(location);

			
			locationDataResult.add(psd);
		}
		return locationDataResult;
	}
	
    @Transactional (readOnly =true)
	public LocationData returnLocationById(Long locationId) {
		Location location = findLocationById(locationId);
		
		return new LocationData(location);
	}

	@Transactional(readOnly =false)
	public void deleteLocationById(Long locationId) {
		Location location = findLocationById(locationId);
		locationDao.delete(location);
	}

}