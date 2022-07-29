package pl.igokaw.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.igokaw.car.CarFieldsService;
import pl.igokaw.car.CarService;
import pl.igokaw.reservation.dto.ReservationRequestDto;
import pl.igokaw.reservation.value_objects.CostValidator;
import pl.igokaw.reservation.value_objects.PaymentInAdvanceValidator;
import pl.igokaw.reservation.value_objects.ReservationDatesValidator;
import pl.igokaw.user.UserRepository;
import pl.igokaw.user.value_objects.EmailValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class ReservationWebMapper {
    private final CarService carService;

    private final UserRepository userRepository;

    private final CarFieldsService carFieldsService;

    public ReservationEntity fromReservationRequestDtoToReservationEntity(ReservationRequestDto reservationRequestDto){
        return ReservationEntity.builder()
                //.userId(userService.getUser(reservationRequestDto.userId()))
                //OPCJONALNA ZMIANA NA userId ZAMIAST EMAILU
                .userId(userRepository.findUserByEmail(new EmailValidator(reservationRequestDto.email())))
                .carId(carService.getCarEntityById(reservationRequestDto.carId()))
                .date(new ReservationDatesValidator(reservationRequestDto.dateStart(), reservationRequestDto.dateEnd()))
                .cost(new CostValidator(setTotalCost(reservationRequestDto)))
                .paymentInAdvance(new PaymentInAdvanceValidator(setPaymentInAdvance(reservationRequestDto.carId())))
                .build();
    }

    public BigDecimal setTotalCost(ReservationRequestDto reservationRequestDto){
        LocalDate dateStart = reservationRequestDto.dateStart();
        LocalDate dateEnd = reservationRequestDto.dateEnd();
        BigDecimal daysBetween = BigDecimal.valueOf(DAYS.between(dateStart, dateEnd));

        return carFieldsService.setInitialPrice(reservationRequestDto.carId()).multiply(daysBetween);
    }

    public BigDecimal setPaymentInAdvance(Long carId){
        var car = carService.getCarEntityById(carId);
        var paymentInAdvance = BigDecimal.valueOf(car.getNewCarCost().toLong()).
                multiply(BigDecimal.valueOf(0.001));
        if (paymentInAdvance.compareTo(BigDecimal.valueOf(1000)) < 1) return BigDecimal.valueOf(500.00);
        return paymentInAdvance;
    }

}
