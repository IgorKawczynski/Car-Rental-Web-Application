import { Component, OnInit } from '@angular/core';
import { SelectedCar } from './selected-car';
import { ReservationForCar } from './reservation-for-car';
import { SelectedCarService } from './selected-car.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { ReservationRequestDto } from '../reservations/reservation-request-dto';
import { ErrorsListDto } from '../errorsList/errors-list-dto';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-selected-car',
  templateUrl: './selected-car.component.html',
  styleUrls: ['./selected-car.component.css']
})
export class SelectedCarComponent implements OnInit {

  public reservations: ReservationForCar[] = [];
  public columns: any[] = [];
  public priceRent!: number;
  public selectedCar!: SelectedCar;
  public carId!: number;
  public from!: string;
  public to!: string;

  reservationRequestDto: ReservationRequestDto = new ReservationRequestDto();
  errorsListDto: ErrorsListDto = new ErrorsListDto();

  constructor(
    private selectedCarService: SelectedCarService,
    private _Activatedroute:ActivatedRoute,
    private router: Router,
    private messageService: MessageService
    ) {
  }
  ngOnInit(): void {
    this.carId = this._Activatedroute.snapshot.params['id'];
    this.getCar();

    this.columns = [
      { field: 'userId', header: 'UserId' },
      { field: 'carId', header: 'CarId' },
      { field: 'dateStart', header: 'Date of Start' },
      { field: 'dateEnd', header: 'Date of End' },
      { field: 'cost', header: 'Cost' },
      { field: 'paymentInAdvance', header: 'Payment in Advance' },
    ];
    this.getReservationsForCar();

    this.reservationRequestDto.carId = this.carId;
    this.reservationRequestDto.email = localStorage.getItem('email');
  }

  public finalPrice(): void{
    this.rentCost();
    this.payment();
  }

  public rentCost(): void {
    const fromDate = new Date(this.from);
    const toDate = new Date(this.to);
    var diff = toDate.getTime() - fromDate.getTime();
    if (diff > 0) {
      diff = Math.ceil(diff / (1000 * 3600 * 24));
      this.priceRent = diff * this.selectedCar.pricePerDayRent;
      this.priceRent = Number(this.priceRent.toFixed(2));
    }
      else this.priceRent = 0;
  }

  public payment(): void {
    this.selectedCar.paymentInAdvance = this.selectedCar.newCarCost * 0.001;
    if (this.selectedCar.paymentInAdvance < 1000) this.selectedCar.paymentInAdvance = 500;
    this.selectedCar.paymentInAdvance = Number(this.selectedCar.paymentInAdvance.toFixed(2));
  }

  public getCar(): void {
    this.selectedCarService.getCar(this.carId).subscribe((response: any) => {
      this.selectedCar = response;
    });
  }

  public getReservationsForCar(): void {

    this.selectedCarService.getReservationsForCar(this.carId).subscribe((response: any) => {
      this.reservations = response;
    });
  }

  public btnClick(x: string) {
    this.router.navigateByUrl(x);
  }
  
  public btnBack() {
    history.back();
  }

  btnReserve(): void {
    this.createReservation();
  }

  public createReservation() {
    this.reservationRequestDto.dateStart = new Date(this.from);
    this.reservationRequestDto.dateEnd = new Date(this.to);

    this.reservationRequestDto.dateStart.setDate(this.reservationRequestDto.dateStart.getDate() + 1)
    this.reservationRequestDto.dateEnd.setDate(this.reservationRequestDto.dateEnd.getDate() + 1)

    this.selectedCarService
    .createReservation(this.reservationRequestDto)
    .subscribe( (response: any) => {
      this.errorsListDto = response;

      if( !this.errorsListDto.listOfErrorsEmpty ) {
        this.errorsListDto.errors.forEach((error) =>
        this.messageService.add({life:10000, severity:'error', summary:'Reservation', detail:error})
        );

      }
      else{
        this.messageService.add({severity:'success', summary:'Reservation', detail:'Reserved successfully!'});
        this.router.navigateByUrl('/reservations')
        // this.router.navigateByUrl('/users');
        setTimeout(() => {
          window.location.reload();
       }, 2000);

      }
      });
    }

}
