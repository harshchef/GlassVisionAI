import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon'
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar'; // ✅ Import MatSnackBarModule
import { Router } from '@angular/router'; // ✅ Import Router

@Component({
  selector: 'app-auth',
  standalone: true,
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      MatCardModule,        // ✅ Angular Material Card
      MatFormFieldModule,   // ✅ Material Form Field
      MatInputModule,       // ✅ Material Input
      MatButtonModule,      // ✅ Material Button
      MatIconModule,

    ],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent {
  authForm: FormGroup;
  isLogin = true; // Toggle between login and register

  constructor(private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router) {
    this.authForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: [''], // Only used in register mode
    });
  }

  onSubmit() {
    if (this.authForm.invalid) return;

    const { email, password, confirmPassword } = this.authForm.value;

    if (!this.isLogin && password !== confirmPassword) {
      this.snackBar.open("Passwords don't match!", 'Close', { duration: 2000 });
      return;
    }

    console.log(this.isLogin ? 'Logging in...' : 'Registering...', this.authForm.value);

  // ✅ Redirect to /upload after login
    if (this.isLogin) {
      this.router.navigate(['/upload']);
    } else {
      this.snackBar.open("Registration successful! Please log in.", 'Close', { duration: 2000 });
      this.isLogin = true; // Switch to login mode after registration
    }
  }

  toggleMode() {
    this.isLogin = !this.isLogin;
    if (this.isLogin) {
      this.authForm.removeControl('confirmPassword');
    } else {
      this.authForm.addControl('confirmPassword', this.fb.control('', Validators.required));
    }
  }
}

