import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
import { importProvidersFrom } from '@angular/core';
import {UploadComponent} from './upload/upload.component'
import { AuthComponent } from './auth/auth.component';
// import { UploadComponent } from './components/upload/upload.component';
// import { DetectionResultsComponent } from './components/detection-results/detection-results.component';

export const routes: Routes = [
  { path: '', redirectTo: 'auth', pathMatch: 'full' }, // Redirect to auth by default
  { path: 'auth', component: AuthComponent },
  { path: 'upload', component: UploadComponent }
]
