import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ReservationRequestDto } from 'src/app/reservations/reservation-request-dto';
import { SelectedCar } from './selected-car';
import { ReservationForCar } from './reservation-for-car';

@Injectable({
  providedIn: 'root'
})
export class SelectedCarService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient,) { }

  public getCar(id: number): Observable<SelectedCar> {
    return this.http.get<SelectedCar>(`${this.apiServerUrl}/cars/${id}`);
  }

  public getReservationsForCar(id: number): Observable<ReservationForCar> {
    return this.http.get<ReservationForCar>(`${this.apiServerUrl}/reservations/car/${id}`);
  }

  public createReservation(reservation: ReservationRequestDto): Observable<Object> {
    return this.http.post<ReservationRequestDto>(`${this.apiServerUrl}/api/reservation`, reservation);
  }

}
