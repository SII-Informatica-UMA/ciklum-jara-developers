import { TestBed, tick } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { Centro } from './centro';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Entrenador } from './entrenador';

describe('AppComponent', () => {

  let modalServiceStub: Partial<NgbModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should initialize with empty values`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.gerentes_entrenadores.size).toBe(0);
    expect(app.centroElegido).toBeUndefined();
    expect(app.gerenteElegido).toBeUndefined();
    expect(app.entrenadorElegido).toBeUndefined();
  });

  it('should select a center', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const centroMock: Centro = { nombre: "McFit", direccion: "Centro Comercial Carrefour Alameda", idCentro: 1,
                                  rutaImagen: "https://www.abba.es/wp-content/uploads/2023/03/EKFKDpia.png",
                                  anoFundacion: "2012"};
    app.elegirCentro(centroMock);
    expect(app.centroElegido).toEqual(centroMock);
  });

  it('should select a trainer', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const entrenadorMock: Entrenador = { idUsuario: 4, direccion: "Calle Principal 123",
                                          dni: "12345678A", fechaNacimiento: new Date("1990-05-15"), fechaAlta: new Date("2023-01-10"),
                                          fechaBaja: new Date("2023-12-31"), especialidad: "Entrenamiento de fuerza",
                                          titulacion: "Licenciado en Ciencias del Deporte", experiencia: "5 años de experiencia como entrenador personal",
                                          observaciones: "Paciente y comprometido con el progreso de los clientes", id: 1 };
    app.elegirEntrenador(entrenadorMock);
    expect(app.entrenadorElegido).toEqual(entrenadorMock);
  });

  it('should initialize the map', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.ngOnInit();
    expect(app.gerentes_entrenadores.size).toBe(5);
    
  })

  it('should delete a selected trainer', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.ngOnInit();

    const centroMock: Centro = { nombre: "McFit", direccion: "Centro Comercial Carrefour Alameda", idCentro: 1,
                                  rutaImagen: "https://www.abba.es/wp-content/uploads/2023/03/EKFKDpia.png",
                                  anoFundacion: "2012"};
    app.elegirCentro(centroMock);
    app.gerenteElegido = { idUsuario: 2, empresa: "McFit", id: 1};
    const entrenadorMock: Entrenador = { idUsuario: 4, direccion: "Calle Principal 123",
                                      dni: "12345678A", fechaNacimiento: new Date("1990-05-15"), fechaAlta: new Date("2023-01-10"),
                                      fechaBaja: new Date("2023-12-31"), especialidad: "Entrenamiento de fuerza",
                                      titulacion: "Licenciado en Ciencias del Deporte", experiencia: "5 años de experiencia como entrenador personal",
                                      observaciones: "Paciente y comprometido con el progreso de los clientes", id: 1 };

    expect(app.getEntrenadoresDelCentroElegido()?.length).toBe(2);

    app.elegirEntrenador(entrenadorMock);
    app.eliminarEntrenador(entrenadorMock.id);
    expect(app.getEntrenadoresDelCentroElegido()?.length).toBe(1);
  })
});