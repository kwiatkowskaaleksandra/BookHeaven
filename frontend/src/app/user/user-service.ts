import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CurrentUser} from '../auth/login/auth-service';
import {environment} from '../../environments/environments';
import {API_PATHS} from '../api/api.paths';

@Injectable({providedIn: 'root'})
export class UserService {
  constructor(private http: HttpClient) {
  }

  aboutUser(): Observable<CurrentUser> {
    return this.http.get<CurrentUser>(`${environment.apiBaseUrl}${API_PATHS.users.aboutUser}`, {
      withCredentials: true,
    });
  }
}
