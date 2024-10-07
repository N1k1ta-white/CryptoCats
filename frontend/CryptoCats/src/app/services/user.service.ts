import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../entity/user.entity';
import {PaginatedResponse} from "../util/page.util";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = "http://localhost:8080/api";

  constructor(private http: HttpClient) {}

  authUser(initData: any) : Observable<User> {
    console.log(JSON.stringify(initData));
    return this.http.post(this.url + "/users/auth", initData, {withCredentials: true});
  }

  logout() : Observable<any> {
    return this.http.post(this.url + "/logout", {}, {withCredentials: true});
  }

  getReferrals(size: number = 0, pageNumber : number = 10): Observable<PaginatedResponse<User>> {
    return this.http.get<PaginatedResponse<User>>(this.url + "/users/referrals" + ("?" + new URLSearchParams(
      {page : pageNumber.toString(), size : size.toString()}) ).toString(), {withCredentials: true});
  }
}
