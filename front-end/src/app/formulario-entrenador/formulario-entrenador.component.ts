import { Component } from '@angular/core';
import { Entrenador } from '../entrenador';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Usuario } from '../usuario';
import { EntrenadoresService } from '../entrenadores.service';

@Component({
  selector: 'app-formulario-entrenador',
  templateUrl: './formulario-entrenador.component.html',
  styleUrls: ['./formulario-entrenador.component.css']
})
export class FormularioEntrenadorComponent {
  usuariosDisponibles: Usuario[] = [];
  accion?: "Añadir" | "Editar";
  entrenador: Entrenador = { idUsuario: 0, direccion: '', dni: '',
                              fechaNacimiento: new Date(), fechaAlta: new Date(), fechaBaja: new Date(),
                              especialidad: '', titulacion: '', experiencia: '',
                              observaciones: '', id: 0 };

  constructor(private entrenadoresService: EntrenadoresService, public modal: NgbActiveModal) { }

  guardarEntrenador(): void {
    this.modal.close(this.entrenador);
  }

  ngOnInit(): void {
    this.usuariosDisponibles = this.entrenadoresService.getUsuariosDisponibles();
  }

  toNumero(cadena: string): number {
    return parseInt(cadena);
  }
}
