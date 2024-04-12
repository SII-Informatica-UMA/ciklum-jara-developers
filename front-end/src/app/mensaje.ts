
export interface Mensaje {
    asunto: string;
    destinatarios: Destinatario [];
    copia: Destinatario [];
    copiaOculta: Destinatario [];
    contenido: string;
    idMensaje: number;
}

export interface Destinatario {
    id: number;
    tipo: 'CENTRO' | 'ENTRENADOR' | 'CLIENTE';  // Definición de unión de tipos literales
}

export const mensajesPorUsuario: Map<number, Mensaje[]> = new Map();

mensajesPorUsuario.set(4, [{ asunto: 'Reunión semanal', destinatarios: [{ id: 9, tipo: 'CENTRO' }], copia: [], copiaOculta: [], contenido: 'Estimado equipo, recuerden la reunión de esta tarde a las 4 PM. ¡Nos vemos allí!', idMensaje: 1 }, { asunto: 'Actualización de horarios', destinatarios: [{ id: 10, tipo: 'ENTRENADOR' }], copia: [], copiaOculta: [], contenido: 'Por favor, revisen los nuevos horarios actualizados para la próxima semana. ¡Gracias!', idMensaje: 2 }]);
mensajesPorUsuario.set(5, [{ asunto: 'Recordatorio de cita', destinatarios: [{ id: 4, tipo: 'CLIENTE' }], copia: [], copiaOculta: [], contenido: 'Hola, recuerda tu cita de entrenamiento hoy a las 5 PM. ¡Nos vemos en el gimnasio!', idMensaje: 3 }, { asunto: 'Confirmación de reserva', destinatarios: [{ id: 10, tipo: 'CENTRO' }, { id: 3, tipo: 'CLIENTE' }], copia: [], copiaOculta: [], contenido: 'Su reserva para la clase de yoga mañana a las 10 AM ha sido confirmada. ¡Esperamos verte allí!', idMensaje: 4 }]);
mensajesPorUsuario.set(9, [{ asunto: 'Nueva promoción', destinatarios: [{ id: 4, tipo: 'CENTRO' }, { id: 5, tipo: 'ENTRENADOR' }, { id: 6, tipo: 'CLIENTE' }], copia: [], copiaOculta: [], contenido: '¡No te pierdas nuestra nueva promoción de membresía! Obtén un 20% de descuento al referir a un amigo.', idMensaje: 5 }]);
mensajesPorUsuario.set(10, [{ asunto: 'Actualización del protocolo de seguridad', destinatarios: [{ id: 4, tipo: 'CENTRO' }, { id: 8, tipo: 'ENTRENADOR' }], copia: [], copiaOculta: [], contenido: 'Hemos actualizado nuestro protocolo de seguridad. Por favor, revisen las nuevas medidas antes de su próxima visita al gimnasio.', idMensaje: 6 }]);