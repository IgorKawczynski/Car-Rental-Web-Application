import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { ErrorsListDto } from '../errorsList/errors-list-dto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  model: any = {};
  sessionId: any = "";
  errorsListDto: ErrorsListDto = new ErrorsListDto();

  constructor(
      private router: Router,
      private http: HttpClient,
      private messageService: MessageService
  ) { }

  ngOnInit(): void {

  }

  btnLogin() {
    this.login();
  }

  btnClick(x: string) {
    this.router.navigateByUrl(x);
  }

  login() {
    let url = '/api/login';
    this.http.post<any>(url, {
      email: this.model.email,
      password: this.model.password
    }).subscribe(

      res => {
        console.log(res);

      if (res.sessionId != null) {
        this.sessionId = res.sessionId;
        sessionStorage.setItem('token',this.sessionId);
        this.router.navigate(['localhost:4200/']);
        this.messageService.add({life:3000, severity:'success', summary:'Login', detail:" You have successfully logged in!"})
        localStorage.setItem('email', this.model.email)
      }
      else {
        res.errorsListDto.errors.forEach((error: any) =>
          this.messageService.add({life:10000, severity:'error', summary:'Logowanie', detail:error})
          );
      }
    });

  }
  history() {

  }


}
