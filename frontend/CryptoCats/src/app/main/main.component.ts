import { Component, Inject, inject, OnInit } from '@angular/core';
import { Telegram } from '@twa-dev/types';

declare global {
  interface Window {
    Telegram: any;
  }
}


@Component({
  selector: 'app-main',
  standalone: true,
  imports: [],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {

  // constructor(private telegramService : TelegramService) {
  // }
  // private window;
  // public info : any;

  // constructor(@Inject(DOCUMENT) private win : any) {
  //   this.window = win;
  // }

  ngOnInit(): void {
    console.log(window?.Telegram?.WebApp?.initDataUnsafe);


    // console.log("HI");
    // let info = this.window.Telegram;
    // console.log(info);
      // let info = window.Telegram.init()q
      // console.log(info);
  }
}
