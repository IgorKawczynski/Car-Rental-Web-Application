import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EditUser } from './edit-user';
import { environment } from 'src/environments/environment';
import { UserRequestDto } from "../user/user-request-dto";
import { UserEditRequestDto } from "../user/user-edit-request-dto";

@Injectable({
    providedIn: 'root'
})
export class EditUserService {

  private apiServerUrl = environment.apiBaseUrl;
  emailTemp = localStorage.getItem('email');

  constructor(private http: HttpClient) { }

 public putUser(user: UserEditRequestDto): Observable<Object> {
    return this.http.put<any>(`${this.apiServerUrl}/users?email=${localStorage.getItem('email')}`, user);
  }

  public getUserByEmail(): Observable<UserEditRequestDto>{
    return this.http.get<UserEditRequestDto>(`${this.apiServerUrl}/users?email=${localStorage.getItem('email')}`);
  }
}
