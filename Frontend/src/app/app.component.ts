import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AuthComponent} from './auth/auth.component';
 // ✅ Import UploadComponent

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AuthComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Frontend';
}
