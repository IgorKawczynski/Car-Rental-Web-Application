package pl.igokaw.reservation;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import pl.igokaw.car.CarEntity;
import pl.igokaw.reservation.dto.ReservationDto;
import pl.igokaw.reservation.dto.ReservationRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    public ReservationDto fromReservationToReservationDto(ReservationEntity reservationEntity) {
        CarEntity carId = reservationEntity.getCarId();
        carId = (CarEntity) Hibernate.unproxy(carId);
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setId(reservationEntity.getId());
        reservationDto.setUserId(reservationEntity.getUserId().getId());
        reservationDto.setCarId(reservationEntity.getCarId().getId());
        reservationDto.setBrand(carId.getBrand().toString());
        reservationDto.setModel(carId.getModel().toString());
        reservationDto.setDateStart(reservationEntity.getDate().getDateStart());
        reservationDto.setDateEnd(reservationEntity.getDate().getDateEnd());
        reservationDto.setCost(reservationEntity.getCost().getCost());
        reservationDto.setPaymentInAdvance(reservationEntity.getPaymentInAdvance().getPaymentInAdvance());
        return reservationDto;
    }
    public List<ReservationDto> fromReservationListToReservationDtoList(List<ReservationEntity> reservationEntityList) {
        return reservationEntityList.stream()
                .map(this::fromReservationToReservationDto)
                .collect(Collectors.toList());
    }
    public ReservationRequestDto fromReservationToReservationRequestDto(ReservationEntity reservationEntity){
        return ReservationRequestDto.builder()
                .email(reservationEntity.getUserId().getEmail().toString())
                .carId(reservationEntity.getCarId().getId())
                .dateStart(reservationEntity.getDate().getDateStart())
                .dateEnd(reservationEntity.getDate().getDateEnd())
                .build();
    }
}
