import {Component} from '@angular/core';
import {CryptoCat} from "../entity/cryptoCat.entity";
import {AsyncPipe, NgClass, NgOptimizedImage} from "@angular/common";
import {GameService} from "../services/game.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-opening',
  standalone: true,
  imports: [
    NgClass,
    NgOptimizedImage,
    AsyncPipe
  ],
  templateUrl: './opening.component.html',
  styleUrl: './opening.component.css'
})
export class OpeningComponent {

  readonly active = 0;
  readonly disabled = 1;

  private currState : number = this.disabled;

  public cat$ : Subject<CryptoCat>;

  constructor(private gameService: GameService) {

    this.cat$ = new Subject<CryptoCat>();
  }

  execute() : void {
    this.currState = this.active;

    this.gameService.openEgg("")

    this.currState  = this.disabled;
  }

  isActive() : boolean {
    return this.currState === this.active;
  }
}
