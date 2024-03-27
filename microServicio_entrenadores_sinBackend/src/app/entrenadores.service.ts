import { Injectable } from '@angular/core';
import { Entrenador } from './entrenador';
import { Centro } from './centro';
import { Gerente } from './gerente';
import { KeyedRead } from '@angular/compiler';
import { Usuario } from './usuario';
import { Mensaje } from './mensaje';

@Injectable({
  providedIn: 'root'
})
export class EntrenadoresService {
  private gerentes_entrenadores: Map<[Centro, Gerente], Entrenador[]> = new Map();

  private mensajes: Map<number,Mensaje[]> = new Map();

  private allUsuarios: Usuario[] = [];

  constructor() {

    const usuario1: Usuario = { nombre: "Juan", apellido1: "García", apellido2: "López", email: "juan.garcia@example.com", id: 1, telefono: "647929183"};
    
    const usuario2: Usuario = { nombre: "María", apellido1: "Martínez", apellido2: "González", email: "maria.martinez@example.com", id: 2, telefono: "678123456"};
    
    const usuario3: Usuario = { nombre: "Luis", apellido1: "Rodríguez", apellido2: "Sánchez", email: "luis.rodriguez@example.com", id: 3, telefono: "612345678"};
    
    const usuario4: Usuario = { nombre: "Ana", apellido1: "López", apellido2: "Hernández", email: "ana.lopez@example.com", id: 4, telefono: "654987321"};
    
    const usuario5: Usuario = { nombre: "Carlos", apellido1: "Fernández", apellido2: "Pérez", email: "carlos.fernandez@example.com", id: 5, telefono: "666555444"};
    
    const usuario6: Usuario = { nombre: "Laura", apellido1: "Díaz", apellido2: "Jiménez", email: "laura.diaz@example.com", id: 6, telefono: "699888777"};
    
    const usuario7: Usuario = { nombre: "Pedro", apellido1: "Ruiz", apellido2: "Ramírez", email: "pedro.ruiz@example.com", id: 7, telefono: "678456789"};
    
    const usuario8: Usuario = { nombre: "Sofía", apellido1: "Moreno", apellido2: "Iglesias", email: "sofia.moreno@example.com", id: 8, telefono: "621234567"};
    
    const usuario9: Usuario = { nombre: "Pablo", apellido1: "Ortega", apellido2: "Vázquez", email: "pablo.ortega@example.com", id: 9, telefono: "676543210"};
    
    const usuario10: Usuario = { nombre: "Elena", apellido1: "Serrano", apellido2: "Alonso", email: "elena.serrano@example.com", id: 10, telefono: "688876543"};

    const usuario11: Usuario = { nombre: "Juan", apellido1: "Martinez", apellido2: "Gomez", email: "juan.martinez@example.com", id: 11, telefono: "677765432" };
  
    const usuario12: Usuario = { nombre: "Maria", apellido1: "Lopez", apellido2: "Fernandez", email: "maria.lopez@example.com", id: 12, telefono: "666654321" };
    
    const usuario13: Usuario = { nombre: "David", apellido1: "González", apellido2: "Martínez", email: "david.gonzalez@example.com", id: 13, telefono: "645987321" };

    const usuario14: Usuario = { nombre: "Sara", apellido1: "Torres", apellido2: "Sanz", email: "sara.torres@example.com", id: 14, telefono: "654123789" };

    const usuario15: Usuario = { nombre: "Javier", apellido1: "Jiménez", apellido2: "Blanco", email: "javier.jimenez@example.com", id: 15, telefono: "632478965" };

    const usuario16: Usuario = { nombre: "Eva", apellido1: "García", apellido2: "Martín", email: "eva.garcia@example.com", id: 16, telefono: "689753421" };

    const usuario17: Usuario = { nombre: "Miguel", apellido1: "Fernández", apellido2: "Santos", email: "miguel.fernandez@example.com", id: 17, telefono: "678954321" };

    const usuario18: Usuario = { nombre: "Lucía", apellido1: "Sanchez", apellido2: "Navarro", email: "lucia.sanchez@example.com", id: 18, telefono: "687456123" };

    const usuario19: Usuario = { nombre: "Rubén", apellido1: "Hernández", apellido2: "García", email: "ruben.hernandez@example.com", id: 19, telefono: "665214789" };

    const usuario20: Usuario = { nombre: "Carmen", apellido1: "Martín", apellido2: "Díaz", email: "carmen.martin@example.com", id: 20, telefono: "677894512" };

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
    this.allUsuarios.push(usuario11);
    this.allUsuarios.push(usuario12);
    this.allUsuarios.push(usuario13);
    this.allUsuarios.push(usuario14);
    this.allUsuarios.push(usuario15);
    this.allUsuarios.push(usuario16);
    this.allUsuarios.push(usuario17);
    this.allUsuarios.push(usuario18);
    this.allUsuarios.push(usuario19);
    this.allUsuarios.push(usuario20);



    

    const entrenador1: Entrenador = { idUsuario: 4, direccion: "Calle Principal 123",
                                      dni: "12345678A", fechaNacimiento: new Date("1990-05-15"), fechaAlta: new Date("2023-01-10"),
                                      fechaBaja: new Date("2023-12-31"), especialidad: "Entrenamiento de fuerza",
                                      titulacion: "Licenciado en Ciencias del Deporte", experiencia: "5 años de experiencia como entrenador personal",
                                      observaciones: "Paciente y comprometido con el progreso de los clientes", id: 1 };
    const entrenador2: Entrenador = { idUsuario: 5, direccion: "Avenida Secundaria 456",
                                      dni: "87654321B", fechaNacimiento: new Date("1985-10-20"), fechaAlta: new Date("2022-07-05"),
                                      fechaBaja: new Date("2024-03-15"), especialidad: "Entrenamiento de resistencia",
                                      titulacion: "Entrenador personal certificado", experiencia: "Trabajó en varios gimnasios reconocidos",
                                      observaciones: "Especializado en maratones y triatlones", id: 2 };
    const entrenador3: Entrenador = { idUsuario: 9, direccion: "Plaza Central 789",
                                      dni: "55555555C", fechaNacimiento: new Date("1988-12-03"), fechaAlta: new Date("2024-01-01"),
                                      fechaBaja: new Date("2024-12-31"), especialidad: "Entrenamiento funcional",
                                      titulacion: "Entrenador personal avanzado", experiencia: "Especializado en rehabilitación y prevención de lesiones",
                                      observaciones: "Ofrece clases grupales de entrenamiento funcional", id: 3 };

    const entrenador4: Entrenador = { idUsuario: 10, direccion: "Calle de los Deportes 321",
                                      dni: "10101010D", fechaNacimiento: new Date("1992-08-25"), fechaAlta: new Date("2023-03-10"),
                                      fechaBaja: new Date("2023-12-31"), especialidad: "Entrenamiento de flexibilidad",
                                      titulacion: "Entrenador de yoga certificado", experiencia: "Imparte clases de yoga desde hace 3 años",
                                      observaciones: "Promueve un enfoque holístico para la salud y el bienestar", id: 4};
      
    const entrenador5: Entrenador = { idUsuario: 12, direccion: "Calle Dr Miguel Diaz Recio",
                                      dni: "54723580F", fechaNacimiento: new Date("2000-03-23"), fechaAlta: new Date("2023-02-25"),
                                      fechaBaja: new Date("2023-12-23"), especialidad: "Entrenamiento de calistenia",
                                      titulacion: "Cursos de nutricion y entrenamiento", experiencia: "Entrena calistenia desde hace 7 años",
                                      observaciones: "Gan capacidad de enseñanza y motivacion", id: 5};
    const entrenador6: Entrenador = { idUsuario: 8, direccion: "Calle Badajoz",
                                      dni: "46765923G", fechaNacimiento: new Date("2000-04-23"), fechaAlta: new Date("2023-01-07"),
                                      fechaBaja: new Date("2023-12-03"), especialidad: "Entrenamiento funcional",
                                      titulacion: "Entrenador personal certificado", experiencia: "Experiencia en gimnasios de la competencia",
                                      observaciones: "Mucha disciplina y constancia en su trabajo", id: 6};

    const entrenador7: Entrenador = { idUsuario: 15, direccion: "Calle del Deporte 789",
                                      dni: "98765432H", fechaNacimiento: new Date("1987-06-12"), fechaAlta: new Date("2023-05-20"),
                                      fechaBaja: new Date("2024-06-30"), especialidad: "Entrenamiento de alta intensidad",
                                      titulacion: "Entrenador personal especializado", experiencia: "Trayectoria en el entrenamiento deportivo profesional",
                                      observaciones: "Enfoque en la superación personal y el rendimiento óptimo", id: 7 };
    
    const entrenador8: Entrenador = { idUsuario: 17, direccion: "Avenida de los Deportistas 456",
                                      dni: "23456789I", fechaNacimiento: new Date("1984-09-28"), fechaAlta: new Date("2022-11-15"),
                                      fechaBaja: new Date("2024-01-10"), especialidad: "Entrenamiento de resistencia muscular",
                                      titulacion: "Entrenador personal profesional", experiencia: "Trabajó con atletas de élite",
                                      observaciones: "Comprometido con el éxito de sus clientes", id: 8 };
    
    const entrenador9: Entrenador = { idUsuario: 20, direccion: "Calle Deportiva 101",
                                      dni: "87654321J", fechaNacimiento: new Date("1995-02-17"), fechaAlta: new Date("2023-08-10"),
                                      fechaBaja: new Date("2024-07-20"), especialidad: "Entrenamiento de Pilates",
                                      titulacion: "Instructor de Pilates certificado", experiencia: "Especializado en Pilates terapéutico",
                                      observaciones: "Fomenta el equilibrio entre cuerpo y mente", id: 9 };
    
    const entrenador10: Entrenador = { idUsuario: 16, direccion: "Avenida de la Salud 567",
                                       dni: "76543210K", fechaNacimiento: new Date("1993-11-05"), fechaAlta: new Date("2023-04-05"),
                                       fechaBaja: new Date("2024-03-20"), especialidad: "Entrenamiento de boxeo",
                                       titulacion: "Entrenador de boxeo profesional", experiencia: "Experiencia en el mundo del boxeo amateur",
                                       observaciones: "Apasionado por el arte del boxeo", id: 10 };
    
    const entrenador11: Entrenador = { idUsuario: 13, direccion: "Calle del Entrenamiento 321",
                                       dni: "54321098L", fechaNacimiento: new Date("1989-07-30"), fechaAlta: new Date("2022-10-12"),
                                       fechaBaja: new Date("2024-01-05"), especialidad: "Entrenamiento de artes marciales",
                                       titulacion: "Instructor de artes marciales certificado", experiencia: "Practicante de artes marciales desde la infancia",
                                       observaciones: "Transmite los valores del respeto y la disciplina", id: 11 };
    
    const entrenador12: Entrenador = { idUsuario: 14, direccion: "Avenida del Deporte 789",
                                       dni: "87654321M", fechaNacimiento: new Date("1986-04-18"), fechaAlta: new Date("2023-03-15"),
                                       fechaBaja: new Date("2024-02-28"), especialidad: "Entrenamiento de CrossFit",
                                       titulacion: "Entrenador de CrossFit certificado", experiencia: "Participó en competiciones de CrossFit",
                                       observaciones: "Fomenta el trabajo en equipo y la superación personal", id: 12 };
    

    const centro1: Centro = { nombre: "McFit", direccion: "Centro Comercial Carrefour Alameda", idCentro: 1,
                              rutaImagen: "https://www.abba.es/wp-content/uploads/2023/03/EKFKDpia.png",
                              anoFundacion: "2012"};

    const centro2: Centro = { nombre: "BasicFit", direccion: "Blvr. Louis Pasteur, 20", idCentro: 2,
                              rutaImagen: "https://www.vksport.eu/wp-content/uploads/2023/10/BF_Toolkit_Logo-Orange.png",
                              anoFundacion: "2015"};

    const centro3: Centro = { nombre: "Vals Sport Teatinos", direccion: "C/ Mesonero Romanos, 11", idCentro: 3,
                              rutaImagen: "https://www.centrodeportivoaxarquia.com/uploads/muKxJIAL/vals-sport-blanco.png",
                              anoFundacion: "2016"};
           
    
    const centro4: Centro = { nombre: "Real Club de Padel Marbella", direccion: "C. Río Amazonas, s/n, 29660 Marbella", idCentro: 4,
                              rutaImagen: "https://www.bartolomeconsultores.com/wp-content/uploads/logo-superior-padel.png",
                              anoFundacion: "2011"};
     
    const centro5: Centro = { nombre: "Forus Gym", direccion: " C. Malasaña, 4, 6, Distrito Centro, 29009 Málaga", idCentro: 5,
                              rutaImagen: "https://upload.wikimedia.org/wikipedia/commons/7/7c/Logo_Forus.png",
                              anoFundacion: "2009"};
    
    
    const gerente1: Gerente = { idUsuario: 2, empresa: "McFit", id: 1};

    const gerente2: Gerente = { idUsuario: 7, empresa: "BasicFit", id: 2};

    const gerente3: Gerente = { idUsuario: 1, empresa: "ValsSportTeatinos", id: 3};

    const gerente4: Gerente = { idUsuario: 11, empresa: "ValsSportConsul", id: 4};

    const gerente5: Gerente = { idUsuario: 18, empresa: "Forus Gym", id: 5};



    this.agregarEntrenador(centro1, gerente1, entrenador1);
    this.agregarEntrenador(centro1, gerente1, entrenador2);
    this.agregarEntrenador(centro2, gerente2, entrenador3);
    this.agregarEntrenador(centro3, gerente3, entrenador4);
    this.agregarEntrenador(centro4, gerente4, entrenador5);
    this.agregarEntrenador(centro4, gerente4, entrenador6);
    this.agregarEntrenador(centro5, gerente5, entrenador11);
    this.agregarEntrenador(centro5, gerente5, entrenador10);
    this.agregarEntrenador(centro5, gerente5, entrenador12);
    this.agregarEntrenador(centro2, gerente2, entrenador7);
    this.agregarEntrenador(centro5, gerente5, entrenador8);
    this.agregarEntrenador(centro3, gerente3, entrenador9);
  }

