import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { UserDto } from './user';
import { UserService } from './user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {

  public users: UserDto[] = [];
  public columns: any[] = [];

  constructor(private userService: UserService, private primengConfig: PrimeNGConfig) { }

  ngOnInit(): void {

    this.primengConfig.ripple = true;

    this.columns = [
      { field: 'id', header: 'ID'},
      { field: 'firstName', header: 'First Name'},
      { field: 'secondName', header: 'Second Name'},
      { field: 'phoneNumber', header: 'Phone Number'},
      { field: 'email', header: 'Email'}
  ];

    this.getUsers();
  }

  public getUsers(): void {
    this.userService.getUsers().subscribe((response: any) => {
      this.users = response;
      console.log(response);
    });

  }

}
