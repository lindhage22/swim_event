package swim.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import swim.event.entity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

}
