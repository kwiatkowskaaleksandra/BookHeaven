import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environments';
import {API_PATHS} from '../../api/api.paths';
import {BookGenreGroup} from '../interfaces';

@Injectable({providedIn: 'root'})
export class BookGenreGroupsService {
  constructor(private http: HttpClient) {}

  getAllBookGenreGroups(): Observable<BookGenreGroup[]> {
    return this.http.get<BookGenreGroup[]>(`${environment.apiBaseUrl}${API_PATHS.booksGenreGroup.getAll}`, {
      withCredentials: true
    })
  }
}
