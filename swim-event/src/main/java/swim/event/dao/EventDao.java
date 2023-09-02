package swim.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import swim.event.entity.Event;

public interface EventDao extends JpaRepository<Event, Long> {

}
