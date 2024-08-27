import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private url: string = "http://localhost:8080/api/game/";

  constructor(private http: HttpClient
  ) {}

  openEgg(egg: any) : Observable<any> {
    console.log(JSON.stringify(egg));
    return this.http.get(this.url + "egg/1", {withCredentials: true});
  }
}
