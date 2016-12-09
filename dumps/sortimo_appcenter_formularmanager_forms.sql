-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: sortimo_appcenter
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `formularmanager_forms`
--

DROP TABLE IF EXISTS `formularmanager_forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formularmanager_forms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(60) NOT NULL,
  `country` varchar(10) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formularmanager_forms`
--

LOCK TABLES `formularmanager_forms` WRITE;
/*!40000 ALTER TABLE `formularmanager_forms` DISABLE KEYS */;
INSERT INTO `formularmanager_forms` VALUES (1,'umfrage','DE','2016-11-21 10:33:14','2016-11-22 11:26:31',0),(2,'umfrage','DE','2016-11-21 13:24:03','2016-11-21 13:27:59',0),(3,'umfrage','DE','2016-11-21 13:35:05','2016-11-22 11:48:00',1),(4,'umfrage','DE','2016-11-21 13:38:31','2016-11-21 13:38:31',0),(5,'umfrage','DE','2016-11-22 11:51:36','2016-11-30 14:14:44',0),(6,'umfrage','DE','2016-11-22 14:03:06','2016-11-22 14:08:09',1),(7,'umfrage','DE','2016-11-22 14:09:40','2016-11-22 14:09:58',1),(8,'umfrage','GB','2016-11-23 07:31:31','2016-11-23 07:53:37',0),(9,'antrag','DE','2016-11-30 14:00:18','2016-12-05 13:04:04',0),(10,'umfrage','DE','2016-12-08 13:00:29','2016-12-08 13:18:31',1);
/*!40000 ALTER TABLE `formularmanager_forms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-09 14:46:01
