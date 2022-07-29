import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ReservationDto } from '../reservations/reservations';

@Injectable({
    providedIn: 'root'
})
export class SelectedReservationService {

    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient,) { }


    public getReservation(id: number): Observable<ReservationDto> {
        return this.http.get<ReservationDto>(`${this.apiServerUrl}/reservations/user/${id}`);
      }

}