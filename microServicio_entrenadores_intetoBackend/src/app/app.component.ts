import { Component, OnInit } from '@angular/core';
import { Entrenador } from './entrenador';
import {EntrenadorService } from './entrenador.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioEntrenadorComponent} from './formulario-contacto/formulario-contacto.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  entrenadores: Entrenador [] = [];
  entrenadorElegido?: Entrenador;
  centro: number = 0;

  constructor(private entrenadorService: EntrenadorService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.actualizaEntrenadores();
  }

  private actualizaEntrenadores(id?: number): void {
    this.entrenadorService.getEntrenadoresCentro(this.centro)
      .subscribe(entrenadores => {
        this.entrenadores = entrenadores;
        if (id) {
          this.entrenadorElegido = this.entrenadores.find(c => c.id == id);
        }
      });
  }

  elegirEntrenador(entrenador: Entrenador): void {
    this.entrenadorElegido = entrenador;
  }

  aniadirEntrenador(): void {
    let ref = this.modalService.open(FormularioEntrenadorComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.entrenador = { idUsuario: 0, telefono: '', direccion: '', dni: '', fechaNacimiento: new Date(), fechaAlta: new Date(), fechaBaja: new Date(), especialidad: '', titulacion: '', experiencia: '', observaciones: '', id: 0 };
    ref.result.then((entrenador: Entrenador) => {
      this.entrenadorService.anadirEntrenadorCentro(entrenador, this.centro)
        .subscribe(c => {
          this.actualizaEntrenadores();
        })
    }, (reason) => {});

  }

  contactoEditado(entrenador: Contacto): void {
    this.contactosService.editarContacto(contacto)
      .subscribe(c => {
        this.actualizaContactos(contacto.id);
      })
  }

  eliminarContacto(id: number): void {
    this.contactosService.eliminarcContacto(id)
      .subscribe(r => {
        this.actualizaContactos();
      });
    this.contactoElegido = undefined;
  }
}
