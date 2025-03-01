import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-plot-prediction',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './plot-prediction.component.html',
  styleUrls: ['./plot-prediction.component.css']
})
export class PlotPredictionComponent {
  @Input() imageSrc: string | null = null;
  @Input() prediction: { x_min: number, y_min: number, x_max: number, y_max: number } | null = null;
}
