import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { PlotPredictionComponent } from '../plot-prediction/plot-prediction.component';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule, PlotPredictionComponent],
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent {
  selectedFile: File | null = null;
  imagePreview: string | null = null;
  firstPrediction: { x: number, y: number, width: number, height: number } | null = null;

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        const result = reader.result;
        if (typeof result === 'string') {
          this.imagePreview = result; // Ensure it's a string
        }
      };
      reader.readAsDataURL(file);

      // Mock API response
      const apiResponse = [
        {
          "imageName": "OIP.jpg",
          "predictions": {
            "predictions": [
              {
                "x": 240,
                "y": 238,
                "width": 348,
                "height": 344,
                "confidence": 0.8626012802124023
              }
            ]
          }
        }
      ];

      this.firstPrediction = apiResponse[0]?.predictions?.predictions[0] || null;
    }
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    if (event.dataTransfer?.files.length) {
      this.selectedFile = event.dataTransfer.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        const result = reader.result;
        if (typeof result === 'string') {
          this.imagePreview = result; // Ensure it's a string
        }
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  removeImage() {
    this.selectedFile = null;
    this.imagePreview = null;
    this.firstPrediction = null; // Also clear the prediction box
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
  }
}
