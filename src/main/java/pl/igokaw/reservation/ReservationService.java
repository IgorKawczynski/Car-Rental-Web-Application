package pl.igokaw.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.igokaw.global.dto.ErrorsListDto;
import pl.igokaw.reservation.dto.ReservationDto;
import pl.igokaw.user.UserEntity;
import pl.igokaw.user.UserRepository;
import pl.igokaw.user.value_objects.EmailValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    private final ReservationValidator reservationEditor;
    private final ReservationWebMapper reservationWebMapper;

    private final UserRepository userRepository;

    @Autowired
    @Lazy
    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper,
            ReservationValidator reservationEditor,
            ReservationWebMapper reservationWebMapper,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.reservationEditor = reservationEditor;
        this.userRepository = userRepository;
        this.reservationWebMapper = reservationWebMapper;
    }

    public ReservationDto createReservation(ReservationEntity reservationEntity) {
        return reservationMapper.fromReservationToReservationDto(reservationRepository.save(reservationEntity));
    }

    public String deleteReservationById(Long id) {
        var reservation = getReservation(id);
        if (!reservationEditor.isPresentDayBeforeReservationToChange(reservation))
            throw new IllegalStateException("Not allowed to delete started/completed reservations!");
        reservationRepository.delete(reservation);
        return "Reservation with id:" + id + " deleted successfully";

    }

    public ErrorsListDto changeReservationDatesByReservationId(Long id, LocalDate dateStart, LocalDate dateEnd) {

        ErrorsListDto errorsListDto = new ErrorsListDto(new ArrayList<>());

        if ( dateStart == null )  {
            errorsListDto.add("Please enter the date of when reservation starts !");
            errorsListDto.setFieldName("dateStart");
        }
        if ( dateEnd == null ) {
            errorsListDto.add("Please enter the date of when reservation ends !");
            errorsListDto.setFieldName("dateEnd");
        }
        if ( dateStart != null && dateEnd != null && !dateEnd.isAfter(dateStart) ) {
            errorsListDto.add("Date of reservation ending must be after date of start !");
            errorsListDto.setFieldName("dateEnd");
        }
        if ( !reservationEditor.isReservationAvailable(getReservation(id), dateStart, dateEnd) ) {
            errorsListDto.add("Other reservation is in progress during this period !");
            errorsListDto.setFieldName("dateEnd");
        }
        if ( errorsListDto.isListOfErrorsEmpty() ) {
            var reservation = getReservation(id);

            reservation.changeReservationDates(dateStart, dateEnd);
            BigDecimal cost = reservationWebMapper
                    .setTotalCost(reservationMapper
                            .fromReservationToReservationRequestDto(reservation));

            reservation.changeCost(cost);
            reservationRepository.save(reservation);
        }
        return errorsListDto;
    }

    public List<ReservationDto> getAllReservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        return reservationMapper.fromReservationListToReservationDtoList(reservationEntities);
    }

    public ReservationEntity getReservation(Long Id) {
        return reservationRepository.findById(Id)
                .orElseThrow(() -> new NoSuchElementException("ReservationEntity with id: " + Id + " not found!"));
    }

    public ReservationDto getReservationByReservationId(Long Id) {
        ReservationEntity reservationEntity = getReservation(Id);
        return reservationMapper.fromReservationToReservationDto(reservationEntity);
    }

    public List<ReservationEntity> findAllReservations() {
        return reservationRepository.findAll();
    }

    public List<ReservationDto> getAllReservationsByUserId(Long id) {
        UserEntity user = userRepository.findUserEntityById(id);
        List<ReservationEntity> allReservationsByUserId = reservationRepository.findAllByUserId(user);
        return reservationMapper.fromReservationListToReservationDtoList(allReservationsByUserId);
    }

    public List<ReservationDto> getAllReservationsByEmail(String email) {
        EmailValidator emailValidator = new EmailValidator(email);
        List<ReservationEntity> allReservationsByEmail = reservationRepository.findAllByUserIdEmail(emailValidator);
        return reservationMapper.fromReservationListToReservationDtoList(allReservationsByEmail);
    }

    public List<ReservationDto> getAllReservationsByCarId(Long id){
        List<ReservationEntity> allReservationsByCarId = reservationRepository.getAllReservationsByCarId(id);
        return reservationMapper.fromReservationListToReservationDtoList(allReservationsByCarId);
    }

    public int getNumberOfReservationsOfTheMostPopularCar() {
        return reservationRepository.getAllCarsByPopularityOfReservations().stream().mapToInt(i -> i).max()
                .orElse(0);
    }

    public int getNumberOfReservationsByCarId(Long id){
        return reservationRepository.getAllNumberOfReservationsById(id);
    }
}
