import { Injectable } from '@angular/core';
import { Entrenador } from './entrenador';
import { Centro } from './centro';
import { Gerente } from './gerente';
import { KeyedRead } from '@angular/compiler';
import { Usuario } from './usuario';

@Injectable({
  providedIn: 'root'
})
export class EntrenadoresService {
  private gerentes_entrenadores: Map<[Centro, Gerente], Entrenador[]> = new Map();

  private allUsuarios: Usuario[] = [];

  constructor() {

    const usuario1: Usuario = { nombre: "Juan", apellido1: "García", apellido2: "López", email: "juan.garcia@example.com", id: 1 };
    
    const usuario2: Usuario = { nombre: "María", apellido1: "Martínez", apellido2: "González", email: "maria.martinez@example.com", id: 2 };
    
    const usuario3: Usuario = { nombre: "Luis", apellido1: "Rodríguez", apellido2: "Sánchez", email: "luis.rodriguez@example.com", id: 3 };
    
    const usuario4: Usuario = { nombre: "Ana", apellido1: "López", apellido2: "Hernández", email: "ana.lopez@example.com", id: 4 };
    
    const usuario5: Usuario = { nombre: "Carlos", apellido1: "Fernández", apellido2: "Pérez", email: "carlos.fernandez@example.com", id: 5 };
    
    const usuario6: Usuario = { nombre: "Laura", apellido1: "Díaz", apellido2: "Jiménez", email: "laura.diaz@example.com", id: 6 };
    
    const usuario7: Usuario = { nombre: "Pedro", apellido1: "Ruiz", apellido2: "Ramírez", email: "pedro.ruiz@example.com", id: 7 };
    
    const usuario8: Usuario = { nombre: "Sofía", apellido1: "Moreno", apellido2: "Iglesias", email: "sofia.moreno@example.com", id: 8 };
    
    const usuario9: Usuario = { nombre: "Pablo", apellido1: "Ortega", apellido2: "Vázquez", email: "pablo.ortega@example.com", id: 9 };
    
    const usuario10: Usuario = { nombre: "Elena", apellido1: "Serrano", apellido2: "Alonso", email: "elena.serrano@example.com", id: 10 };

    this.allUsuarios.push(usuario1);
    this.allUsuarios.push(usuario2);
    this.allUsuarios.push(usuario3);
    this.allUsuarios.push(usuario4);
    this.allUsuarios.push(usuario5);
    this.allUsuarios.push(usuario6);
    this.allUsuarios.push(usuario7);
    this.allUsuarios.push(usuario8);
    this.allUsuarios.push(usuario9);
    this.allUsuarios.push(usuario10);

    const entrenador1: Entrenador = { idUsuario: 4, telefono: "123456789", direccion: "Calle Principal 123",
                                      dni: "12345678A", fechaNacimiento: new Date("1990-05-15"), fechaAlta: new Date("2023-01-10"),
                                      fechaBaja: new Date("2023-12-31"), especialidad: "Entrenamiento de fuerza",
                                      titulacion: "Licenciado en Ciencias del Deporte", experiencia: "5 años de experiencia como entrenador personal",
                                      observaciones: "Paciente y comprometido con el progreso de los clientes", id: 1 };
    const entrenador2: Entrenador = { idUsuario: 5, telefono: "987654321", direccion: "Avenida Secundaria 456",
                                      dni: "87654321B", fechaNacimiento: new Date("1985-10-20"), fechaAlta: new Date("2022-07-05"),
                                      fechaBaja: new Date("2024-03-15"), especialidad: "Entrenamiento de resistencia",
                                      titulacion: "Entrenador personal certificado", experiencia: "Trabajó en varios gimnasios reconocidos",
                                      observaciones: "Especializado en maratones y triatlones", id: 2 };
    const entrenador3: Entrenador = { idUsuario: 9, telefono: "555555555", direccion: "Plaza Central 789",
                                      dni: "55555555C", fechaNacimiento: new Date("1988-12-03"), fechaAlta: new Date("2024-01-01"),
                                      fechaBaja: new Date("2024-12-31"), especialidad: "Entrenamiento funcional",
                                      titulacion: "Entrenador personal avanzado", experiencia: "Especializado en rehabilitación y prevención de lesiones",
                                      observaciones: "Ofrece clases grupales de entrenamiento funcional", id: 3 };

    const centro1: Centro = { nombre: "McFit", direccion: "Centro Comercial Carrefour Alameda", idCentro: 1 };

    const centro2: Centro = { nombre: "BasicFit", direccion: "Blvr. Louis Pasteur, 20", idCentro: 2 };

    const gerente1: Gerente = { idUsuario: 2, empresa: "McFit", id: 1 };

    const gerente2: Gerente = { idUsuario: 7, empresa: "BasicFit", id: 2 };

    this.agregarEntrenador(centro1, gerente1, entrenador1);
    this.agregarEntrenador(centro1, gerente1, entrenador2);
    this.agregarEntrenador(centro2, gerente2, entrenador3);
  }

