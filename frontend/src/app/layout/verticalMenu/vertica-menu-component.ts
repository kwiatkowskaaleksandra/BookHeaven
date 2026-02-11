import {Component, EventEmitter, inject, Input, Output} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {AsyncPipe, NgClass} from '@angular/common';
import { TranslatePipe } from '@ngx-translate/core';
import {LangSwitchComponent} from './lang-switch-component';
import {AuthService} from '../../auth/login/auth-service';

@Component({
  selector: 'vertical-menu',
  templateUrl: './verticalMenu.html',
  imports: [
    RouterLink,
    RouterLinkActive,
    NgClass,
    TranslatePipe,
    LangSwitchComponent,
    AsyncPipe,
  ],
  standalone: true,
  styleUrls: ['./verticalMenu.css']
})
export class VerticaMenuComponent {
  private auth = inject(AuthService);

  @Input() isExpanded: boolean = false;
  @Output() toggleExpanded = new EventEmitter<void>();

  isLoggedIn$ = this.auth.isLoggedIn();
  user$ = this.auth.currentUser();

  handleSidebarToggle(): void {
    this.toggleExpanded.emit();
  }

  handleLogout(): void {
    this.auth.logout()
  }
}
