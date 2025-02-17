import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module'; // Import Material UI Components
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';
import {AuthComponent} from './auth/auth.component';
// Import Angular Material Modules
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [AppComponent],  // Add your components here
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    RouterModule.forRoot(routes),  // Ensure routing works
    MatCardModule,
  ],
  providers: [],
  bootstrap: [AppComponent]  // Main app component
})
export class AppModule { }
