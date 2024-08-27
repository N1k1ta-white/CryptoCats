import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private name = new BehaviorSubject<string>("null");
  private coins = new BehaviorSubject<number>(0); 
  private time = new BehaviorSubject<number>(0);
  private interval: any;

    constructor() {
      if (typeof this.getNameStorage() === 'string') {
        this.name.next(this.getNameStorage() ?? "null");
      }
      this.interval = setInterval(() => {
        const time = this.time.getValue();
        // if (time >= 0) {
          this.time.next(time - 1);
        // }
      }, 1000)
    }

    saveName(name : string) : void {
      sessionStorage.setItem("name", name);
      this.name.next(name);
    }

    private getNameStorage() : string | null {
      return sessionStorage.getItem("name");
    }

    getName() : Observable<string> {
      return this.name.asObservable();
    }

    getCoins() : Observable<number> {
      return this.coins.asObservable();
    }

    getTime() : Observable<number> {
      return this.time.asObservable();
    }

    setTime(timestamp: number) {
      this.time.next(timestamp);
    }

    setCoins(coins : number) {
      this.coins.next(coins);
    }
  
    saveUserData(data: any) {
      sessionStorage.setItem("data", JSON.stringify(data));
    }
  
    getUserData() {
      return sessionStorage.getItem("data");
    }
  
    eraseUserData() {
      sessionStorage.removeItem("data");
    }
  }