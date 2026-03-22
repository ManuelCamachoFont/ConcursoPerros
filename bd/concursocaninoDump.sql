CREATE DATABASE  IF NOT EXISTS `concursocanino` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish2_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `concursocanino`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: concursocanino
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `duenos`
--

DROP TABLE IF EXISTS `duenos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `duenos` (
  `idDueno` int NOT NULL AUTO_INCREMENT,
  `nombreDueno` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `apellidosDueno` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  PRIMARY KEY (`idDueno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duenos`
--

LOCK TABLES `duenos` WRITE;
/*!40000 ALTER TABLE `duenos` DISABLE KEYS */;
/*!40000 ALTER TABLE `duenos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jueces`
--

DROP TABLE IF EXISTS `jueces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jueces` (
  `idJuez` int NOT NULL AUTO_INCREMENT,
  `nombreJuez` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `apellidosJuez` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `cargoJuez` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  PRIMARY KEY (`idJuez`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jueces`
--

LOCK TABLES `jueces` WRITE;
/*!40000 ALTER TABLE `jueces` DISABLE KEYS */;
INSERT INTO `jueces` VALUES (1,'Donald','Trump','Presidente de mesa'),(2,'Pedro','Sánchez','Adjunto 1'),(3,'José María','Aznar','Adjunto 2');
/*!40000 ALTER TABLE `jueces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perros`
--

DROP TABLE IF EXISTS `perros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perros` (
  `idPerro` int NOT NULL AUTO_INCREMENT,
  `nombrePerro` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `razaPerro` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `tamanoPerro` int NOT NULL,
  `colorPerro` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `puntuacionPerro` decimal(4,2) DEFAULT NULL,
  `idDuenoFK` int NOT NULL,
  PRIMARY KEY (`idPerro`),
  KEY `idDuenoFK` (`idDuenoFK`),
  CONSTRAINT `perros_ibfk_1` FOREIGN KEY (`idDuenoFK`) REFERENCES `duenos` (`idDueno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perros`
--

LOCK TABLES `perros` WRITE;
/*!40000 ALTER TABLE `perros` DISABLE KEYS */;
/*!40000 ALTER TABLE `perros` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-23  0:10:59
