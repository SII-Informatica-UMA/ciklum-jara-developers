import { Injectable } from '@angular/core';
import {Entrenador } from './entrenador';
import { Mensaje } from './mensaje';
import { Usuario } from './usuario';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EntrenadorService {

  private baseURI: string = 'http://localhost:8080';
  private entrenadorString: string = '/entrenador';
  private mensajeString: string = '/mensaje';

  constructor(private http: HttpClient) { }

  getEntrenador(id: number): Observable<Entrenador> {
    return this.http.get<Entrenador>(this.baseURI + this.entrenadorString + '/' + id);
  }


  editarEntrenador(entrenador: Entrenador): Observable<Entrenador> {
    return this.http.put<Entrenador>(this.baseURI + this.entrenadorString + '/' + entrenador.id, entrenador);
  }


  // quizas hay que mandarle el propio objeto Entrenador
  eliminarEntrenador(id: number): Observable<HttpResponse<string>> {
    return this.http.delete(this.baseURI + this.entrenadorString + '/' + id, {observe: "response", responseType: 'text'});
  }


  getMensajes(entrenador: Entrenador): Observable<Mensaje []> {
    return this.http.get<Mensaje []>(this.baseURI + this.mensajeString + this.entrenadorString + '?entrenador=' + entrenador.id);
  }


  crearMensaje(entrenador: Entrenador, asuntoM: string, contenidoM: string): Observable<Mensaje> {
    // Crear un objeto Mensaje con los datos necesarios
    const mensaje: Mensaje = {
      asunto: asuntoM,
      destinatarios: { id: entrenador.id, tipo: 'ENTRENADOR' },
      copia: { id: entrenador.id, tipo: 'ENTRENADOR' },
      copiaOculta: { id: entrenador.id, tipo: 'ENTRENADOR' },
      contenido: contenidoM,
      idMensaje: 0 // PREGUNTAR AL PROFESOR QUÉ DEBERÍA PONER AHÍ
    };

    return this.http.post<Mensaje>(this.baseURI + this.mensajeString +
                      this.entrenadorString + "?entrenador=" + entrenador.id,
                      mensaje);
  }


  getEntrenadoresCentro(idCentro: number): Observable<Entrenador []> {
    return this.http.get<Entrenador []>(this.baseURI + this.entrenadorString + "?centro=" + idCentro);
  }


  anadirEntrenadorCentro(user: Usuario, idCentro: number): Observable<Entrenador> {

    const entrenadorCreado: Entrenador = {
      idUsuario: user.id,
      telefono: '',
      direccion: '',
      dni: '',
      fechaNacimiento: new Date("2003-07-08"),
      fechaAlta: new Date("2015-05-01"),
      fechaBaja: new Date("2021-01-21"),
      especialidad: '',
      titulacion: '',
      experiencia: '',
      observaciones: '',
      id: 0 // PREGUNTAR AL PROFESOR QUE PONER AQUÍ
    };

    return this.http.post<Entrenador>(this.baseURI + this.entrenadorString + "?centro=" + idCentro,
                                      entrenadorCreado)
  }

  obtenerMensajeEntrenador(idMensaje: number): Observable<Mensaje> {
    return this.http.get<Mensaje>(this.baseURI + this.mensajeString + this.entrenadorString + '/' + idMensaje);
  }

  eliminarMensaje(id: number): Observable<HttpResponse<string>> {
    return this.http.delete(this.baseURI + this.mensajeString + this.entrenadorString + '/' + id, {observe: "response", responseType: 'text'});
  }
}
