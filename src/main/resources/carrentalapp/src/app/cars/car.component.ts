import { Component, OnInit } from '@angular/core';
import { Car } from './car';
import { CarService } from './car.service';
import { PrimeNGConfig } from 'primeng/api';
import { Router } from '@angular/router';
import { CarFilterDto } from './car-filter-dto';
import { BodyType } from './enums/body-type';
import { TypeOfFuel } from './enums/type-of-fuel';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent implements OnInit {

  public cars: Car[] = [];
  public columns: any[] = [];

  carFilterDto : CarFilterDto = new CarFilterDto();
  bodyTypes : BodyType[];
  fuelTypes : TypeOfFuel[];

  constructor(
    private carService: CarService,
    private primengConfig: PrimeNGConfig,
    private router: Router
    ){
      this.bodyTypes = [
        BodyType.SUV, BodyType.STATION_WAGON, BodyType.SEDAN, BodyType.HATCHBACK, BodyType.COUPE, BodyType.CABRIOLET
      ];
      this.fuelTypes = [
        TypeOfFuel.LPG, TypeOfFuel.ELECTRIC, TypeOfFuel.DIESEL, TypeOfFuel.BENZINE
      ]
  }

  ngOnInit(): void{
    this.primengConfig.ripple = true;
    this.columns = [
      { field: 'brand', header: 'Brand' },
      { field: 'model', header: 'Model' },
      { field: 'bodyTypeEnum', header: 'Body Type' },
      { field: 'typeOfFuelEnum', header: 'Type of Fuel' },
      { field: 'engineCapacity', header: 'Engine Capacity' },
      { field: 'productionYear', header: 'Production Year' },
      { field: 'pricePerDayRent', header: 'Cost of rent per day' },
      { field: 'status', header: 'Status'}
    ];
    this.getCars();
  }

  public getCars(): void {
    this.carService.getCars().subscribe((response: any) => {
      this.cars = response;
    });
  }

  public btnClick(url: string) {
    this.router.navigateByUrl(url);
  }

  public btnRefreshCarList(carFilterDto: CarFilterDto) {
    this.carService.getFilteredCars(carFilterDto).subscribe((response: any) => {
      this.cars = response;
    });
  }

  public clearBodyType(): void {
    this.carFilterDto.bodyType = null;
  }
}
