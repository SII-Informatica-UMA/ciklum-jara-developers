
export interface Entrenador {
  idUsuario: number;
  telefono: string;
  direccion: string;
  dni: string;
  fechaNacimiento: Date;  // Puedes considerar usar el tipo string si lo prefieres
  fechaAlta: Date;        // Puedes considerar usar el tipo string si lo prefieres
  fechaBaja: Date;        // Puedes considerar usar el tipo string si lo prefieres
  especialidad: string;
  titulacion: string;
  experiencia: string;
  observaciones: string;
  id: number;
}