  obtenerUsuarioPorPersona(persona: Gerente | Entrenador): Usuario {
    const usuarioEncontrado = this.allUsuarios.find(usuario => usuario.id === persona.idUsuario);
    if (usuarioEncontrado) {
      return usuarioEncontrado;
    } else {
      const usuarioVacio: Usuario = { nombre: "", apellido1: "", apellido2: "", email: "", id: -1 };
      return usuarioVacio;
    }
  }

  getAllEntrenadores() {
    return this.gerentes_entrenadores;
  }

  getEntrenadores(centro: Centro, gerente: Gerente): Entrenador[] | undefined {
    let centroExistente: [Centro, Gerente] | undefined;
    for (const [key, value] of this.gerentes_entrenadores) {
        if (key[0].idCentro === centro.idCentro && key[1].id === gerente.id) {
            centroExistente = key;
            break;
        }
    }
    if (centroExistente) {
      return this.gerentes_entrenadores.get(centroExistente);
    } else {
      return undefined;
    }
  }

  agregarEntrenador(centro: Centro, gerente: Gerente, entrenador: Entrenador) {
    // Obtener todas las matrices de entrenadores y combinarlas en una sola matriz
    const allEntrenadores: Entrenador[] = Array.from(this.gerentes_entrenadores.values()).flat();
    
    // Encontrar el ID máximo
    entrenador.id = allEntrenadores.length > 0 ? Math.max(...allEntrenadores.map(c => c.id)) + 1 : 1;

    // Verificar si el centro ya existe en el mapa
    let centroExistente: [Centro, Gerente] | undefined;
    for (const [key, value] of this.gerentes_entrenadores) {
        if (key[0].idCentro === centro.idCentro && key[1].id === gerente.id) {
            centroExistente = key;
            break;
        }
    }

    if (centroExistente) {
      console.log("El centro ya existe en el mapa.");
      // El centro y gerente ya existen, simplemente agregamos el entrenador al array existente
      this.gerentes_entrenadores.get(centroExistente)?.push(entrenador);
    } else {
      console.log("Agregando nuevo centro al mapa.");
      // El centro y gerente no existen, creamos un nuevo array y agregamos el entrenador
      this.gerentes_entrenadores.set([centro, gerente], [entrenador]);
    }

  }

  editarEntrenador(centro: Centro, gerente: Gerente, entrenador: Entrenador) {
    let keyExistente: [Centro, Gerente] | undefined;
    for (const [key, value] of this.gerentes_entrenadores) {
        if (key[0].idCentro === centro.idCentro && key[1].id === gerente.id) {
            keyExistente = key;
            break;
        }
    }

    if (keyExistente) {
      const entrenadores = this.gerentes_entrenadores.get(keyExistente);
      if (entrenadores) {
        let indice = entrenadores.findIndex(c => c.id === entrenador.id);
        if (indice !== -1) {
            entrenadores[indice] = entrenador;
        } else {
            console.error("No se encontró el entrenador con el ID especificado.");
        }
      } else {
        console.error("No se encontraron entrenadores para el centro y gerente especificados.");
      }
    }
  }

  eliminarEntrenador(centro: Centro, gerente: Gerente, idArray: number) {
    
    let keyExistente: [Centro, Gerente] | undefined;
    for (const [key, value] of this.gerentes_entrenadores) {
        if (key[0].idCentro === centro.idCentro && key[1].id === gerente.id) {
            keyExistente = key;
            break;
        }
    }

    if (keyExistente) {
      const entrenadores = this.gerentes_entrenadores.get(keyExistente);
      if (entrenadores) {
        let indice = entrenadores.findIndex(c => c.id == idArray);
        if (indice !== -1) {
            entrenadores.splice(indice, 1);
        } else {
            console.error("No se encontró el entrenador con el ID especificado.");
        }
      } else {
        console.error("No se encontraron entrenadores para el centro y gerente especificados.");
      }
    }

  }

  getGerentePorCentro(centro: Centro): Gerente | undefined {
    for (const [key, _] of this.gerentes_entrenadores) {
      if (key[0] === centro) {
        return key[1]; // Devolver el gerente asociado al centro
      }
    }
    return undefined; // Devolver undefined si no se encontró el centro
  }
}
