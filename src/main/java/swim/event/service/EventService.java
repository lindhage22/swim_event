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

	@Autowired
	private SwimmerDao swimmerDao;

	@Autowired
	private EventDao eventDao;

	public LocationData saveLocation(LocationData locationData) {
		Location location = findOrCreateLocation(locationData.getLocationId());
		copyLocationFields(location, locationData);

		Location dbLocation = locationDao.save(location);
		return new LocationData(dbLocation);
	}

	private void copyLocationFields(Location location, LocationData locationData) {
		location.setPoolName(locationData.getPoolName());
		location.setStreetAddress(locationData.getStreetAddress());
		location.setCity(locationData.getCity());
		location.setState(locationData.getState());
		location.setZip(locationData.getZip());
		location.setPhone(locationData.getPhone());
	}

	private Location findOrCreateLocation(Long locationId) {
		if (Objects.isNull(locationId)) {
			return new Location();
		} else {
			return findLocationById(locationId);
		}
	}

	private Location findLocationById(Long locationId) {
		return locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException("Location with ID=" + locationId + " does not exist."));
	}

	@Transactional(readOnly = true)
	public LocationData retrieveLocationById(Long locationId) {
		return new LocationData(findLocationById(locationId));
	}

	private Swimmer findOrCreateSwimmer(Long swimmerId, Long locationId) {
		if (Objects.isNull(swimmerId)) {
			return new Swimmer();
		} else {
			return findSwimmerById(swimmerId, locationId);
		}
	}

	private void copySwimmerFields(Swimmer swimmer, Swimmer sourceSwimmer) {
		swimmer.setName(sourceSwimmer.getName());
		swimmer.setAge(sourceSwimmer.getAge());
		swimmer.setSchool(sourceSwimmer.getSchool());
	}

	@Transactional(readOnly = false)
	public Swimmer saveSwimmer(Long locationId, Swimmer sourceSwimmer) {
		Location location = findLocationById(locationId);

		Swimmer swimmer = findOrCreateSwimmer(sourceSwimmer.getSwimmerId(), locationId);
		copySwimmerFields(swimmer, sourceSwimmer);
		swimmer.setLocation(location);
		location.getSwimmers().add(swimmer);
		Swimmer dbSwimmer = swimmerDao.save(swimmer);
		return dbSwimmer;
	}

	private Event findEventById(Long eventId) {
		Event event = eventDao.findById(eventId)
				.orElseThrow(() -> new NoSuchElementException("Event with ID=" + eventId + " does not exist."));

		return event;
	}

	private Event findOrCreateEvent(Long eventId, Long locationId) {
		if (Objects.isNull(eventId)) {
			return new Event();
		} else {
			return findEventById(eventId, locationId);
		}
	}



	private void copyEventFields(Event event, Swimmer swimmerEvent) {
		event.setName(swimmerEvent.getName());
	}

	@Transactional(readOnly = false)
	public EventData saveEvent(Long swimmerEventId, Swimmer swimmerEvent) {
		Location location = findLocationById(locationId);
		if (location == null) {
			throw new IllegalArgumentException("Location with ID=" + locationId + " does not exist.");
		}
		Event event = findOrCreateEvent(swimmerEventId,eventId);
		if (swimmerEvent == null) {
			throw new IllegalArgumentException("Swimmer event is required.");
		}
		copyEventFields(event, swimmerEvent);
		location.getEvents().add(event);
		event.getSwimmers().add(swimmerEvent);

		Event dbEvent = eventDao.save(event);
		return new EventData(dbEvent);
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveAllLocations() {
		List<Location> locations = locationDao.findAll();
		List<LocationData> result = new LinkedList<>();

		for (Location location : locations) {
			LocationData locationData = new LocationData(location);
			locationData.getEvents().clear();
			locationData.getSwimmers().clear();
			result.add(locationData);
		}
		return result;
	}

	public LocationData returnLocationById(Long locationId) {
		Location location = findLocationById(locationId);
		return new LocationData(location);
	}

	public void deleteLocationById(Long locationId) {
		Location location = findLocationById(locationId);
		locationDao.delete(location);
	}

	private Swimmer findSwimmerById(Long swimmerId, Long eventId) {
		Swimmer swimmer = swimmerDao.findById(swimmerId)
				.orElseThrow(() -> new NoSuchElementException("Swimmer with ID=" + swimmerId + "does not exist."));
		boolean found = false;
		for (Event event : swimmer.getEvents()) {
			if (event.getEventId().equals(event)) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Event with ID=" + eventId + "not found for Swimmer with ID=" + swimmerId);

		}
		return swimmer;

	}

	public EventService eventfound(Long eventId) {
		Event foundEvent = findEventById(eventId);
		return foundEvent != null ? new EventService() : null;

	}
	public EventData saveEvent(EventData eventData) {
		Event event = findOrCreateEvent(EventData.getEventId());
		copyEventFields(event, eventData);

		Event dbEvent = eventDao.save(event);
		return new EventData(dbEvent);

	
	}

}