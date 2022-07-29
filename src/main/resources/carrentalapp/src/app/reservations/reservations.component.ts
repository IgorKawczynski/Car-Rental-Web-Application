import { Component, OnInit } from '@angular/core';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { ReservationDto } from './reservations';
import { ReservationsService } from './reservations.service';
import { UserService } from '../user/user.service';
import { UserRequestDto } from '../user/user-request-dto';
import { Router } from '@angular/router';
import { ReservationDate } from './date';
import { ErrorsListDto } from '../errorsList/errors-list-dto';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {

  public reservations: ReservationDto[] = [];
  public columns: any[] = [];
  displayBasicCancel: boolean = false;
  displayBasicEdit: boolean = false;
  emailTemp: any;
  public from!: any;
  public to!: any;
  public price!: number;
  public date2: ReservationDate = new ReservationDate();
  public errorsListDto: ErrorsListDto = new ErrorsListDto();
  public rowId!: number;
  public xd!: string;

  constructor(
    private reservationsService: ReservationsService,
    private primengConfig: PrimeNGConfig,
    private router: Router,
    private messageService: MessageService) {
  }

  ngOnInit(): void {

    console.log(localStorage.getItem('email'))
    this.primengConfig.ripple = true;

    this.columns = [
      { field: 'brand', header: 'Brand'},
      { field: 'model', header: 'Model'},
      { field: 'dateStart', header: 'Start of reservation'},
      { field: 'dateEnd', header: 'End of reservation'},
      { field: 'cost', header: 'Total Cost'},
      { field: 'paymentInAdvance', header: 'Payment in Advance'}
  ];

    this.getReservations();
  }

  public btnClick(x: string) {
    this.router.navigateByUrl(x);
  }

  public btnReservations(){
    this.emailTemp = localStorage.getItem('email');
    this.getReservations
  }

  public btnDelete(){
    this.deleteReservationById();
    window.location.reload();
  }

  public btnEdit(){
    this.changeReservationDatesByReservationId();
    // window.location.reload();
  }

  public x: number = 0;
  public getReservations(): void {
    this.reservationsService.getAllReservationsByUserEmail(this.emailTemp).subscribe((response: any) => {
      this.reservations = response;
    });
  }

  public deleteReservationById(): void {
    console.log("DELETING RESERVATION WITH ID ");
    console.log(this.rowId);
    this.reservationsService.deleteReservationById(this.rowId).subscribe((response: any) => {
      console.log(response);
    });
  }

  public changeReservationDatesByReservationId(): void {
    this.date2.dateStart = this.from;
    this.date2.dateStart.setDate(this.date2.dateStart.getDate() + 1)
    console.log(this.rowId);
    console.log(this.from);
    this.date2.dateEnd = this.to;
    this.date2.dateEnd.setDate(this.date2.dateEnd.getDate() + 1)
    console.log(this.to);
    if( !this.from ) {
      console.log("FROM DATE CANNOT BE NULL !");
      this.messageService.add({life: 3000, severity:'error', summary:'Reservation', detail:' You must enter date of when reservation starts !'});
      this.errorsListDto.fieldName = "dateStart"
    }
    if( !this.to ) {
      console.log("TO DATE CANNOT BE NULL !");
      this.messageService.add({life: 3000, severity:'error', summary:'Reservation', detail:' You must enter date of when reservation ends !'});
      this.errorsListDto.fieldName = "dateEnd"
    }
    else {
      this.reservationsService.changeReservationDatesByReservationId(this.rowId, this.date2).subscribe((response: any) => {
      console.log(response);
        this.errorsListDto = response;
        if( this.errorsListDto.listOfErrorsEmpty ) {
        this.messageService.add({life: 3000, severity:'success', summary:'Reservation', detail:' You have successfully edited your reservation !'});
        this.displayBasicEdit = false;
        window.location.reload();
        }
        else {
          this.displayBasicEdit = true;
          this.errorsListDto.errors.forEach((error) =>
          this.messageService.add({life:3000, severity:'error', summary:'Reservation', detail: error})
          );
        }
    });
  }
  }

  public isToNull(): boolean {
    return this.to == null;
  }

  public isFieldNameDateStart(): boolean {
    return this.errorsListDto.fieldName == "dateStart";
  }
  public isFieldNameDateEnd(): boolean {
    return this.errorsListDto.fieldName == "dateEnd";
  }


  public btnReload(): void {
    window.location.reload();
  }
  showBasicDialogCancel(id: number) {
    this.displayBasicCancel = true;
    this.rowId = id;
  }

  showBasicDialogEdit(id: number) {
    this.displayBasicEdit = true;
    this.rowId = id;
  }
}
