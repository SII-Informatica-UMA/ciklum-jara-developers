import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Entrenador } from '../entrenador';
import { NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { FormularioEntrenadorComponent } from '../formulario-entrenador/formulario-entrenador.component';
import { EntrenadoresService } from '../entrenadores.service';

@Component({
  selector: 'app-detalle-entrenador',
  templateUrl: './detalle-entrenador.component.html',
  styleUrls: ['./detalle-entrenador.component.css']
})
export class DetalleEntrenadorComponent {
  @Input() entrenador?: Entrenador;
  @Output() entrenadorEditado = new EventEmitter<Entrenador>();
  @Output() entrenadorEliminado = new EventEmitter<number>();

  constructor(private entrenadorService: EntrenadoresService, private modalService: NgbModal) { }

  editarEntrenador(): void {
    let ref = this.modalService.open(FormularioEntrenadorComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.entrenador = {...this.entrenador};
    ref.result.then((entrenador: Entrenador) => {
      this.entrenadorEditado.emit(entrenador);
    }, (reason) => {});
  }

  eliminarEntrenador(): void {
    this.entrenadorEliminado.emit(this.entrenador?.id);
  }
}
