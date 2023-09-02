package swim.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import swim.event.entity.Swimmer;

public interface SwimmerDao extends JpaRepository<Swimmer, Long> {

}
