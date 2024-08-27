import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../entity/user.entity';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = "http://localhost:8080/api";

  constructor(private http: HttpClient
  ) {}

  authUser(initData: any) : Observable<User> {
    console.log(JSON.stringify(initData));
    return this.http.post(this.url + "/users/auth", initData, {withCredentials: true});
  }

  logout() : Observable<any> {
    return this.http.post(this.url + "/logout", {}, {withCredentials: true});
  }

  test(): Observable<any> {
    return this.http.get(this.url + "/users", {withCredentials: true})
  }
}
