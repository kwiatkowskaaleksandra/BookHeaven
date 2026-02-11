import {Component, HostListener, inject, OnInit, signal} from '@angular/core';
import {NgIcon, provideIcons} from '@ng-icons/core';
import {hugeSearching, hugeReload, hugeMultiplicationSign} from '@ng-icons/huge-icons';
import {MatButton, MatIconButton} from '@angular/material/button';
import {NonNullableFormBuilder, ReactiveFormsModule} from '@angular/forms';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {LangSwitchComponent} from '../../verticalMenu/lang-switch-component';
import {TranslatePipe} from '@ngx-translate/core';


interface GenreOption {
  code: string,
  namePL: string
}

@Component({
  selector: 'search-book',
  templateUrl: './search.html',
  styleUrls: ['./search.css'],
  imports: [
    NgIcon,
    MatIconButton,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    MatInput,
    MatButton,
    TranslatePipe
  ],
  viewProviders: [provideIcons({hugeSearching, hugeReload, hugeMultiplicationSign})]
})
export class Search {
  private fb = inject(NonNullableFormBuilder);

  // Jeśli masz listę gatunków z API, podepnij ją tutaj (np. przez @Input() albo serwis)
  genres: GenreOption[] = [
    { code: 'FANTASY', namePL: 'Fantasy' },
    { code: 'CRIME', namePL: 'Kryminał' },
  ];

  expanded = signal(false);

  form = this.fb.group({
    author: this.fb.control(''),
    title: this.fb.control(''),
    publisher: this.fb.control(''),
    genre: this.fb.control<string | null>(null),
  });

  toggle(): void {
    this.expanded.update(v => !v);
  }

  close(): void {
    this.expanded.set(false);
  }

  reset(): void {
    this.form.reset({ author: '', title: '', publisher: '', genre: null });
  }

  submit(): void {
    const value = this.form.getRawValue();

    // tutaj wywołujesz usługę do wyszukiwania, np.
    // this.booksService.search(value).subscribe(...)
    console.log('SEARCH:', value);

    // opcjonalnie: zamknij po wyszukaniu
    // this.close();
  }

  @HostListener('document:keydown.escape')
  onEsc(): void {
    if (this.expanded()) this.close();
  }

}
