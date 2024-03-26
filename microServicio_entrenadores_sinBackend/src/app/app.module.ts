import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DetalleEntrenadorComponent } from './detalle-entrenador/detalle-entrenador.component';
import { FormularioEntrenadorComponent } from './formulario-entrenador/formulario-entrenador.component';
import { MensajeEntrenadorComponent } from './mensaje-entrenador/mensaje-entrenador.component';

@NgModule({
  declarations: [
    AppComponent,
    FormularioEntrenadorComponent,
    DetalleEntrenadorComponent,
    MensajeEntrenadorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
