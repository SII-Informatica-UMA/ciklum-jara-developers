import { Component, OnInit } from '@angular/core';
import { Entrenador } from './entrenador';
import { Centro } from './centro';
import { Gerente } from './gerente';
import { Usuario } from './usuario';
import { EntrenadoresService } from './entrenadores.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { FormularioEntrenadorComponent } from './formulario-entrenador/formulario-entrenador.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  gerentes_entrenadores: Map<[Centro, Gerente], Entrenador[]> = new Map();
  centroElegido?: Centro;
  gerenteElegido?: Gerente;
  entrenadorElegido?: Entrenador;

  constructor(private entrenadoresService: EntrenadoresService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.gerentes_entrenadores = this.entrenadoresService.getAllEntrenadores();
  }

  getUsuarioPorPersona(persona: Gerente | Entrenador): Usuario {
    return this.entrenadoresService.obtenerUsuarioPorPersona(persona);
  }

  getEntrenadoresDelCentroElegido(): Entrenador[] | undefined {
    console.log("He entrado");
    if (!this.centroElegido || !this.gerenteElegido) {
      console.error("No se ha seleccionado un centro");
      return;
    } else {
      return this.entrenadoresService.getEntrenadores(this.centroElegido, this.gerenteElegido);
    }
  }

  elegirCentro(centro: Centro): void {
    this.centroElegido = centro;
    this.gerenteElegido = this.entrenadoresService.getGerentePorCentro(centro);
    this.entrenadorElegido = undefined;
  }

  elegirEntrenador(entrenador: Entrenador): void {
    this.entrenadorElegido = entrenador;
  }

  aniadirEntrenador(): void {

    if (!this.centroElegido || !this.gerenteElegido) {
      console.error("No se ha seleccionado un centro");
      return;
    }

    
    let ref = this.modalService.open(FormularioEntrenadorComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.entrenador = { idUsuario: 0, telefono: '', direccion: '', dni: '',
                                        fechaNacimiento: new Date(), fechaAlta: new Date(), fechaBaja: new Date(),
                                        especialidad: '', titulacion: '', experiencia: '',
                                        observaciones: '', id: 0 };
    ref.result.then((entrenador: Entrenador) => {
      if (this.centroElegido && this.gerenteElegido) {
          this.entrenadoresService.agregarEntrenador(this.centroElegido,this.gerenteElegido,entrenador);
          this.gerentes_entrenadores = this.entrenadoresService.getAllEntrenadores();
      }
    }, (reason) => {});

  }

  entrenadorEditado(entrenador: Entrenador): void {
    if (!this.centroElegido || !this.gerenteElegido) {
      console.error("No se ha seleccionado un centro");
      return;
    } else {
      this.entrenadoresService.editarEntrenador(this.centroElegido, this.gerenteElegido, entrenador);
      this.gerentes_entrenadores = this.entrenadoresService.getAllEntrenadores();
      this.entrenadorElegido = this.gerentes_entrenadores.get([this.centroElegido, this.gerenteElegido])?.find(c => c.id === entrenador.id);

      if (!this.entrenadorElegido) {
          console.error("No se encontró el entrenador editado en la lista actualizada.");
      }
    }
  }

  eliminarEntrenador(id: number): void {
    if (!this.centroElegido || !this.gerenteElegido) {
      console.error("No se ha seleccionado un centro");
      return;
    } else if (!this.entrenadorElegido) {
      console.error("No se ha seleccioado un entrenador");
    } else {
      this.entrenadoresService.eliminarEntrenador(this.centroElegido, this.gerenteElegido, id);
      this.gerentes_entrenadores = this.entrenadoresService.getAllEntrenadores();
      this.entrenadorElegido = undefined;
    }
  }
}
