package pl.igokaw.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    @Query(value = "SELECT car_id FROM reservations WHERE id=?1", nativeQuery = true)
    Long getCarIdByReservationId(Long Id);
}
