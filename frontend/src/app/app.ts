import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {VerticalMenuComponent} from './layout/verticalMenu/verticalMenuComponent';
import {HorizontalMenuComponent} from './layout/horizontalMenu/horizontalMenuComponent';
import {AuthService} from './auth/login/auth-service';
import {UserService} from './user/user-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, VerticalMenuComponent, HorizontalMenuComponent],
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
