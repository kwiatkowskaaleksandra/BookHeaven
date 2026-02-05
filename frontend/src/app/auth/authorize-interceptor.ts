import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {catchError, switchMap, throwError} from 'rxjs';
import {AuthService} from './login/auth-service';

const SKIP_AUTH = 'X-Skip-Auth';

function shouldSkipAuth(req: { headers: any; url: string }) {
  return req.headers.has(SKIP_AUTH) || req.url.includes('/refresh');
}

export const authorizeInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  if (shouldSkipAuth(req)) {
    return next(req.clone({ headers: req.headers.delete(SKIP_AUTH) }));
  }

  const token = authService.getAccessToken();
  const authReq = token ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : req;

  return next(authReq).pipe(
    catchError((err: HttpErrorResponse) => {
      if (err.status !== 401) return throwError(() => err);

      if (!token) return throwError(() => err);

      return authService.refreshToken().pipe(
        switchMap(newToken => {
          const retry = req.clone({
            setHeaders: { Authorization: `Bearer ${newToken}` },
          });
          return next(retry);
        }),
        catchError(refreshErr => {
          authService.logoutLocal();
          return throwError(() => refreshErr);
        })
      );
    })
  );
}
