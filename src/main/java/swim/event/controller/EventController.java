package swim.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import swim.event.controller.model.LocationData;
import swim.event.entity.Event;
import swim.event.entity.Swimmer;
import swim.event.service.EventService;

@RestController
@RequestMapping("/swim_event")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/location")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationData createLocation(@RequestBody LocationData locationData) {
        log.info("Creating location {}", locationData);
        return eventService.saveLocation(locationData);
    }

    @PutMapping("/swimevent/{locationId}")
    public LocationData updateLocation(@PathVariable Long locationId, @RequestBody LocationData locationData) {
        locationData.setLocationId(locationId);
        log.info("Updating location {}", locationData);
        return eventService.saveLocation(locationData);
    }

    @PutMapping("/swimevent/{locationId}/swimmer/{swimmerId}")
    public Swimmer updateSwimmer(@PathVariable Long locationId, @PathVariable Long swimmerId, @RequestBody Swimmer swimmer) {
        log.info("Updating swimmer with ID={} at swimmer location with ID={}", swimmerId, locationId);
        swimmer.setSwimmerId(swimmerId);
        return eventService.saveSwimmer(locationId, swimmer);
    }

    @PostMapping("/swimevent/{eventId}/swimmer")
    @ResponseStatus(HttpStatus.CREATED)
    public Swimmer addSwimmer(@PathVariable Long eventId, @RequestBody Swimmer swimmer) {
        log.info("Adding swimmer {} to event with ID={}", swimmer, eventId);
        return eventService.saveSwimmer(eventId, swimmer);
    }

    @PostMapping("/swimevent/{swimmerId}/event")
    @ResponseStatus(HttpStatus.CREATED)
    public Event addEvent(@PathVariable Long swimmerId, @RequestBody Event event) {
        log.info("Adding event {} to swimmer with ID={}", event, swimmerId);
        return eventService.eventData(swimmerId, event);
    }

    @GetMapping("/swimevent/event")
    public List<LocationData> listAllEvents() {
        log.info("Listing all events");
        return eventService.retrieveAllLocations();
    }

    @GetMapping("/swimevent/{locationId}")
    public LocationData returnLocationById(@PathVariable Long locationId) {
        log.info("Retrieving location with ID={}", locationId);
        return eventService.returnLocationById(locationId);
    }

    @DeleteMapping("/swimevent/{locationId}")
    public Map<String, String> deleteLocationById(@PathVariable Long locationId) {
        log.info("Deleting swim location with ID={}", locationId);
        eventService.deleteLocationById(locationId);
        return Map.of("message", "Successfully deleted location with ID=" + locationId);
    }
}