  obtenerUsuarioPorPersona(persona: Gerente | Entrenador): Usuario {
    console.log("El idUsuario de la persona es " + persona.idUsuario + " y el tipo es " + typeof persona.idUsuario);
    const usuarioEncontrado = this.allUsuarios.find(usuario => usuario.id === persona.idUsuario);
    console.log("El usuario extraido es " + usuarioEncontrado?.nombre + " .La longitud de allusuarios es " + this.allUsuarios.length);
    if (usuarioEncontrado) {
      return usuarioEncontrado;
    } else {
      const usuarioVacio: Usuario = { nombre: "", apellido1: "", apellido2: "", email: "", id: -1, telefono: ""};
      return usuarioVacio;
    }
  }

  obtenerUsuarioPorId(id: number): Usuario {
    const usuarioEncontrado = this.allUsuarios.find(usuario => usuario.id === id);
    if (usuarioEncontrado) {
      return usuarioEncontrado;
    } else {
      const usuarioVacio: Usuario = { nombre: "", apellido1: "", apellido2: "", email: "", id: -1, telefono: ""};
      return usuarioVacio;
    }
  }

  getUsuariosDisponibles(): Usuario[] {
    const usuariosAsignados: number[] = [];
    
    // Recorremos los entrenadores y obtenemos los IDs de usuario asignados
    this.gerentes_entrenadores.forEach(entrenadores => {
      entrenadores.forEach(entrenador => {
        usuariosAsignados.push(entrenador.idUsuario);
      });
    });
    
    // Filtramos los usuarios que no están asignados
    return this.allUsuarios.filter(usuario => !usuariosAsignados.includes(usuario.id));
  }

  getAllUsuarios(): Usuario[] {
    return this.allUsuarios;
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
    //console.log("Tratando de añadir entrenador con idUsuario " + entrenador.idUsuario + ", de tipo " + typeof entrenador.idUsuario);

    if (typeof entrenador.idUsuario == 'string') {
      entrenador.idUsuario = parseInt(entrenador.idUsuario);
    }

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
