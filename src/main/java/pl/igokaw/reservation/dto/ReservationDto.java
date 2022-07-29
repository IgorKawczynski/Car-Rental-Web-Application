package pl.igokaw.reservation.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto implements Serializable {

    Long id;
    Long userId;
    Long carId;
    String brand;
    String model;
    LocalDate dateStart;
    LocalDate dateEnd;
    BigDecimal cost;
    BigDecimal paymentInAdvance;


}
