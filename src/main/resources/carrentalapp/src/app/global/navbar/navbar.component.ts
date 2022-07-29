import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(
    private router: Router,
    private http: HttpClient,
    private messageService: MessageService
    ) {
  }

  public emailTemp: any;
  items: MenuItem[] = [];

  ngOnInit(): void {

    this.emailTemp = localStorage.getItem('email');

    if(this.isLogged()) {

      this.items = [
        {
          label: 'Contact Us',
            items: [
              {label: 'Warsaw', routerLink: "/warsaw", icon: "pi pi-building", target: "tel:997"},
              {label: 'Bratislava', routerLink: "/bratislava", icon: "pi pi-building", target: "tel:997"},
              {label: 'Berlin', routerLink: "/berlin", icon: "pi pi-building", target: "tel:997"},
          ]
        },
        {
          label: 'Home',
          routerLink: '/'
        },
        {
          label: 'Rent a Car',
          routerLink: '/cars'
        },
        {
          label: 'My account',
          routerLink: '/edit'
        },
        {
          label: 'FAQ',
          routerLink: '/help'
        },
    ];

    }
    else {
      this.items = [
        {
          label: 'Contact Us',
            items: [
                {label: 'Warszawa', routerLink: "/warsaw",},
                {label: 'Poznań', routerLink: "/poznan",},
                {label: 'Berlin', routerLink: "/berlin",},
          ]
        },
        {
          label: 'Home',
          routerLink: '/'
        },
        {
          label: 'Rent a Car',
          routerLink: '/login'
        },
        {
          label: 'My account',
          routerLink: '/login'
        },
        {
          label: 'FAQ',
          routerLink: '/help'
        },
    ];
  }

}

  public isLogged() {
    return sessionStorage.length > 0;
  }

  public btnLogout() {
    this.logout();
  }

  public logout() {
    if(sessionStorage.length > 0){
      sessionStorage.removeItem('token')
      this.router.navigateByUrl('localhost:4200/');
      this.messageService.add({life:3000, severity:'success', summary:'Logout', detail:" You have successfully logged out!"})
    }
    else {
      this.messageService.add({life:3000, severity:'info', summary:'Logout', detail:" You have to log in first!"})
    }
  }

}
