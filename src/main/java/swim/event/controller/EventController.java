package swim.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import swim.event.controller.model.LocationData;
import swim.event.controller.model.LocationData.EventData;
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
    public LocationData insertLocation(@RequestBody LocationData locationData) {
        log.info("Creating location {}", locationData);
        return eventService.saveLocation(locationData);
    }

    @PutMapping("/swimevent/{locationId}")
    public LocationData updateLocation(@PathVariable Long locationId, @RequestBody LocationData locationData) {
        locationData.setLocationId(locationId);
        log.info("Updating location {}", locationData);
        return eventService.saveLocation(locationData);
    }

    
    @PostMapping("/swimevent/{eventId}/swimmer")
    @ResponseStatus(HttpStatus.CREATED)
    public Swimmer insertSwimmer(@PathVariable Long eventId, @RequestBody Swimmer swimmer) {
        log.info("Adding swimmer {} to event with ID={}", swimmer, eventId);
        return eventService.saveSwimmer(eventId,swimmerData);
        
    }


    @PostMapping("/swimevent/{locationId}/event")
    @ResponseStatus(HttpStatus.CREATED)
    public Event insertEvent(@PathVariable Long locationId, @RequestBody Event eventData) {
        log.info("Adding event {} to swimmer with ID={}", eventData, locationId);
        return eventService.saveEvent(locationId, eventData);
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
