package swim.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import swim.event.controller.model.LocationData;
import swim.event.controller.model.LocationData.EventData;
import swim.event.controller.model.LocationData.LocationSwimmer;
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
    public LocationData updateLocation(@PathVariable Long locationId, 
    		@RequestBody LocationData locationData) {
        locationData.setLocationId(locationId);
        log.info("Updating location {}", locationData);
        return eventService.saveLocation(locationData);
    }

    
    @PostMapping("/swimevent/{locationId}/swimmer")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationSwimmer addSwimmer(@PathVariable Long locationId, 
    		@RequestBody LocationSwimmer locationSwimmer) {
        log.info("Adding swimmer {} to event with ID={}", locationSwimmer, locationId);
        return eventService.saveSwimmer(locationId, locationSwimmer);
        
    }


    @PostMapping("/swimevent/{swimmerId}/event")
    @ResponseStatus(HttpStatus.CREATED)
    public EventData addEvent(@PathVariable Long swimmerId, 
    		@RequestBody EventData eventData) {
        log.info("Adding event {} to swimmer with ID={}", eventData, swimmerId);
        return eventService.saveEvent(swimmerId, eventData);
    }

    @GetMapping("/swimevent/location")
    public List<LocationData> listAllLocations() {
        log.info("Listing all locations");
        return eventService.retrieveAllLocations();
    }

    @GetMapping("/swimevent/{locationId}")
    public LocationData returnLocationById(@PathVariable Long locationId) {
        log.info("Retrieving location with ID={}", locationId);
        return eventService.returnLocationById(locationId);
    }

    @DeleteMapping("/swimevent/{locationId}")
    public Map<String, String> deleteLocationById(
    		@PathVariable Long locationId) {
        log.info("Deleting pool location with ID={}", locationId);
        
        eventService.deleteLocationById(locationId);
        
        return Map.of("message", "Successfully deleted location with ID=" + locationId);
    }
}
