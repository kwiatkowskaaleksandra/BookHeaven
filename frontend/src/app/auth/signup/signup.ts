import {ChangeDetectorRef, Component, inject} from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {TranslatePipe} from '@ngx-translate/core';
import {AuthService} from '../login/auth-service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

type SignupForm = FormGroup<{
  firstname: FormControl<string>;
  lastname: FormControl<string>;
  email: FormControl<string>;
  username: FormControl<string>;
  password: FormControl<string>;
  repeatedPassword: FormControl<string>;
}>;

@Component({
  selector: 'signup',
  templateUrl: './signup.html',
  styleUrls: ['./signup.css'],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    TranslatePipe
  ],
  standalone: true
})
export class SignupComponent {
  private fb = inject(NonNullableFormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef)

  errorMessage: string | null = null;

  form: SignupForm = this.fb.group({
    firstname: this.fb.control('', {validators: [Validators.required]}),
    lastname: this.fb.control('', {validators: [Validators.required]}),
    email: this.fb.control('', {validators: [Validators.required, Validators.email]}),
    username: this.fb.control('', { validators: [Validators.required] }),
    password: this.fb.control('', { validators: [Validators.required, Validators.minLength(10)] }),
    repeatedPassword: this.fb.control('', { validators: [Validators.required, Validators.minLength(6)] }),
  });

  submit() {
    this.errorMessage = null;
    this.cdr.markForCheck();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = {
      firstname: this.form.value.firstname!,
      lastname: this.form.value.lastname!,
      email: this.form.value.email!,
      username: this.form.value.username!,
      password: this.form.value.password!,
      repeatedPassword: this.form.value.repeatedPassword!,
      roles: ['user']
    };

    this.auth.signup(payload).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err: unknown) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 401 || err.status === 409) {
            this.errorMessage = 'ERROR.'+err.error.message;
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
        this.cdr.markForCheck();
      }
    })
  }

}
