import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {VerticaMenuComponent} from './layout/verticalMenu/vertica-menu-component';
import {HorizontalMenuComponent} from './layout/horizontalMenu/horizontal-menu-component';
import {AuthService} from './auth/login/auth-service';
import {UserService} from './user/user-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, VerticaMenuComponent, HorizontalMenuComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})

export class App implements OnInit {
  private auth = inject(AuthService);
  private users = inject(UserService);

  sidebarExpanded = false;

  ngOnInit() {
    this.auth.initFromRefresh().subscribe((ok) => {
      if (!ok) return;

      this.users.aboutUser().subscribe({
        next: (user) => {
          this.auth.setCurrentUserFromMe(user)
        },
          error: (err) => {
            console.log(err)
            this.auth.logoutLocal()
          }
        });

    });
  }
}
