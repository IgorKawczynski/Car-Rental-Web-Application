import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDto } from './user';
import { environment } from 'src/environments/environment';
import { UserRequestDto } from './user-request-dto';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<UserDto[]> {
    return this.http.get<UserDto[]>(`${this.apiServerUrl}/users/all`);
  }
}
