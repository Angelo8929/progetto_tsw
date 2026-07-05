-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: progetto_tsw
-- ------------------------------------------------------
-- Server version	9.7.1

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '0e7fd214-74a1-11f1-9eae-6083e7cbc9fb:1-245,
b5d16312-723f-11f1-bc69-2c6dc1d457b5:1-91';

--
-- Table structure for table `carrello`
--

DROP TABLE IF EXISTS `carrello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrello` (
  `id_carrello` int NOT NULL AUTO_INCREMENT,
  `email_utente` varchar(45) NOT NULL,
  PRIMARY KEY (`id_carrello`),
  KEY `id_utente_idx` (`email_utente`),
  CONSTRAINT `id_utente` FOREIGN KEY (`email_utente`) REFERENCES `utente` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrello`
--

LOCK TABLES `carrello` WRITE;
/*!40000 ALTER TABLE `carrello` DISABLE KEYS */;
INSERT INTO `carrello` VALUES (1,'angeloverolla8@gmail.com');
/*!40000 ALTER TABLE `carrello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_consegna`
--

DROP TABLE IF EXISTS `info_consegna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_consegna` (
  `id_consegna` int NOT NULL AUTO_INCREMENT,
  `via` varchar(45) NOT NULL,
  `civico` varchar(45) NOT NULL,
  `citta` varchar(45) NOT NULL,
  `destinatario` varchar(45) NOT NULL,
  `id_utente` varchar(45) NOT NULL,
  PRIMARY KEY (`id_consegna`),
  KEY `id_utente_consegna_idx` (`id_utente`),
  CONSTRAINT `id_utente_consegna` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_consegna`
--

LOCK TABLES `info_consegna` WRITE;
/*!40000 ALTER TABLE `info_consegna` DISABLE KEYS */;
INSERT INTO `info_consegna` VALUES (1,'via delle vie','12','milano','boh','angeloverolla8@gmail.com'),(2,'via mariscoli','96','fisciano','angelo verolla','angeloverolla8@gmail.com'),(4,'ssss','12','ss','ssssssss','angeloverolla8@gmail.com'),(7,'gghh','234','weds','fffff','angeloverolla8@gmail.com'),(8,'ffff','22','wsa','dddele','angeloverolla8@gmail.com');
/*!40000 ALTER TABLE `info_consegna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordine` (
  `id_ordine` int NOT NULL AUTO_INCREMENT,
  `data_ordine` date NOT NULL,
  `costo_totale` decimal(10,2) NOT NULL,
  `num_prodotti` int NOT NULL,
  `email_utente` varchar(45) NOT NULL,
  `id_consegna` int NOT NULL,
  PRIMARY KEY (`id_ordine`),
  KEY `email_utente_idx` (`email_utente`),
  KEY `id_consegna_ordine_idx` (`id_consegna`),
  CONSTRAINT `email_utente` FOREIGN KEY (`email_utente`) REFERENCES `utente` (`email`),
  CONSTRAINT `id_consegna_ordine` FOREIGN KEY (`id_consegna`) REFERENCES `info_consegna` (`id_consegna`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` VALUES (1,'2026-06-18',35.70,4,'angeloverolla8@gmail.com',1),(2,'2026-06-19',70.00,1,'angeloverolla8@gmail.com',1),(3,'2026-06-19',7.40,3,'angeloverolla8@gmail.com',1),(4,'2026-06-20',68.00,2,'angeloverolla8@gmail.com',1),(5,'2026-06-20',208.00,2,'angeloverolla8@gmail.com',1),(6,'2026-06-20',80.00,1,'angeloverolla8@gmail.com',1),(7,'2026-06-21',124.50,3,'angeloverolla8@gmail.com',2),(10,'2026-06-23',287.38,3,'angeloverolla8@gmail.com',2),(11,'2026-06-28',14.70,4,'angeloverolla8@gmail.com',4),(12,'2026-06-28',25.00,1,'angeloverolla8@gmail.com',7),(13,'2026-06-28',104.00,1,'angeloverolla8@gmail.com',8),(14,'2026-06-28',41.48,1,'angeloverolla8@gmail.com',8),(15,'2026-07-01',23.46,1,'angeloverolla8@gmail.com',8),(16,'2026-07-01',87.36,1,'angeloverolla8@gmail.com',7),(17,'2026-07-02',125.84,1,'angeloverolla8@gmail.com',2),(18,'2026-07-02',41.48,1,'angeloverolla8@gmail.com',4),(19,'2026-07-04',41.15,2,'angeloverolla8@gmail.com',4),(20,'2026-07-05',21.08,1,'angeloverolla8@gmail.com',1),(21,'2026-07-05',30.50,1,'angeloverolla8@gmail.com',2),(22,'2026-07-05',23.00,1,'angeloverolla8@gmail.com',2),(23,'2026-07-05',13.00,1,'angeloverolla8@gmail.com',2),(24,'2026-07-05',77.00,1,'angeloverolla8@gmail.com',2),(25,'2026-07-05',6.00,1,'angeloverolla8@gmail.com',2),(26,'2026-07-05',14.00,1,'angeloverolla8@gmail.com',2),(27,'2026-07-05',15.00,1,'angeloverolla8@gmail.com',2),(28,'2026-07-05',111.65,1,'angeloverolla8@gmail.com',2);
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto` (
  `id_prodotto` int NOT NULL AUTO_INCREMENT,
  `perc_alcol` decimal(10,2) DEFAULT NULL,
  `nome_prodotto` varchar(45) NOT NULL,
  `effervescenza` varchar(45) DEFAULT NULL,
  `prezzo` decimal(10,2) NOT NULL,
  `categoria` varchar(45) NOT NULL,
  `imgPath` varchar(45) DEFAULT NULL,
  `descrizione` longtext,
  `iva` tinyint DEFAULT NULL,
  `disponibilita` int NOT NULL DEFAULT '50',
  PRIMARY KEY (`id_prodotto`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES (1,0.00,'Acqua uliveto','naturale',17.40,'Analcolici','san_benedetto.jpg','frate è la san benedetto',25,78),(2,14.00,'Chianti Classico',NULL,14.50,'alcolici','chianti.jpg',NULL,22,47),(3,7.00,'Birra Artigianale IPA',NULL,6.00,'alcolici','birra_ipa.png',NULL,22,47),(4,30.00,'Amaro alle Erbe','',20.00,'Alcolici','amaro_erbe.jpg','',22,48),(5,7.00,'birra chill','',34.00,'Alcolici','birra_chill.jpg','',22,48),(6,0.00,'paolo bonolis','',104.00,'Analcolici','paolo.jpg','AVANTI UN ALTRO',21,47),(7,0.00,'monster gusto tachipirina','',13.00,'Analcolici','monster.jpg','giuro, ha quel sapore lì',22,10),(8,0.00,'aaaa','',78.00,'Superalcolici','aaaa.png','si',12,54),(9,0.00,'massimo korovskyy','',77.00,'Alcolici','massimo.jpg','il mio compagno di progetto',45,0),(10,70.00,'latte di suocera','',25.00,'Superalcolici','latte_suocera.jpg','bono',22,0),(11,76.00,'ezio greggio','',23.00,'Superalcolici','ezio.jpg','sono loro o non sono loro?',2,46);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto_carrello`
--

DROP TABLE IF EXISTS `prodotto_carrello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto_carrello` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_prodotto` int NOT NULL,
  `id_carrello` int NOT NULL,
  `quantita` int NOT NULL,
  `imgPath` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_prodotto_carrello_idx` (`id_prodotto`),
  KEY `id_carrello_carrello_idx` (`id_carrello`),
  CONSTRAINT `id_carrello_carrello` FOREIGN KEY (`id_carrello`) REFERENCES `carrello` (`id_carrello`),
  CONSTRAINT `id_prodotto_carrello` FOREIGN KEY (`id_prodotto`) REFERENCES `prodotto` (`id_prodotto`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto_carrello`
--

LOCK TABLES `prodotto_carrello` WRITE;
/*!40000 ALTER TABLE `prodotto_carrello` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto_carrello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto_ordine`
--

DROP TABLE IF EXISTS `prodotto_ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto_ordine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_prodotto` varchar(45) NOT NULL,
  `id_ordine` int NOT NULL,
  `id_prodotto` int NOT NULL,
  `prezzo` decimal(10,2) NOT NULL,
  `quantita` int NOT NULL,
  `iva` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_prodotto_idx` (`id_prodotto`),
  KEY `id_prodotto_ordine_idx` (`id_prodotto`),
  KEY `id_ordine_ordine_idx` (`id_ordine`),
  CONSTRAINT `id_ordine_ordine` FOREIGN KEY (`id_ordine`) REFERENCES `ordine` (`id_ordine`),
  CONSTRAINT `id_prodotto_ordine` FOREIGN KEY (`id_prodotto`) REFERENCES `prodotto` (`id_prodotto`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto_ordine`
--

LOCK TABLES `prodotto_ordine` WRITE;
/*!40000 ALTER TABLE `prodotto_ordine` DISABLE KEYS */;
INSERT INTO `prodotto_ordine` VALUES (1,'Chianti Classico',1,2,15.00,2,22),(2,'Acqua san benedetto',1,1,1.00,1,22),(3,'Birra Artigianale IPA',1,3,6.00,1,22),(4,'Acqua san benedetto',2,1,1.00,1,22),(5,'Acqua san benedetto',3,1,1.00,2,22),(6,'Birra Artigianale IPA',3,3,6.00,1,22),(7,'Birra Artigianale IPA',4,3,6.00,1,22),(8,'Acqua uliveto',4,1,0.80,1,22),(9,'paolo bonolis',5,6,104.00,2,22),(10,'Acqua uliveto',6,1,0.80,1,22),(11,'Acqua uliveto',7,1,104.00,1,24),(12,'Birra Artigianale IPA',7,3,6.00,1,22),(13,'Chianti Classico',7,2,14.50,1,22),(17,'massimo korovskyy',10,9,104.69,2,45),(18,'aaaa',10,8,78.00,1,12),(19,'Amaro alle Erbe',11,4,20.00,1,22),(20,'Acqua uliveto',11,1,17.00,1,24),(21,'Birra Artigianale IPA',11,3,6.00,1,22),(22,'paolo bonolis',11,6,104.00,1,21),(23,'latte di suocera',12,10,25.00,1,22),(24,'paolo bonolis',13,6,104.00,1,21),(25,'birra chill',14,5,34.00,1,22),(26,'ezio greggio',15,11,23.00,1,2),(27,'aaaa',16,8,78.00,1,12),(28,'paolo bonolis',17,6,104.00,1,21),(29,'birra chill',18,5,34.00,1,22),(30,'ezio greggio',19,11,23.00,1,2),(31,'Chianti Classico',19,2,14.50,1,22),(32,'Acqua uliveto',20,1,17.00,1,24),(33,'latte di suocera',21,10,25.00,1,22),(34,'ezio greggio',22,11,23.00,1,2),(35,'monster gusto tachipirina',23,7,13.00,1,22),(36,'massimo korovskyy',24,9,77.00,1,45),(37,'Birra Artigianale IPA',25,3,6.00,1,22),(38,'Chianti Classico',26,2,14.50,1,22),(39,'monster gusto tachipirina',27,7,13.00,1,22),(40,'massimo korovskyy',28,9,77.00,1,45);
/*!40000 ALTER TABLE `prodotto_ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `email` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('aaaaa@a.com','angelo345','dOM7gtnnRI4TxHAEalVgOqk3ygshz3us1Vb5CYnfJf8=',0),('angeloverolla8@gmail.com','angelo','lgbZ65ehjuIIeyxzYundTb1SSa1rGc68TuNSYSKi934=',1);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'progetto_tsw'
--
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-05 18:04:21
