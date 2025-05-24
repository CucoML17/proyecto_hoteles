-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: tendspring5
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado` int DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Alimentos',1,'comi.png','Alimento'),(2,'Postres y comidas pequeñas',1,'sope.jpg','Postres'),(3,'Ala',1,'cardan.jpg','Papelería');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre_completo` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `id_vendedor` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKle6w4ybb4cj4bq8yoq89fs9pq` (`id_vendedor`),
  CONSTRAINT `FKle6w4ybb4cj4bq8yoq89fs9pq` FOREIGN KEY (`id_vendedor`) REFERENCES `vendedor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Av, José Suadero','Arnoldo de la O','7575678910',3),(2,'Av, José Francisco Ruiz Massieu No. 12','Trixie','8658474674',1);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalleventa`
--

DROP TABLE IF EXISTS `detalleventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalleventa` (
  `cantidad` int DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `idproducto` int NOT NULL,
  `idventa` int NOT NULL,
  PRIMARY KEY (`idproducto`,`idventa`),
  KEY `FK8v1rumcdv4iq5wr8pt5ysj2ch` (`idventa`),
  CONSTRAINT `FK8i70db592msf9bk0rbko1bb1l` FOREIGN KEY (`idproducto`) REFERENCES `producto` (`id`),
  CONSTRAINT `FK8v1rumcdv4iq5wr8pt5ysj2ch` FOREIGN KEY (`idventa`) REFERENCES `venta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleventa`
--

LOCK TABLES `detalleventa` WRITE;
/*!40000 ALTER TABLE `detalleventa` DISABLE KEYS */;
INSERT INTO `detalleventa` VALUES (12,146.64000000000001,1,1),(4,48.88,1,5),(1,12.22,2,1),(1,12,2,2),(1,12,2,3),(3,36,2,4),(3,132,3,6),(5,220,3,7),(1,222,4,6),(1,222,4,7);
/*!40000 ALTER TABLE `detalleventa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `perfil` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'ADMINISTRADOR'),(2,'SUPERVISOR'),(3,'USUARIO'),(4,'CONSULTA');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `destacado` int DEFAULT NULL,
  `detalles` varchar(255) DEFAULT NULL,
  `fecha_publicacion` datetime(6) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `categoria` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKydg56fw3q3f2lfcvtpkt36ls` (`categoria`),
  CONSTRAINT `FKydg56fw3q3f2lfcvtpkt36ls` FOREIGN KEY (`categoria`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Arroz con verduras',1,'Arroz con verduras','2025-04-08 00:00:00.000000','gohan.webp','Gohan',12.22,'Disponible',1),(2,'Un tazón de arroz del Kenshi',1,'Un tazón de arroz del Kenshi','2025-04-17 00:00:00.000000','arroz.webp','Tazón de arroz',12,'Disponible',1),(3,'Dulce',1,'Dulce','2025-04-03 00:00:00.000000','panacota.jpg','Pana Cotta',44,'Disponible',2),(4,'Maizoro',1,'asdasdasd','2025-05-07 00:00:00.000000','Yopemrro.jpg','Perrito Chaufa',222,'Disponible',1),(5,'asdfasdasd',0,'choich','2025-05-12 00:00:00.000000','6-removebg-preview.png','Queso',56,'Agotado',2);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productovendedor`
--

DROP TABLE IF EXISTS `productovendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productovendedor` (
  `fecha_venta` datetime(6) DEFAULT NULL,
  `id_producto` int NOT NULL,
  `id_vendedor` int NOT NULL,
  PRIMARY KEY (`id_producto`,`id_vendedor`),
  KEY `FKr6gfbac16108vthm8seftdo1f` (`id_vendedor`),
  CONSTRAINT `FKda3l6rfw3m63nf7v0jq2onfe` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`),
  CONSTRAINT `FKr6gfbac16108vthm8seftdo1f` FOREIGN KEY (`id_vendedor`) REFERENCES `vendedor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productovendedor`
--

LOCK TABLES `productovendedor` WRITE;
/*!40000 ALTER TABLE `productovendedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `productovendedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('cuco','{noop}cuco123',1),('darce','{noop}darce123',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `estatus` int DEFAULT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'marinabb@gmail.com',1,'2025-04-08 19:30:56.266000','Marina','{noop}123','marina123'),(2,'bambino7576@gmail.com',1,'2025-04-30 11:45:21.490000','Juana de Arce','123','jarce'),(3,'karin@gmail.com',1,'2025-05-07 00:00:00.000000','Karin','{noop}123','karin123'),(4,'daan@gmail.com',1,'2025-05-07 00:00:00.000000','Daan','{noop}123','daan123'),(5,'sam@gmail.com',1,'2025-05-07 00:00:00.000000','Samuel','{noop}123','samuel123'),(6,'dcxzvc@gmail.com',1,'2025-05-07 18:49:33.154000','Juan','123','juanjo'),(7,'carol@gmail.com',1,'2025-05-07 23:06:13.768000','Carol','$2a$10$LMMcKrej5SVsw7xxE8RnnO/dLp6n770L8Ky1FJIt6eJpkNnG3hN2O','carol123'),(8,'marco@gmail.com',1,'2025-05-07 23:21:30.318000','Markoth','$2a$10$jS63Jx8.q96wW7SUjWT9pufc1NfyNSEOLFEpMnRvshJDOk5/0iISe','marko123'),(9,'sarna@gmail.com',1,'2025-05-07 23:43:08.564000','Sarnarie','$2a$10$n7nDYEuaWpwcoNkB3oYtv.cFsZTKCQNZWT7sNG.98oLNRpTR5STRm','sarmarie'),(10,'darce4545@gmail.com',1,'2025-05-07 23:46:24.092000','D\'Arce','$2a$10$rX8ixoQt.gJBheAwvaJWzeihwUiuKkJnIO2g4JvV.8hXyPXEyPeC6','darcebb'),(11,'sona@gmail.com',1,'2025-05-07 23:48:12.069000','Sona','$2a$10$93cNRVqQbPYs2SHEnRjNp.80pg9kCxCZob2JJ.tA3vXZ56LJd.oaK','sona123'),(12,'sona@gmail.com',1,'2025-05-07 23:51:25.387000','Sona','$2a$10$0xI1Or4AlU4J/UEZ01VpjuuEby9Ap.dZxtRkYv28..qlyFs4xAe.C','sona2'),(13,'sona@gmail.com',1,'2025-05-07 23:53:47.223000','Sona','$2a$10$UG5w4DdDPolJG3CQSJW8FOCs16859ojnwGOCcJemySqe6buR5D5nW','sona3'),(14,'sona@gmail.com',1,'2025-05-08 00:00:11.028000','Sona','$2a$10$g5F2jcTM8Qpi..WDkSNT2eIR2GXKw2e3xGGcRUvFdCtO8EBeMkzZ.','sona5'),(15,'Ara@gmail.com',1,'2025-05-08 00:03:07.906000','Ariana Pequeña','$2a$10$f/fRwsyZv.sN1fR.pjEVgO75yz95bkmTWWk530sBoiT.atSfgxAOW','aria123'),(16,'Ara@gmail.com',1,'2025-05-08 00:20:36.821000','Carlos','$2a$10$WzjaW1L3jowB21hQLjHh7uSbXjq60xbSPrj2vHegqz5V1OYQee9dG','consulta1'),(17,'bambino7576@gmail.com',1,'2025-05-08 00:24:33.657000','Carlos supervisa','$2a$10$vmhYTwXbo3Oj27sS3fj8sOYG5xk.petHv1GTKBKbI1eGIfUusf0jW','supervisor1');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarioperfil`
--

DROP TABLE IF EXISTS `usuarioperfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarioperfil` (
  `id_usuario` int NOT NULL,
  `id_perfil` int NOT NULL,
  KEY `FK5yj99imivfu7kerdv33bek22j` (`id_perfil`),
  KEY `FKq1r0h8415xks6jugqpgivi4fb` (`id_usuario`),
  CONSTRAINT `FK5yj99imivfu7kerdv33bek22j` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id`),
  CONSTRAINT `FKq1r0h8415xks6jugqpgivi4fb` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarioperfil`
--

LOCK TABLES `usuarioperfil` WRITE;
/*!40000 ALTER TABLE `usuarioperfil` DISABLE KEYS */;
INSERT INTO `usuarioperfil` VALUES (1,4),(2,4),(3,1),(4,2),(5,4),(6,4),(7,4),(8,4),(9,1),(14,2),(15,1),(15,2),(16,4),(17,2);
/*!40000 ALTER TABLE `usuarioperfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendedor`
--

DROP TABLE IF EXISTS `vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendedor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre_completo` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendedor`
--

LOCK TABLES `vendedor` WRITE;
/*!40000 ALTER TABLE `vendedor` DISABLE KEYS */;
INSERT INTO `vendedor` VALUES (1,'Av, José Francisco Ruiz Massieu No. 12','Jeannete','7575678910'),(2,'Av, José Francisco Ruiz Massieu No. 12','Olivia t','7575678910'),(3,'por ahí','Yui Molina','7571731231');
/*!40000 ALTER TABLE `vendedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `folio` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `idcliente` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjrlg3vfoj5gfcwhh2apdgjuum` (`idcliente`),
  CONSTRAINT `FKjrlg3vfoj5gfcwhh2apdgjuum` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (1,'2025-04-11 00:00:00.000000','1231',158.64000000000001,1),(2,'2025-04-08 00:00:00.000000','123111',24.22,1),(3,'2025-05-07 00:00:00.000000','222',12,1),(4,'2025-05-10 00:00:00.000000','222211',36,1),(5,'2025-05-12 00:00:00.000000','1231111',48.88,2),(6,'2025-05-13 00:00:00.000000','99899',354,2),(7,'2025-05-14 00:00:00.000000','777712',442,2);
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-18 15:05:24
