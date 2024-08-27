import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TelegramService } from '../services/telegram.service';
import { UserService } from '../services/user.service';
import { DataService } from '../services/data.service';
import { AppComponent } from '../app.component';
import { delay, Observable, of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from '../loader/loader.component';
import { GameService } from '../services/game.service';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css',
})
export class MainComponent implements OnInit {
  public img : string = "";
  public name$ : Observable<string>;
  public coins$ : Observable<number>;
  public time$ : Observable<number>;

  constructor(private telegramService: TelegramService,
            private userService: UserService,
            private dataService: DataService,
            private appComponent: AppComponent,
            private gameService: GameService,
          ) {
            this.name$ = dataService.getName();
            this.coins$ = dataService.getCoins();
            this.time$ = dataService.getTime();
  }

  ngOnInit(): void {
    console.log(this.img);
  }

  test(): void {
    this.userService.test().subscribe({
      complete: () => console.log("OK"),
      error: () => console.log("Err")
    })
  }

  logout(): void {
    this.userService.logout().subscribe({
      complete: () => console.log("Unauth"),
      error: () => console.log("Error")
    })
  }

  openEgg() {
    this.gameService.openEgg("sometinh").subscribe({
      complete: () => console.log("OK"),
      error: () => console.log("err"),
    });
  }


  // TODO: more efficient
  protected timestampToTime(timestamp : number | null) {
    if (timestamp == null) {
      return "0";
    }
    var date = new Date(timestamp * 1000);

    // Hours part from the timestamp
    var hours = date.getHours();

    // Minutes part from the timestamp
    var minutes = "0" + date.getMinutes();

    // Seconds part from the timestamp
    var seconds = "0" + date.getSeconds();

    // Will display time in 10:30:23 format
    return hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
  }
}
