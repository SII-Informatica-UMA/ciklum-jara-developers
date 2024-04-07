import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleEntrenadorComponent } from './detalle-entrenador.component';
import { Entrenador } from '../entrenador';
import { Usuario } from '../usuario';

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

  it('should trainer be undefined', () => {
    expect(component.entrenador).toBeUndefined();
  });

  it('should return a specific user', () => {
    const entrenadorMock: Entrenador = { idUsuario: 4, direccion: "Calle Principal 123",
                                      dni: "12345678A", fechaNacimiento: new Date("1990-05-15"), fechaAlta: new Date("2023-01-10"),
                                      fechaBaja: new Date("2023-12-31"), especialidad: "Entrenamiento de fuerza",
                                      titulacion: "Licenciado en Ciencias del Deporte", experiencia: "5 años de experiencia como entrenador personal",
                                      observaciones: "Paciente y comprometido con el progreso de los clientes", id: 1 };

    const usuarioMock: Usuario = component.getUsuarioPorPersona(entrenadorMock);
    expect(usuarioMock.id).toBe(4);
  });

});