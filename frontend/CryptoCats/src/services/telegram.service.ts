import { DOCUMENT } from '@angular/common';
import { Inject, Injectable } from '@angular/core';
import { Telegram } from '@twa-dev/types';

declare global {
  interface Window {
    Telegram: Telegram;
  }
}

@Injectable({
  providedIn: 'root'
})
export class TelegramService {
  // private window;
  // private tg;
  // constructor(@Inject(DOCUMENT) private _document : any) {
  //   this.window  = this._document.defaultView;
  //   this.tg = this.window.Telegram.WebApp;
  // }

  // getInfo() : any {
  //   return this.tg.OnInit;
  // }
}
