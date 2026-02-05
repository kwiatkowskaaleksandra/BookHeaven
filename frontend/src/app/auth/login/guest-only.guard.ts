import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { map } from 'rxjs';
import {AuthService} from './auth-service';

export const guestOnlyGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.getAccessToken()) {
    return router.parseUrl('/home');
  }

  return auth.initFromRefresh().pipe(
    map(ok => (ok ? router.parseUrl('/home') : true))
  );
};
