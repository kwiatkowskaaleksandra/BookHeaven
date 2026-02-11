import {Component, EventEmitter, HostBinding, Input, Output} from '@angular/core';
import {RouterLink} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';
import {NestedBookCategories} from './nested-book-categories/nested-book-categories';
import {Search} from './search/search';

@Component({
  selector: 'horizontal-menu',
  templateUrl: './horizontal-menu.html',
  standalone: true,
  styleUrls: ['./horizontal-menu.css'],
  imports: [
    RouterLink,
    NgOptimizedImage,
    NestedBookCategories,
    Search,
  ],
})
export class HorizontalMenuComponent {
  @Input() sidebarExpanded = false;

  @Input() isLoggedIn = false;
  @Input() username = '';

  @Output() toggleSidebar = new EventEmitter<void>();
  @Output() logoutClicked = new EventEmitter<void>();

  @HostBinding('class.sidebarExpanded')
  get expandedClass() {
    return this.sidebarExpanded;
  }
  onToggleSidebar() {
    this.toggleSidebar.emit();
  }

  onLogout() {
    this.logoutClicked.emit();
  }

}
