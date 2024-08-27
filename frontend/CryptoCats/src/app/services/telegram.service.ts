import { DOCUMENT } from '@angular/common';
import { Inject, Injectable, OnInit } from '@angular/core';
import { DataService } from './data.service';
import { Observable } from 'rxjs';
import { UserService } from './user.service';
import { Telegram, WebApp } from '@twa-dev/types';


@Injectable({
  providedIn: 'root'
})
export class TelegramService {

  private app : WebApp;
  private tg : Telegram;

  constructor(private dataService: DataService,
              private userServices: UserService,
  ) {
    this.tg = window.Telegram;
    this.app = this.tg.WebApp;
  }

  start(): Observable<any> {
    this.app.ready();
    this.app.themeParams;
    const initDataUnsafe = this.app.initDataUnsafe;
    this.dataService.saveUserData(initDataUnsafe);
    if  (initDataUnsafe.user?.username) {
      this.dataService.saveName(initDataUnsafe.user.username);
    } else {
      this.dataService.saveName(initDataUnsafe.user?.first_name + " " + initDataUnsafe.user?.last_name);
    }

    let id: number = initDataUnsafe.user?.id ?? 0;
    const auth : {id : number, initData: String } = {
      id: id, 
      initData: decodeURIComponent(this.app.initData)
     };
    return this.userServices.authUser(auth);
  }

  getWebApp() : WebApp {
    return this.app;
  }

  getTelegram() : Telegram {
    return this.tg;
  }

  getTheme() : string {
    return this.app.colorScheme;
  }
}
