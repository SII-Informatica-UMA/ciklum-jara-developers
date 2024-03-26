import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MensajeEntrenadorComponent } from './mensaje-entrenador.component';

describe('MensajeEntrenadorComponent', () => {
  let component: MensajeEntrenadorComponent;
  let fixture: ComponentFixture<MensajeEntrenadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MensajeEntrenadorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MensajeEntrenadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
