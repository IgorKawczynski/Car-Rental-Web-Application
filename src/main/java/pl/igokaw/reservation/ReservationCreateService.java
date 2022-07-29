package pl.igokaw.reservation;

import org.springframework.stereotype.Service;
import pl.igokaw.global.dto.ErrorsListDto;
import pl.igokaw.reservation.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationCreateService {

    private final ReservationWebMapper reservationWebMapper;

    private final ReservationRepository reservationRepository;

    private final ReservationValidator reservationValidator;

    public ReservationCreateService(ReservationWebMapper reservationWebMapper, ReservationRepository reservationRepository, ReservationValidator reservationValidator) {
        this.reservationWebMapper = reservationWebMapper;
        this.reservationRepository = reservationRepository;
        this.reservationValidator = reservationValidator;
    }

    public ErrorsListDto create(ReservationRequestDto reservationRequestDto) {

        ErrorsListDto errorsListDto = new ErrorsListDto( new ArrayList<>() );

        if(Objects.isNull(reservationRequestDto.dateStart())){
            errorsListDto.add("Date start can't be null!!");
        }

        if(Objects.isNull(reservationRequestDto.dateEnd())){
            errorsListDto.add("Date end can't be null!");
        }

        if ( !isPresentDayBeforeReservation(reservationRequestDto)){
            errorsListDto.add("Reservation must start at least 1 day after the present day!");
        }

        if ( reservationRequestDto.dateStart().isAfter(reservationRequestDto.dateEnd())){
            errorsListDto.add("Date of start must be before the date of end reservation!");
        }

        if (reservationRequestDto.dateStart().equals(reservationRequestDto.dateEnd())) {
            errorsListDto.add("Minimum reservation length is 1 day!");
        }

        if ( !isNewReservationCollidingWithOtherReservations(reservationRequestDto)) {
            errorsListDto.add("Your reservation is colliding with other reservation!");
        }

        if (errorsListDto.isListOfErrorsEmpty()) {
            var reservationEntity = reservationWebMapper.fromReservationRequestDtoToReservationEntity(reservationRequestDto);
            reservationRepository.save(reservationEntity);
        }
        return errorsListDto;
    }
    public boolean isPresentDayBeforeReservation (ReservationRequestDto reservation){
        return LocalDate.now().isBefore(reservation.dateStart());
    }

    public boolean isNewReservationCollidingWithOtherReservations(ReservationRequestDto reservationRequestDto){
        List<ReservationEntity> reservationEntitiesForSpecifiedCar = reservationRepository.findAll();
        reservationEntitiesForSpecifiedCar.removeIf(reservation -> !reservation.getCarId().getId().equals(reservationRequestDto.carId()));

        for (ReservationEntity reservation : reservationEntitiesForSpecifiedCar) {
            if ((reservationValidator.areDatesEquals(reservationRequestDto.dateStart(), reservationRequestDto.dateEnd(),
                    reservation.getDate().getDateStart(), reservation.getDate().getDateEnd()))
                    || (reservationValidator.isDateBetween(reservation.getDate().getDateEnd(), reservationRequestDto.dateStart(), reservationRequestDto.dateEnd()))
                    || (reservationValidator.isDateBetween(reservation.getDate().getDateStart(), reservationRequestDto.dateStart(), reservationRequestDto.dateEnd())
                    || (reservationValidator.isReservationContainingNewReservation(reservationRequestDto.dateStart(), reservationRequestDto.dateEnd(),
                    reservation.getDate().getDateStart(), reservation.getDate().getDateEnd())))){
                return false;
            }
        }
        return true;
    }

}
