import {Component, inject, OnInit} from '@angular/core';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import { NgIcon, provideIcons } from '@ng-icons/core';
import {hugeMenuCircle} from '@ng-icons/huge-icons'
import {BookGenreGroupsService} from '../../../book/book-genre-groups/book-genre-groups-service';
import {BookGenreGroup} from '../../../book/interfaces';

@Component({
  selector: 'nested-book-categories',
  templateUrl: './nested-book-categories.html',
  styleUrls: ['./nested-book-categories.css'],
  imports: [MatButtonModule, MatMenuModule, NgIcon],
  viewProviders: [provideIcons({hugeMenuCircle})]
})
export class NestedBookCategories implements OnInit{
  private bookGenreGroupsService = inject(BookGenreGroupsService);
  savedLang = ""
  bookGenreGroup: BookGenreGroup[] = [];

  getBookGenreGroups(): void {
    this.bookGenreGroupsService.getAllBookGenreGroups().subscribe({
      next: (res) => {

        this.bookGenreGroup = res
          .filter(group => group.active)
          .map(group => ({
            ...group,
            bookGenreSet: (group.bookGenreSet ?? []).filter(genre => genre.active)
          }))
          .filter(group => group.bookGenreSet.length > 0);
      },
      error: (err: unknown) =>{
        console.error('error: ',err);
      }
    })
  }



  ngOnInit(): void {
    this.savedLang = localStorage.getItem('lang') || 'pl';

    this.getBookGenreGroups()
  }


}
