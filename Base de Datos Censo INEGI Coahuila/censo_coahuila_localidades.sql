-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: censo_coahuila
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `localidades`
--

DROP TABLE IF EXISTS `localidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `localidades` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `municipio_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `municipio_id` (`municipio_id`),
  CONSTRAINT `localidades_ibfk_1` FOREIGN KEY (`municipio_id`) REFERENCES `municipios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localidades`
--

LOCK TABLES `localidades` WRITE;
/*!40000 ALTER TABLE `localidades` DISABLE KEYS */;
INSERT INTO `localidades` VALUES (1,'Saltillo Centro',30),(2,'Mirasierra',30),(3,'Morelos',30),(4,'Nueva Tlaxcala',30),(5,'La Aurora',30),(6,'Rancho de Peña',30),(7,'Lomas del Refugio',30),(8,'Colonia Zaragoza',30),(9,'Colonia Bellavista',30),(10,'Monclova Centro',18),(11,'Colonia Guadalupe',18),(12,'Colonia Obrera Sur',18),(13,'Fraccionamiento Industrial',18),(14,'Colonia Del Río',18),(15,'Colonia Petrolera',18),(16,'Colonia El Pueblo',18),(17,'Torreón Centro',35),(18,'Colonia Abastos',35),(19,'Colonia Las Etnias',35),(20,'Fraccionamiento Los Viñedos',35),(21,'Colonia Nueva Laguna Norte',35),(22,'Colonia Rincón La Merced',35),(23,'Colonia Sol de Oriente',35),(24,'Piedras Negras Centro',25),(25,'Colonia Doctores',25),(26,'Colonia Lázaro Cárdenas',25),(27,'Colonia Mundo Nuevo',25),(28,'Colonia Tecnológico',25),(29,'Colonia Año 2000',25),(30,'Sabinas Centro',28),(31,'Colonia Sarabia',28),(32,'Colonia Las Palmas',28),(33,'Colonia Santo Domingo',28),(34,'Colonia La Madrid',28);
/*!40000 ALTER TABLE `localidades` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-02 16:59:55
