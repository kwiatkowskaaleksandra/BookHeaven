import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZonelessChangeDetection
} from '@angular/core';
import {routes} from './app.routes';
import {provideRouter} from '@angular/router';
import {provideHttpClient, withInterceptors, withXsrfConfiguration} from '@angular/common/http';
import {authorizeInterceptor} from './auth/authorize-interceptor';
import { provideTranslateService } from '@ngx-translate/core';
import { provideTranslateHttpLoader } from '@ngx-translate/http-loader';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZonelessChangeDetection(),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([authorizeInterceptor]),
      withXsrfConfiguration({
        cookieName: 'X-CSRF-TOKEN',
        headerName: 'X-CSRF-TOKEN'
      })
    ),

    provideTranslateService({
      lang: 'pl',
      loader: provideTranslateHttpLoader({
        prefix: './assets/i18n/',
        suffix: '.json',
        useHttpBackend: true
      })
    })
  ]
}
