CREATE sequence entrenador_seq START WITH 1 increment by 50;
CREATE sequence mensaje_seq START WITH 1 increment by 50;
CREATE TABLE copia (
    mensaje_id bigint NOT NULL,
    id bigint,
    tipo varchar(255)
);
CREATE TABLE copia_oculta (
    mensaje_id bigint NOT NULL,
    id bigint,
    tipo varchar(255)
);
CREATE TABLE destinatarios (
    mensaje_id bigint NOT NULL,
    id bigint,
    tipo varchar(255)
);
CREATE TABLE entrenador (
    id bigint NOT NULL,
    direccion varchar(255),
    dni varchar(255),
    especialidad varchar(255),
    experiencia varchar(255),
    fecha_alta date,
    fecha_baja date,
    fecha_nacimiento date,
    id_centro bigint,
    id_usuario bigint,
    observaciones varchar(255),
    telefono varchar(255),
    titulacion varchar(255),
    PRIMARY KEY (id)
);
CREATE TABLE mensaje (
    id_mensaje bigint NOT NULL,
    asunto varchar(255),
    contenido varchar(255),
    id bigint,
    tipo varchar(255),
    entrenador_id bigint,
    PRIMARY KEY (id_mensaje)
);
ALTER TABLE IF EXISTS copia
ADD CONSTRAINT mensaje_copia_fk FOREIGN KEY (mensaje_id) REFERENCES mensaje;
ALTER TABLE IF EXISTS copia_oculta
ADD CONSTRAINT mensaje_copiaoculta_fk FOREIGN KEY (mensaje_id) REFERENCES mensaje;
ALTER TABLE IF EXISTS destinatarios
ADD CONSTRAINT mensaje_destinatario_fk FOREIGN KEY (mensaje_id) REFERENCES mensaje;
ALTER TABLE IF EXISTS mensaje
ADD CONSTRAINT entrenador_mensaje_fk FOREIGN KEY (entrenador_id) REFERENCES entrenador;