import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ErrorsListDto } from '../errorsList/errors-list-dto';
import { ReservationDto } from '../reservations/reservations';
import { ReservationsService } from '../reservations/reservations.service';
import { SelectedCarService } from '../selected-car/selected-car.service';
import { SelectedReservationDto } from './selected-reservation';
import { SelectedReservationService } from './selected-reservation.service';
import { SelectedCar } from '../selected-car/selected-car'

@Component({
    templateUrl: './selected-reservation.component.html',
    styleUrls: ['./selected-reservation.component.css']
})
export class SelectedReservationComponent implements OnInit {

    public selectedReservation = new ReservationDto();
    public reservationId!: number;
    public selectedCar: SelectedCar = new SelectedCar();
    public carId: number = 1;
    
    errorsListDto: ErrorsListDto = new ErrorsListDto();

  constructor(
    private selectedReservationService: SelectedReservationService,
    private selectedCarService: SelectedCarService,
    private _Activatedroute:ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    ) {
  }

    ngOnInit(): void {
      this.reservationId = this._Activatedroute.snapshot.params['id'];
      this.getReservationAndCar();
    }

    public getReservationAndCar(): void {
        this.selectedReservationService.getReservation(this.reservationId).subscribe((response: any) => {
          this.selectedReservation = response;
          this.carId = this.selectedReservation.carId;
          this.selectedCarService.getCar(this.carId).subscribe((response: any) => {
            this.selectedCar = response;
            this.selectedReservation.paymentInAdvance = this.selectedCar.newCarCost * 0.01;
            if (this.selectedReservation.paymentInAdvance > 1000) this.selectedReservation.paymentInAdvance = 500;
            this.selectedReservation.paymentInAdvance = Number(this.selectedReservation.paymentInAdvance.toFixed(2));
        })
        });
    }

    public btnBack() {
      history.back();
    }
}