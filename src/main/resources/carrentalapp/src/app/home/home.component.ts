import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MenuItem } from 'primeng/api';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private router: Router,
    private http: HttpClient,
    private messageService: MessageService
    ) {
  }

  displayBasic: boolean = false;
  options: any;

  overlays: any[] = [];

  ngOnInit(): void {

  this.options = {
    center: {lat: 53.7732837, lng: 20.4570858},
    zoom: 12
  };

}

  public btnClick(url: string): void {
    this.router.navigateByUrl(url);
  }

  public isLogged() {
    return sessionStorage.length > 0;
  }

  public btnLogout() {
    this.logout();
  }

  public btnRent() {
    if(sessionStorage.length > 0){
      this.router.navigateByUrl('/cars')
    }
    else {
      this.router.navigateByUrl('/login')
      this.messageService.add({life:3000, severity:'info', summary:'Login', detail:" You have to log in first ! "})
    }
  }

  public logout() {
    if(sessionStorage.length > 0){
      sessionStorage.removeItem('token')
      this.router.navigateByUrl('localhost:4200/');
      this.messageService.add({life:3000, severity:'success', summary:'Logout', detail:" You have successfully logged out ! "})
    }
    else {
      this.messageService.add({life:3000, severity:'info', summary:'Logout', detail:" You have to log in first ! "})
    }
  }

  showBasicDialog() {
    this.displayBasic = true;
  }
}
