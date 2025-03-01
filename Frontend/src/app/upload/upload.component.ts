import { Component, ChangeDetectorRef } from '@angular/core';
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
//   firstPrediction: { x: number, y: number, width: number, height: number } | null = null;
firstPrediction: { x_min: number, y_min: number, x_max: number, y_max: number } | null = null;

    constructor(private cdr: ChangeDetectorRef) {}

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
                       "x": 1541.5,
                       "y": 2309.5,
                       "width": 1393,
                       "height": 1903,
                "confidence": 0.8626012802124023
              }
            ]
          }
        }
      ];
      this.firstPrediction = apiResponse[apiResponse.length-1]?.predictions?.predictions[0]
        ? {
            x_min: apiResponse[0].predictions.predictions[0].x - apiResponse[0].predictions.predictions[0].width / 2,
            y_min: apiResponse[0].predictions.predictions[0].y - apiResponse[0].predictions.predictions[0].height / 2,
            x_max: apiResponse[0].predictions.predictions[0].x + apiResponse[0].predictions.predictions[0].width / 2,
            y_max: apiResponse[0].predictions.predictions[0].y + apiResponse[0].predictions.predictions[0].height / 2
          }
        : null;


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
    this.firstPrediction = null;

  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
  }
}
