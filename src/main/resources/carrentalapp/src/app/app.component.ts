import { Component, OnInit } from '@angular/core';
import { Car } from './cars/car';
import { CarService } from './cars/car.service';
import { PrimeNGConfig } from 'primeng/api';
import { Router } from '@angular/router';
import { RegistrationService } from './registration/registration.service';
import { UserRequestDto } from './user/user-request-dto';
import { HttpClient } from '@angular/common/http';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  public cars: Car[] = [];
  public columns: any[] = [];

  constructor(
    private app: AppService,
    private http: HttpClient,
    private carService: CarService,
    private registrationService: RegistrationService,
    private router: Router,
    ){
  }

  ngOnInit(): void{
  }

}
