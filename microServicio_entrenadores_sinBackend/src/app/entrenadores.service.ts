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

  constructor() {
    const entrenador1: Entrenador = { idUsuario: 10, telefono: "123456789", direccion: "Calle Principal 123",
                                      dni: "12345678A", fechaNacimiento: new Date("1990-05-15"), fechaAlta: new Date("2023-01-10"),
                                      fechaBaja: new Date("2023-12-31"), especialidad: "Entrenamiento de fuerza",
                                      titulacion: "Licenciado en Ciencias del Deporte", experiencia: "5 años de experiencia como entrenador personal",
                                      observaciones: "Paciente y comprometido con el progreso de los clientes", id: 1 };
    const entrenador2: Entrenador = { idUsuario: 21, telefono: "987654321", direccion: "Avenida Secundaria 456",
                                      dni: "87654321B", fechaNacimiento: new Date("1985-10-20"), fechaAlta: new Date("2022-07-05"),
                                      fechaBaja: new Date("2024-03-15"), especialidad: "Entrenamiento de resistencia",
                                      titulacion: "Entrenador personal certificado", experiencia: "Trabajó en varios gimnasios reconocidos",
                                      observaciones: "Especializado en maratones y triatlones", id: 2 };
    const entrenador3: Entrenador = { idUsuario: 23, telefono: "555555555", direccion: "Plaza Central 789",
                                      dni: "55555555C", fechaNacimiento: new Date("1988-12-03"), fechaAlta: new Date("2024-01-01"),
                                      fechaBaja: new Date("2024-12-31"), especialidad: "Entrenamiento funcional",
                                      titulacion: "Entrenador personal avanzado", experiencia: "Especializado en rehabilitación y prevención de lesiones",
                                      observaciones: "Ofrece clases grupales de entrenamiento funcional", id: 3 };

    const centro1: Centro = { nombre: "McFit", direccion: "Centro Comercial Carrefour Alameda", idCentro: 1 };

    const centro2: Centro = { nombre: "BasicFit", direccion: "Blvr. Louis Pasteur, 20", idCentro: 2 };

    const gerente1: Gerente = { idUsuario: 101, empresa: "Empresa del Gerente 1", id: 1 };

    const gerente2: Gerente = { idUsuario: 102, empresa: "Empresa del Gerente 2", id: 2 };

    this.agregarEntrenador(centro1, gerente1, entrenador1);
    this.agregarEntrenador(centro1, gerente1, entrenador2);
    this.agregarEntrenador(centro2, gerente2, entrenador3);
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
