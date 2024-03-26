import { Component } from '@angular/core';
import { Entrenador } from '../entrenador';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Usuario } from '../usuario';
import { EntrenadoresService } from '../entrenadores.service';
import { Mensaje, mensajesPorUsuario } from '../mensaje';
import { Gerente } from '../gerente';

@Component({
  selector: 'app-mensaje-entrenador',
  templateUrl: './mensaje-entrenador.component.html',
  styleUrls: ['./mensaje-entrenador.component.css']
})
export class MensajeEntrenadorComponent {
  entrenador: Entrenador = { idUsuario: 0, direccion: '', dni: '',
                              fechaNacimiento: new Date(), fechaAlta: new Date(), fechaBaja: new Date(),
                              especialidad: '', titulacion: '', experiencia: '',
                              observaciones: '', id: 0 };

  allMensajes: Map<number, Mensaje[]> = mensajesPorUsuario;

  mensajeSeleccionado: number = -1;
  
  constructor(private entrenadoresService: EntrenadoresService, public modal: NgbActiveModal) { }

  getUsuarioPorPersona(persona: Gerente | Entrenador | undefined): Usuario {
    if (persona != undefined) {
      return this.entrenadoresService.obtenerUsuarioPorPersona(persona);
    } else {
      const usuarioVacio: Usuario = { nombre: "", apellido1: "", apellido2: "", email: "", id: -1, telefono: ""};
      return usuarioVacio;
    }
  }

  getMensajesEntrenadorConEmisor(): { mensaje: Mensaje, emisor: Usuario }[] | undefined {
    if (this.entrenador.idUsuario !== 0) {
      // Filtrar los mensajes donde el entrenador aparezca como destinatario
      const mensajesEntrenador: { mensaje: Mensaje, emisor: Usuario }[] = [];
      for (const [idUsuario, mensajes] of this.allMensajes) {
        for (const mensaje of mensajes) {
          const destinatarios = mensaje.destinatarios.filter(destinatario => destinatario.id === this.entrenador.idUsuario);
          if (destinatarios.length > 0) {
            const emisor = this.entrenadoresService.obtenerUsuarioPorId(idUsuario);
            mensajesEntrenador.push({ mensaje, emisor });
          }
        }
      }
      return mensajesEntrenador;
    } else {
      return [];
    }
  }


  seleccionarMensaje(num: number) {
    this.mensajeSeleccionado = num;
  }
}
