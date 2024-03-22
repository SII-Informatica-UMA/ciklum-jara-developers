import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleEntrenadorComponent } from './detalle-entrenador.component';

describe('DetalleEntrenadorComponent', () => {
  let component: DetalleEntrenadorComponent;
  let fixture: ComponentFixture<DetalleEntrenadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetalleEntrenadorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleEntrenadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
