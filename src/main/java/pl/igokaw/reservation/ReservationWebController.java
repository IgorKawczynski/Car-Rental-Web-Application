package pl.igokaw.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.igokaw.global.dto.ErrorsListDto;
import pl.igokaw.reservation.dto.ReservationRequestDto;


@RestController
@RequestMapping("/api")
public class ReservationWebController {
    @Autowired
    private ReservationCreateService reservationCreateService;

    @PostMapping("/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public ErrorsListDto create(@RequestBody ReservationRequestDto reservation) {
        return reservationCreateService.create(reservation);
    }
}
