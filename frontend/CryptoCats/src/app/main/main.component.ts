import { Component, inject, OnInit } from '@angular/core';
import { TelegramWebappService } from '@zakarliuka/ng-telegram-webapp';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {
  readonly telegramService = inject(TelegramWebappService)

  ngOnInit(): void {
      let info = this.telegramService.initData;
      console.log(info);
  }
}
