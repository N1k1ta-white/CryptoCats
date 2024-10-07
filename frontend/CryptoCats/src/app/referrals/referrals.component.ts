import {Component} from '@angular/core';
import {Observable} from "rxjs";
import {UserService} from "../services/user.service";
import {AsyncPipe} from "@angular/common";
import {User} from "../entity/user.entity";
import {PageInfo, PaginatedResponse} from "../util/page.util";

@Component({
  selector: 'app-referrals',
  standalone: true,
  imports: [
    AsyncPipe
  ],
  templateUrl: './referrals.component.html',
  styleUrl: './referrals.component.css'
})
export class ReferralsComponent {
  public referrals$: Observable<any>;

  private readonly size = 10;
  private page : number = 0;


  constructor(private userService: UserService) {
    this.referrals$ = this.userService.getReferrals(this.size, this.page);
  }

  getUsers(response: PaginatedResponse<User>) : User[] {
    if (response._embedded != null) {
      const key = Object.keys(response._embedded).join();

      return response._embedded[key];
    }

    return [];
  }

  getTotalElements(response: PaginatedResponse<PageInfo>) : number {
    return response.page.totalElements;
  }
}
