import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TelegramService } from '../services/telegram.service';
import { UserService } from '../services/user.service';
import { DataService } from '../services/data.service';
import { AppComponent } from '../app.component';
import {BehaviorSubject, Observable} from 'rxjs';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { GameService } from '../services/game.service';
import {OpeningComponent} from "../opening/opening.component";

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [RouterModule, CommonModule, NgOptimizedImage, OpeningComponent],
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
            private openComponent: OpeningComponent
          ) {
            this.name$ = dataService.getName();
            this.coins$ = dataService.getCoins();
            this.time$ = dataService.getTime();
            this.buttonState$ = new BehaviorSubject(this.states[this.close]);

            // TODO: extract from constructor
            this.time$.subscribe(value => {
              if (value === 0 && this.isClosed()) {
                this.buttonState$.next(this.states[this.open]);
              } else if (value !== 0 && !this.isClosed()) {
                this.buttonState$.next(this.states[this.close]);
              }
            })

  }

  logout(): void {
    this.userService.logout().subscribe({
      complete: () => console.log("Unauth"),
      error: () => console.log("Error")
    })
  }

  openEgg() {
    this.openComponent.execute();

    // TODO: erase
    this.gameService.openEgg("something").subscribe({
      complete: () => console.log("OK"),
      error: () => console.log("err"),
    });
  }

  isClosed() : boolean {
    return this.buttonState$.value == this.states[this.close];
  }

  // TODO: do it more efficient
  protected timestampToTime(timestamp : number | null) : string {

    if (timestamp == null || timestamp === 0) {
      return "0:00:00";
    }

    let date = new Date(timestamp * 1000);

    let [hours, minutes, seconds] = [date.getHours(),
                      date.getMinutes(),
                      date.getSeconds(),];

    return hours + ':' + this.formatNum(minutes) + ':' + this.formatNum(seconds);
  }

  private formatNum(num : number) : string {
    if (num > 10) {
      return num.toString();
    }
    return "0" + num.toString();
  }
}
