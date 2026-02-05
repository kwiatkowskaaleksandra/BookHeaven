import {Component, EventEmitter, HostBinding, Input, Output} from '@angular/core';
import {RouterLink} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'horizontal-menu',
  templateUrl: './horizontalMenu.html',
  standalone: true,
  styleUrls: ['./horizontalMenu.css'],
  imports: [
    RouterLink,
    NgOptimizedImage,
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
