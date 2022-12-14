import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router, private messageService: MessageService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {


      let token = sessionStorage.getItem('token');


      if (state.url == "/login") {
        if(token){
          this.messageService.add({life:3000, severity:'info', summary:'Login', detail:" You are already logged in ! "})
          return this.router.parseUrl('/');
        }
        return true;
      }

      if(!token) {
        let urlEdit = "/user/edit"
        let urlHistory = "/user/history"
        let urlCars = "/cars"
        let urlSelectedCar = "/selected-car"
        if(state.url == urlCars || state.url == urlHistory || state.url == urlEdit || state.url == urlSelectedCar) { //TU DODAC /user/**
          console.log("NIE JESTES ZALOGOWANY !!!!!!!!111!")
          this.messageService.add({life:3500, severity:'info', summary:'Login', detail:" You have to login first ! "})
          return this.router.parseUrl('/login');
        }
        return true;
      }

    return true;
  }

}
