import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CarFilterDto } from './car-filter-dto';

import { Car } from './car';

@Injectable({
  providedIn: 'root'
})
export class CarService {


  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiServerUrl}/cars`);
  }

  public getFilteredCars(carFilterDto: CarFilterDto): Observable<Car[]> {
    const valuesFromCarFilterDto = JSON.parse(JSON.stringify(carFilterDto));
    let queryParams = new HttpParams({
      fromObject: valuesFromCarFilterDto
    });
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.get<Car[]>(`${this.apiServerUrl}/api/filter/cars`, {params:queryParams, headers:headers});
  }

  public addCars(car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.apiServerUrl}/cars`, car);
  }

  public updateCars(car: Car): Observable<Car> {
    return this.http.put<Car>(`${this.apiServerUrl}/cars`, car);
  }

  public deleteCars(carId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/cars/${carId}`);
  }
}
