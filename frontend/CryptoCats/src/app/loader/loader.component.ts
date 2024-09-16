import { Component } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-loader',
  standalone: true,
  imports: [],
  templateUrl: './loader.component.html',
  styleUrl: './loader.component.css'
})
export class LoaderComponent {
    private load = new BehaviorSubject<boolean>(true);

    isLoading() : Observable<boolean> {
      return this.load.asObservable();
    }

    setValue(val : boolean) : void {
      if (!val) {
        setTimeout(() => {this.load.next(val);}, 500);
      } else {
        this.load.next(val);
      }
    }
}
