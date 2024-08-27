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
      this.load.next(val);
    }
}
