-- CREAR BD
CREATE DATABASE concursocanino
CHARSET utf8mb4
COLLATE utf8mb4_spanish2_ci;
-- ACTIVAR BD
USE concursocanino;
-- TABLA DUENOS
CREATE TABLE duenos (
    idDueno INT AUTO_INCREMENT,
    nombreDueno VARCHAR(45) NOT NULL,
    apellidosDueno VARCHAR(45) NOT NULL,
    PRIMARY KEY (idDueno)
);
-- TABLA PERROS
CREATE TABLE perros (
    idPerro INT AUTO_INCREMENT,
    nombrePerro VARCHAR(45) NOT NULL,
    razaPerro VARCHAR(45) NOT NULL,
    tamanoPerro INT NOT NULL,
    colorPerro VARCHAR(45) NOT NULL,
    idDuenoFK INT NOT NULL,
    PRIMARY KEY (idPerro),
    FOREIGN KEY (idDuenoFK)
        REFERENCES duenos (idDueno)
);
-- TABLA JUECES
CREATE TABLE jueces (
    idJuez INT AUTO_INCREMENT,
    nombreJuez VARCHAR(45) NOT NULL,
    apellidosJuez VARCHAR(45) NOT NULL,
    cargoJuez VARCHAR(45) NOT NULL,
    PRIMARY KEY (idJuez)
);
-- CREAR USUARIO
CREATE USER 'concurso'@'localhost' IDENTIFIED BY 'Studium2025#';
GRANT INSERT, SELECT, UPDATE, DELETE ON concursocanino.* TO 'concurso'@'localhost';
GRANT DROP ON concursocanino.* TO 'concurso'@'localhost';
FLUSH PRIVILEGES;

ALTER TABLE perros ADD puntuacionPerro DECIMAL (4,2) AFTER colorPerro;
ALTER TABLE perros MODIFY tamanoPerro VARCHAR(45) NOT NULL;
SELECT * FROM perros;
SELECT * FROM jueces;
SELECT * from duenos;
