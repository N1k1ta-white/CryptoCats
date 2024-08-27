import { HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { TelegramService } from '../services/telegram.service';
import { inject } from '@angular/core';
import { LoaderComponent } from '../loader/loader.component';
import { finalize } from 'rxjs';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const loader = inject(LoaderComponent);
  loader.setValue(true);
  // const telegramService = inject(TelegramService);
  // let id : string = JSON.parse(telegramService.getInfo() ?? "{}").user?.id ?? 0;
  // req = req.clone({
  //     setHeaders: {
  //       "Identity": [id]
  //     }
  // });
  return next(req).pipe(finalize(() => loader.setValue(false)));
};
