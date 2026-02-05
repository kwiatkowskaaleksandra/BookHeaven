import {Component, inject} from '@angular/core';
import {
  FormControl,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {AuthService} from './auth-service';
import {Router} from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import {HttpErrorResponse} from '@angular/common/http';

type LoginForm = FormGroup<{
  username: FormControl<string>;
  password: FormControl<string>;
}>;

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, TranslatePipe],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone: true
})
export class Login {
  private fb = inject(NonNullableFormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  errorMessage: string | null = null;

  form: LoginForm = this.fb.group({
    username: this.fb.control('', { validators: [Validators.required] }),
    password: this.fb.control('', { validators: [Validators.required, Validators.minLength(6)] }),
  });

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = {
      username: this.form.value.username!,
      password: this.form.value.password!
    };

    this.auth.login(payload).subscribe({
      next: () => {

        this.router.navigate(['/home']);
      },
      error: (err: unknown) => {

        if (err instanceof HttpErrorResponse) {
          if (err.status === 401 || err.status === 409) {
            this.errorMessage = 'ERROR.INCORRECT_LOGIN_OR_PASSWORD';
          } else if (err.status === 0) {
            this.errorMessage = 'ERROR.NO_CONNECTION_TO_THE_SERVER';
          } else {
            this.errorMessage = (err.error && (err.error.message || err.error.error))
              ? (err.error.message || err.error.error)
              : `ERROR.AN_ERROR_HAS_OCCURRED (${err.status}).`;
          }
        } else {
          this.errorMessage = 'ERROR.AN_UNEXPECTED_ERROR_HAS_OCCURRED';
        }
      }
    })
  }
}
