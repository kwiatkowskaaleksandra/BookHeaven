import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, catchError, finalize, map, Observable, of, shareReplay, tap} from 'rxjs';
import { API_PATHS } from '../../api/api.paths';
import {environment} from '../../../environments/environments';

export interface LoginResponse {
  id: number;
  username: string;
  email: string;
  roles: string[];
  accessToken: string;
}

export interface CurrentUser {
  id: number;
  username: string;
  email: string;
  roles: string[];
}

export interface RegisterRequest {
  firstname: string;
  lastname: string;
  email: string;
  username: string;
  password: string;
  repeatedPassword: string;
}

const SKIP_AUTH = 'X-Skip-Auth';
const ACCESS_TOKEN_KEY = 'access_token';
const USER_KEY = 'current_user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private accessToken: string | null = sessionStorage.getItem(ACCESS_TOKEN_KEY);

  private loggedIn$ = new BehaviorSubject<boolean>(!!this.accessToken);
  private user$ = new BehaviorSubject<CurrentUser | null>(this.readUserFromStorage());

  private refreshInFlight$?: Observable<string>;

  constructor(private http: HttpClient) {  }

  getAccessToken(): string | null {
    return this.accessToken;
  }

  isLoggedIn(): Observable<boolean> {
    return this.loggedIn$.asObservable();
  }

  currentUser(): Observable<CurrentUser | null> {
    return this.user$.asObservable();
  }

  login(credentials: {username: string, password: string}): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiBaseUrl}${API_PATHS.auth.login}`, credentials, {
      withCredentials: true
    })
      .pipe(
        tap(res => {
          this.setAccessToken(res.accessToken);
          this.setUser({
            id: res.id,
            username: res.username,
            email: res.email,
            roles: res.roles,
          })
        }),
      )
  }

  signup(credentials: RegisterRequest): Observable<void> {
    return this.http.post<void>(`${environment.apiBaseUrl}${API_PATHS.auth.signup}`, credentials, {
      withCredentials: true
    })
  }

  refreshToken(): Observable<string> {
    if (this.refreshInFlight$) return this.refreshInFlight$;

    const headers = new HttpHeaders({ [SKIP_AUTH]: '1' });

    this.refreshInFlight$ = this.http
      .post<{ accessToken: string }>(`${environment.apiBaseUrl}${API_PATHS.auth.refresh}`, {}, { withCredentials: true, headers })
      .pipe(
        map(r => r.accessToken),
        tap(token => {
          if (!token) throw new Error('Refresh returned empty accessToken');
          this.setAccessToken(token);
        }),
        shareReplay(1),
        finalize(() => (this.refreshInFlight$ = undefined))
      );

    return this.refreshInFlight$;
  }

  initFromRefresh(): Observable<boolean> {
    if (this.accessToken) return of(true);

    return this.refreshToken().pipe(
      map(token => !!token),
      catchError(() => of(false))
    );
  }

  logoutLocal() {
    this.accessToken = null;
    sessionStorage.removeItem(ACCESS_TOKEN_KEY);
    sessionStorage.removeItem(USER_KEY);
    this.loggedIn$.next(false);
    this.user$.next(null);
  }

  private setAccessToken(token: string) {
    this.accessToken = token;
    sessionStorage.setItem(ACCESS_TOKEN_KEY, token);
    this.loggedIn$.next(true);
  }

  setCurrentUserFromMe(me: CurrentUser) {
    this.setUser(me);

    this.loggedIn$.next(true);
  }

  private setUser(me: CurrentUser) {
    this.user$.next(me);
    sessionStorage.setItem(USER_KEY, JSON.stringify(me));
  }

  private readUserFromStorage(): CurrentUser | null {
    const raw = sessionStorage.getItem(USER_KEY);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as CurrentUser;
    } catch {
      return null;
    }
  }

  logout(): void{
    this.http.post(`${environment.apiBaseUrl}${API_PATHS.auth.logout}`, {}, {withCredentials: true})
    .subscribe({
      next: () => this.logoutLocal(), error: () => this.logoutLocal()
      }
    )
  }
}
