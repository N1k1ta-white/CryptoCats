import { Component, HostListener, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TelegramService } from './services/telegram.service';
import { DataService } from './services/data.service';
import { UserService } from './services/user.service';
import { ThemeService } from './services/theme.service';
import { Observable } from 'rxjs';
import { User } from './entity/user.entity';
import { LoaderComponent } from './loader/loader.component';
import { CommonModule } from '@angular/common';
import {FooterComponent} from "./footer/footer.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, LoaderComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {

  public isLoading$ : Observable<boolean>;

  constructor(private telegramService: TelegramService,
              private dataService: DataService,
              private userService: UserService,
              private themeService: ThemeService,
              private loader: LoaderComponent,
  ) {
    this.isLoading$ = loader.isLoading();
  }

  private recoverTime : number = 3 * 60 * 60;

  ngOnInit(): void {
    // if (sessionStorage.getItem("data") !== null && sessionStorage.getItem("name") !== null) return;
    console.log("App initzilized");

    // TODO: themeService ???!!!
    // DEPRECATED
    this.themeService.start();

    // TODO: extract for more cleaner code
    this.telegramService.start().subscribe({
      next: (value : User) => {
          console.log(JSON.stringify(value));
          console.log("Received time", value.lastOpenedTime);
          let num = (value.capital ?? 0) / 100;
          let openTime = (value.lastOpenedTime ?? Math.floor(Date.now() / 1000)) + this.recoverTime;
          this.dataService.setCoins(num);
          this.dataService.setTime(openTime - Math.floor(Date.now() / 1000));
          this.loader.setValue(false);
      },
      complete: () =>  {
        console.log("Got a token")
      },
      error: () => console.log("Oh shit"),
    });
  }

  @HostListener('window:unload', ['$event'])
  unloadHandler(event: any) {
    navigator.sendBeacon("http://localhost:8080/api/logout");
  }
}
