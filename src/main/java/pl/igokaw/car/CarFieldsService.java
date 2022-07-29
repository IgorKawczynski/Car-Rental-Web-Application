package pl.igokaw.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.igokaw.car.enums.StatusEnum;
import pl.igokaw.car.value_objects.ProductionYearValidator;
import pl.igokaw.reservation.ReservationService;
import pl.igokaw.reservation.dto.ReservationDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarFieldsService {

    private final ReservationService reservationService;
    private final CarRepository carRepository;

    public BigDecimal setInitialPrice(Long id) {
        CarEntity car = carRepository.getReferenceById(id);
        return BigDecimal.valueOf(car.getNewCarCost().toLong())
                .multiply(BigDecimal.valueOf(0.001))
                .multiply(productionYearFactor(car))
                .multiply(popularityOfCar(id)).setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal popularityOfCar(Long Id) {
        int numberOfReservationOfTheMostPopularCar = reservationService.getNumberOfReservationsOfTheMostPopularCar();
        int numberOfReservationsOfTheSelectedCar = reservationService.getNumberOfReservationsByCarId(Id);

        if (numberOfReservationsOfTheSelectedCar == numberOfReservationOfTheMostPopularCar) {
            return BigDecimal.valueOf(3);
        }
        if (numberOfReservationOfTheMostPopularCar > numberOfReservationsOfTheSelectedCar &&
                numberOfReservationsOfTheSelectedCar > numberOfReservationOfTheMostPopularCar / 2) {
            return BigDecimal.valueOf(2);
        }

        return BigDecimal.ONE;
    }

    public BigDecimal setPricePerDays(Long Id, Integer days) {
        return setInitialPrice(Id).multiply(BigDecimal.valueOf(days));
    }
    public StatusEnum getStatus(Long CarId) {
        List<ReservationDto> allReservationsById = reservationService.getAllReservationsByCarId(CarId);

        for (ReservationDto reservationDto : allReservationsById) {
            if (!Objects.isNull(reservationDto)) {
                if ((reservationDto.getDateStart().isBefore(LocalDate.now()) || reservationDto.getDateStart().equals(LocalDate.now()))
                        && (reservationDto.getDateEnd().isAfter(LocalDate.now())) || reservationDto.getDateEnd().equals(LocalDate.now())) {
                    return StatusEnum.RESERVED;
                }
            }
            return StatusEnum.FREE;
        }
        return StatusEnum.FREE;
    }

    public BigDecimal productionYearFactor(CarEntity car) {
        if (isBetween(car.getProductionYear(), LocalDate.now().minusYears(2), LocalDate.now())) {
            return BigDecimal.valueOf(0.13);
        }
        if (isBetween(car.getProductionYear(), LocalDate.now().minusYears(8), LocalDate.now().minusYears(3))) {
            return BigDecimal.valueOf(0.10);
        }
        if (isBetween(car.getProductionYear(), LocalDate.now().minusYears(12), LocalDate.now().minusYears(9))) {
            return BigDecimal.valueOf(0.09);
        }
        if (isBetween(car.getProductionYear(), LocalDate.now().minusYears(22), LocalDate.now().minusYears(13))) {
            return BigDecimal.valueOf(0.07);
        }

        return BigDecimal.valueOf(0.01);
    }

    private boolean isBetween(ProductionYearValidator productionYear, LocalDate lower, LocalDate upper) {
        return lower.getYear() <= productionYear.toInteger() && productionYear.toInteger() <= upper.getYear();
    }
}
