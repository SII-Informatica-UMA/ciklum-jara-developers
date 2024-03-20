import { Injectable } from '@angular/core';
import {Contacto } from './contacto';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContactosService {
  private baseURI: string = 'http://localhost:8080/api/agenda/contactos';

  constructor(private http: HttpClient) { }

  getContactos(): Observable<Contacto []> {
    return this.http.get<Contacto []>(this.baseURI);
  }

  addContacto(contacto: Contacto): Observable<Contacto> {
    return this.http.post<Contacto>(this.baseURI, contacto);
  }

  editarContacto(contacto: Contacto): Observable<Contacto> {
    return this.http.put<Contacto>(this.baseURI + '/' + contacto.id, contacto);
  }

  eliminarcContacto(id: number): Observable<HttpResponse<string>> {
    return this.http.delete(this.baseURI + '/' + id, {observe: "response", responseType: 'text'});
  }
}
