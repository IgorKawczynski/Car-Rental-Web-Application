import { Component, OnInit } from '@angular/core';
import { EditUser } from './edit-user';
import { EditUserService } from './edit-user.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { UserRequestDto } from '../user/user-request-dto';
import { UserService} from '../user/user.service';
import { UserEditRequestDto } from '../user/user-edit-request-dto';
import { MenuItem, MessageService } from 'primeng/api';
import { ErrorsListDto } from '../errorsList/errors-list-dto';

@Component({
    selector: 'app-edit-user',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.css']
  })
  export class EditUserComponent implements OnInit {

    user: UserEditRequestDto = new UserEditRequestDto();
    emailTemp: any;
    items: MenuItem[] = [];
    errorsListDto: ErrorsListDto = new ErrorsListDto();


    constructor(
      private editUserService: EditUserService,
      private router: Router,
      private messageService: MessageService
      ) {
    }

    public btnClick(url: string): void {
      this.router.navigateByUrl(url);
      };

    ngOnInit(): void {
      this.emailTemp = localStorage.getItem('email')
      this.getUserByEmail();
    }

    public btnUpdate() {
        console.log(this.user)
        this.updateUser();
    }

    public updateUser(): void {
      this.editUserService.putUser(this.user).subscribe((response: any) => {
        console.log(this.user);
        this.errorsListDto = response;
        if( !this.errorsListDto.listOfErrorsEmpty ) {
          this.errorsListDto.errors.forEach((error) =>
          this.messageService.add({life:3000, severity:'error', summary:'Account Edit', detail:error})
          );
        }
        else{
          this.messageService.add({life: 3000, severity:'success', summary:'Account Edit', detail:'You have successfully edited your account!'});
          window.location.reload();
        }
      });
    }

    public getUserByEmail(){
      this.emailTemp = localStorage.getItem('email');
      this.editUserService.getUserByEmail().subscribe((response: any) => {
        this.user = response;
      })
    }
  public isLogged() {
    return sessionStorage.length > 0;
  }
  }
