import { Injectable } from '@angular/core';
import { TelegramService } from './telegram.service';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  constructor(private telegramService: TelegramService) {
  }

  start() {
    this.setTheme(this.telegramService.getTheme());
    window.Telegram.WebApp.onEvent('themeChanged', () => {
      let updatedThemeParams: string = this.telegramService.getTheme();
      this.setTheme(updatedThemeParams);
    })
  }

  public setTheme(theme : string) : void {
    let prim: string;
    let secd: string;
    if (theme === "dark") {
      prim = "black"
      secd = "white"
    } else {
      prim = "white"
      secd = "black"
    }
    
    document.documentElement.style.setProperty("--primary-color", prim)
    document.documentElement.style.setProperty("--secondary-color", secd)
  }
}
