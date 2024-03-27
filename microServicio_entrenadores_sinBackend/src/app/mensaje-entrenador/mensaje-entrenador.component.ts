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
  destinatario: number[] = [];
  asunto: string = "";
  mensaje: string = "";

  allMensajes: Map<number, Mensaje[]> = mensajesPorUsuario;

  mensajeSeleccionado: number = -1;

  usuariosDisponibles: Usuario[] = [];

  ngOnInit(): void {
    this.usuariosDisponibles = this.entrenadoresService.getAllUsuarios();
  }

  getUsuariosDisponiblesMensaje(): Usuario[] {
    return this.usuariosDisponibles.filter(usuario => usuario.id !== this.entrenador.idUsuario);
  }
  
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

  generarIdMensaje(): number {
    let maxIdMensaje = 0;
  
    // Iterar sobre todos los mensajes en el mapa
    this.allMensajes.forEach(mensajes => {
      mensajes.forEach(mensaje => {
        // Comparar el idMensaje actual con el máximo encontrado hasta el momento
        if (mensaje.idMensaje > maxIdMensaje) {
          maxIdMensaje = mensaje.idMensaje;
        }
      });
    });
  
    // Incrementar el máximo idMensaje encontrado en 1
    return maxIdMensaje + 1;
  }
  

  enviarMensaje(): void {
    //console.log("ESTOY AQUI " + this.entrenador.idUsuario + ", " + this.destinatario + ", " + this.asunto + ", " + this.mensaje);
    // Crear un nuevo mensaje con los datos del formulario
    // Obtener el array de mensajes del entrenador del Map

    let mensajesEntrenador = this.allMensajes.get(this.entrenador.idUsuario);

    if (!mensajesEntrenador) {
      mensajesEntrenador = [];
    }
    

    for(const id of this.destinatario) {
      const nuevoMensaje: Mensaje = {
        asunto: this.asunto,
        destinatarios: [{ id: id, tipo: 'ENTRENADOR' }], // Aquí puedes ajustar el tipo según corresponda
        copia: [],
        copiaOculta: [],
        contenido: this.mensaje,
        idMensaje: this.generarIdMensaje() // Generar un id único para el mensaje (puedes implementar esta función)
      };
      // Agregar el nuevo mensaje al array de mensajes del destinatario
      mensajesEntrenador.push(nuevoMensaje);

    }

    // Actualizar el Map con el nuevo array de mensajes
    this.allMensajes.set(this.entrenador.idUsuario, mensajesEntrenador);

    console.log("Una vez actualizado el mapa, length = " + this.allMensajes.get(this.entrenador.idUsuario)?.length);

    this.modal.close(this.entrenador);
  }


  seleccionarMensaje(num: number) {
    this.mensajeSeleccionado = num;
  }

  
}
