import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BookGenre} from '../interfaces';
import {environment} from '../../../environments/environments';
import {API_PATHS} from '../../api/api.paths';

@Injectable({providedIn: 'root'})
export class BookGenreService {
  constructor(private http: HttpClient) {
  }

  getAllBookGenre(): Observable<BookGenre[]> {
    return this.http.get<BookGenre[]>(`${environment.apiBaseUrl}${API_PATHS.bookGenre.getAll}`, {
      withCredentials: true
    })
  }


}
