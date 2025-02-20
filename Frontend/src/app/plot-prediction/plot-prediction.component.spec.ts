import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlotPredictionComponent } from './plot-prediction.component';

describe('PlotPredictionComponent', () => {
  let component: PlotPredictionComponent;
  let fixture: ComponentFixture<PlotPredictionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlotPredictionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlotPredictionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
