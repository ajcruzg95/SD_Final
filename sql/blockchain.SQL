/*
Created: 13/02/2019
Modified: 13/02/2019
Model: MySQL 5.7
Database: MySQL 5.7
*/


-- Create tables section -------------------------------------------------

-- Table Persona

CREATE TABLE `Persona`
(
  `address_empadronado` Char(255),
  `Correo` Char(20),
  `Nombre` Char(20),
  `Apellido` Char(20),
  `DNI` Char(20),
  `Codigo` Int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Codigo`)
)
;

-- Table Transacciones

CREATE TABLE `Transacciones`
(
  `id_transaccion` Int(11) NOT NULL AUTO_INCREMENT,
  `address_origen` Varchar(255),
  `address_destino` Varchar(20),
  `id_bloque` Int(11),
  `hash_transaccion` Varchar(255),
  PRIMARY KEY (`id_transaccion`)
)
;

CREATE INDEX `IX_Relationship1` ON `Transacciones` (`id_bloque`)
;

-- Table Bloques

CREATE TABLE `Bloques`
(
  `id_bloque` Int(11) NOT NULL AUTO_INCREMENT,
  `hash_merkle` Varchar(255),
  `timestamp` Char(255),
  `previushash` Varchar(255),
  `nonce` Int(11),
  `hash_bloque` Varchar(255),
  `cant_transacciones` Int(11),
  PRIMARY KEY (`id_bloque`)
)
;

-- Create foreign keys (relationships) section ------------------------------------------------- 


ALTER TABLE `Transacciones` ADD CONSTRAINT `Relationship1` FOREIGN KEY (`id_bloque`) REFERENCES `Bloques` (`id_bloque`) ON DELETE RESTRICT ON UPDATE RESTRICT
;


