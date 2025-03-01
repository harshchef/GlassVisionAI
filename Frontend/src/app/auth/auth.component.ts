import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    HttpClientModule,
  ],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent {
  authForm: FormGroup;
  isLogin = true;
  private apiUrl = 'http://localhost:8081';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.authForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: [''],
    });
  }

  onSubmit() {
    if (this.authForm.invalid) return;

    const { email, password, confirmPassword } = this.authForm.value;

    if (!this.isLogin && password !== confirmPassword) {
      this.snackBar.open("Passwords don't match!", 'Close', { duration: 2000 });
      return;
    }

    if (this.isLogin) {
      this.login(email, password);
    } else {
      this.register(email, password);
    }
  }

  login(email: string, password: string) {
    this.http.post<any>(`${this.apiUrl}/login`, { username: email, password }).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('userId', response.userId);
        this.snackBar.open('Login successful!', 'Close', { duration: 2000 });
        this.router.navigate(['/upload']);
      },
      (error) => {
        this.snackBar.open('Login failed: Invalid credentials', 'Close', { duration: 2000 });
      }
    );
  }

  register(email: string, password: string) {
    this.http.post<any>(`${this.apiUrl}/register`, { username: email, password }).subscribe(
      () => {
        this.snackBar.open('Registration successful! Please log in.', 'Close', { duration: 2000 });
        this.isLogin = true;
      },
      (error) => {
        this.snackBar.open('Registration failed: ' + error.error, 'Close', { duration: 2000 });
      }
    );
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
