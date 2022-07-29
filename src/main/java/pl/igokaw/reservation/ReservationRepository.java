package pl.igokaw.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.igokaw.user.UserEntity;
import pl.igokaw.user.value_objects.EmailValidator;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {


    List<ReservationEntity> findAllByUserId(UserEntity userId);

    @Query(value = "SELECT * FROM reservations WHERE car_id=?1 ", nativeQuery = true)
    List<ReservationEntity> getAllReservationsByCarId(Long id);

    List<ReservationEntity> findAllByUserIdEmail(EmailValidator email);

    @Query(value = "SELECT car_id FROM reservations WHERE id=?1", nativeQuery = true)
    Long getCarIdByReservationId(Long Id);

    ReservationEntity getReservationEntityByUserId(Long id);

    @Query(value = "SELECT COUNT(car_id) FROM reservations GROUP BY car_id ORDER BY car_id", nativeQuery = true)
    List<Integer> getAllCarsByPopularityOfReservations();

    @Query(value = "SELECT COUNT(car_id) FROM reservations WHERE car_id=?1", nativeQuery = true)
    Integer getAllNumberOfReservationsById(Long id);


}
