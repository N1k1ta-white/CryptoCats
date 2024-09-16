import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TelegramService } from '../services/telegram.service';
import { UserService } from '../services/user.service';
import { DataService } from '../services/data.service';
import { AppComponent } from '../app.component';
import {BehaviorSubject, Observable} from 'rxjs';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { GameService } from '../services/game.service';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [RouterModule, CommonModule, NgOptimizedImage],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css',
})
export class MainComponent {
  public name$ : Observable<string>;
  public coins$ : Observable<number>;
  public time$ : Observable<number>;
  public buttonState$ : BehaviorSubject<string>;

  readonly states = ["Open Egg", "Closed"];
  readonly open = 0;
  readonly close = 1;

  constructor(private telegramService: TelegramService,
            private userService: UserService,
            private dataService: DataService,
            private appComponent: AppComponent,
            private gameService: GameService,
          ) {
            this.name$ = dataService.getName();
            this.coins$ = dataService.getCoins();
            this.time$ = dataService.getTime();
            this.buttonState$ = new BehaviorSubject(this.states[this.close]);

            this.time$.subscribe(value => {
              if (value === 0 && this.buttonState$.value != this.states[this.open]) {
                this.buttonState$.next(this.states[this.open]);
              } else if (value !== 0 && this.buttonState$.value != this.states[this.close]) {
                this.buttonState$.next(this.states[this.close]);
              }
            })

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
    this.gameService.openEgg("something").subscribe({
      complete: () => console.log("OK"),
      error: () => console.log("err"),
    });
  }

  isClosed() : boolean {
    return this.buttonState$.value == this.states[this.close];
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
