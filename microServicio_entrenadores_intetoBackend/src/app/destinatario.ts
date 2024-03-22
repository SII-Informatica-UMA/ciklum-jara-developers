
export interface Destinatario {
    id: number;
    tipo: 'CENTRO' | 'ENTRENADOR' | 'CLIENTE';  // Definición de unión de tipos literales
}