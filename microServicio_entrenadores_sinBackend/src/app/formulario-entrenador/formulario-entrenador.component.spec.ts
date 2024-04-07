import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormularioEntrenadorComponent } from './formulario-entrenador.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

describe('FormularioEntrenadorComponent', () => {
  let component: FormularioEntrenadorComponent;
  let fixture: ComponentFixture<FormularioEntrenadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormularioEntrenadorComponent],
      imports: [FormsModule],
      providers: [NgbActiveModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioEntrenadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should trainer id be the one initilized', () => {
    expect(component.entrenador.idUsuario).toBe(0);
  });

  it('should usuarios disponibles not be empty', () => {
    // Como al comienzo de la aplicacion quedan 8 usuarios sin asignar...
    expect(component.usuariosDisponibles.length).toBe(8);
  });

  it('should accion be undefined', () => {
    expect(component.accion).toBeUndefined();
  });
});