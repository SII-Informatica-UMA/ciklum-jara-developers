import { Component } from '@angular/core';
import { Entrenador } from '../entrenador';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-formulario-entrenador',
  templateUrl: './formulario-entrenador.component.html',
  styleUrls: ['./formulario-entrenador.component.css']
})
export class FormularioEntrenadorComponent {
  accion?: "Añadir" | "Editar";
  entrenador: Entrenador = { idUsuario: 0, telefono: '', direccion: '', dni: '',
                              fechaNacimiento: new Date(), fechaAlta: new Date(), fechaBaja: new Date(),
                              especialidad: '', titulacion: '', experiencia: '',
                              observaciones: '', id: 0 };

  constructor(public modal: NgbActiveModal) { }

  guardarEntrenador(): void {
    this.modal.close(this.entrenador);
  }
}
