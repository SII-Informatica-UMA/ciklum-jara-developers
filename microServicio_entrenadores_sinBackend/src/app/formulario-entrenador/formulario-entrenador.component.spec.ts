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
});
