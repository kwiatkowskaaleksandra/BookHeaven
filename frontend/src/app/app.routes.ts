import {Route} from '@angular/router';
import {guestOnlyGuard} from './auth/login/guest-only.guard';

export const routes: Route[] = [{
  path: '',
  children: [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'login', loadComponent: () => import('./auth/login/login').then(m => m.Login), canActivate: [guestOnlyGuard]},
    { path: 'signup', loadComponent: () => import('./auth/signup/signup').then(m => m.SignupComponent), canActivate: [guestOnlyGuard]},
    { path: 'home', loadComponent: () => import('./home/home').then(m => m.HomeComponent)},

  ]
}

]
