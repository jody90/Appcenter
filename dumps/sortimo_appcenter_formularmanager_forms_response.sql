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
-- Table structure for table `formularmanager_forms_response`
--

DROP TABLE IF EXISTS `formularmanager_forms_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formularmanager_forms_response` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `form_id` int(11) NOT NULL,
  `value` longtext NOT NULL,
  `username` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `process_state` varchar(45) NOT NULL DEFAULT 'unfinished',
  `processed_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formularmanager_forms_response`
--

LOCK TABLES `formularmanager_forms_response` WRITE;
/*!40000 ALTER TABLE `formularmanager_forms_response` DISABLE KEYS */;
INSERT INTO `formularmanager_forms_response` VALUES (1,1,'{\"radio-group-1479720793151\":\"option-2\",\"select-1479733553038\":\"option-2\",\"text-1479733554734\":\"hallo\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-21 10:51:01','unfinished',NULL),(2,1,'{\"radio-group-1479720793151\":\"option-2\",\"select-1479733553038\":\"option-2\",\"text-1479733554734\":\"hallo\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-21 14:36:27','unfinished',NULL),(3,1,'{\"radio-group-1479720793151\":\"option-3\",\"select-1479733553038\":\"option-1\",\"text-1479733554734\":\"\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-21 16:43:00','unfinished',NULL),(4,1,'{\"radio-group-1479720793151\":\"option-2\",\"select-1479733553038\":\"option-2\",\"text-1479733554734\":\"\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-22 07:40:13','unfinished',NULL),(5,1,'{\"radio-group-1479720793151\":\"option-1\",\"select-1479733553038\":\"option-1\",\"text-1479733554734\":\"\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-22 11:24:20','unfinished',NULL),(6,1,'{\"checkbox-1479733689313\":\"on\",\"radio-group-1479720793151\":\"option-1\",\"select-1479733553038\":\"option-1\",\"text-1479733554734\":\"\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-22 11:26:45','unfinished',NULL),(7,1,'{\"radio-group-1479720793151\":\"option-1\",\"select-1479733553038\":\"option-1\",\"text-1479733554734\":\"\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-22 11:33:41','unfinished',NULL),(8,1,'{\"radio-group-1479720793151\":\"option-1\",\"select-1479733553038\":\"option-1\",\"text-1479733554734\":\"\",\"textarea-1479733555697\":\"\"}','anonymous','2016-11-22 11:35:38','unfinished',NULL),(9,5,'{\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-22 11:51:44','unfinished',NULL),(10,5,'{\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-22 11:51:59','unfinished',NULL),(11,5,'{\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-22 11:52:06','unfinished',NULL),(12,5,'{\"antwort-weihnachtsfeier\":\"nein\"}','anonymous','2016-11-22 11:52:14','unfinished',NULL),(13,5,'{\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-22 11:53:25','unfinished',NULL),(14,5,'{\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-22 14:12:46','unfinished',NULL),(15,5,'{\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-22 14:13:13','unfinished',NULL),(16,5,'{\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-22 14:20:11','unfinished',NULL),(17,5,'{\"antwort-weihnachtsfeier\":\"nein\"}','anonymous','2016-11-22 14:20:25','unfinished',NULL),(18,9,'{\"willhaben\":\"aaa\"}','jlerch','2016-11-30 14:10:53','unfinished','jlerch'),(19,9,'{\"willhaben\":\"bbb\"}','ttest','2016-11-30 14:11:53','unfinished',NULL),(20,5,'{\"waswillstduessen\":\"burgunder_braten\",\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-30 14:14:52','unfinished',NULL),(21,5,'{\"waswillstduessen\":\"rollbraten\",\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-30 14:14:55','unfinished',NULL),(22,5,'{\"waswillstduessen\":\"vegetarisch\",\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-30 14:14:58','unfinished',NULL),(23,5,'{\"waswillstduessen\":\"ich_bring_selber_was_mit\",\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-30 14:15:01','unfinished',NULL),(24,5,'{\"waswillstduessen\":\"burgunder_braten\",\"antwort-weihnachtsfeier\":\"vielleicht\"}','anonymous','2016-11-30 14:15:04','unfinished',NULL),(25,5,'{\"waswillstduessen\":\"vegetarisch\",\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-30 14:15:10','unfinished',NULL),(26,5,'{\"waswillstduessen\":\"rollbraten\",\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-30 14:15:15','unfinished',NULL),(27,5,'{\"waswillstduessen\":\"burgunder_braten\",\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-30 14:15:20','unfinished',NULL),(28,5,'{\"waswillstduessen\":\"burgunder_braten\",\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-30 14:15:22','unfinished',NULL),(29,5,'{\"waswillstduessen\":\"rollbraten\",\"antwort-weihnachtsfeier\":\"ja\"}','anonymous','2016-11-30 14:19:26','unfinished',NULL),(30,9,'{\"gscheit\":\"ddd\",\"willhaben\":\"ccc\"}','ttest','2016-11-30 16:01:44','finished','jlerch'),(31,9,'{\"gscheit\":\"Sofort bitte\",\"willhaben\":\"Ich will einen Keks\"}','testuser','2016-12-01 14:05:57','unfinished',NULL),(32,9,'{\"gscheit\":\"bitte\",\"willhaben\":\"noch einen keks\"}','testuser','2016-12-01 14:07:15','unfinished',NULL),(33,9,'{\"gscheit\":\"am besten Öttinger\",\"willhaben\":\"ich will bier\"}','testuser','2016-12-01 14:33:11','unfinished',NULL),(34,9,'{\"gscheit\":\"blub\",\"willhaben\":\"bla\"}','testuser','2016-12-02 08:30:13','unfinished',NULL),(35,9,'{\"gscheit\":\"blub\",\"willhaben\":\"bla\"}','testuser','2016-12-02 08:30:56','unfinished',NULL),(36,9,'{\"gscheit\":\"blub\",\"willhaben\":\"bla\"}','testuser','2016-12-02 08:31:05','unfinished',NULL),(37,9,'{\"gscheit\":\"dsaf\",\"willhaben\":\"asd\"}','testuser','2016-12-02 08:31:19','unfinished',NULL),(38,9,'{\"gscheit\":\"dsaf\",\"willhaben\":\"asd\"}','testuser','2016-12-02 08:32:25','unfinished',NULL),(39,9,'{\"gscheit\":\"aaaa\",\"willhaben\":\"aaaaa\"}','testuser','2016-12-02 08:36:14','unfinished',NULL),(40,9,'{\"gscheit\":\"aaaa\",\"willhaben\":\"aaaaa\"}','jlerch','2016-12-02 08:37:14','unfinished',NULL),(41,9,'{\"gscheit\":\"sadf\",\"willhaben\":\"fads\"}','jlerch','2016-12-02 08:37:20','unfinished',NULL),(42,9,'{\"gscheit\":\"sadf\",\"willhaben\":\"fads\"}','jlerch','2016-12-02 08:37:54','unfinished',NULL),(43,9,'{\"gscheit\":\"asdfasdf\",\"willhaben\":\"asdfasdf\"}','anonymous','2016-12-02 08:39:35','unfinished',NULL),(44,9,'{\"gscheit\":\"\",\"willhaben\":\"sadf\"}','testuser','2016-12-02 13:58:33','unfinished',NULL),(45,9,'{\"gscheit\":\"asdfsdafsdfadfsa\",\"willhaben\":\"adfadfa\"}','testuser','2016-12-05 07:59:24','unfinished',NULL),(46,9,'{\"checkbox-1480935265522\":\"hab_was_angehakt\",\"gscheit\":\"fffffffffffffffffff\",\"willhaben\":\"aaaaaaaaa\"}','testuser','2016-12-05 11:55:36','unfinished',NULL),(47,9,'{\"gscheit\":\"\",\"willhaben\":\"nix angehakt\"}','testuser','2016-12-05 11:58:55','unfinished',NULL),(48,9,'{\"checkbox-1480935265522\":\"hab_was_angehakt\",\"select-1480938960729\":\"option-2\",\"text-1480938981584\":\"test 2\",\"date-1480939003156\":\"2016-02-01\",\"checkbox-group-1480938993405[]\":\"option-1\",\"radio-group-1480938967599\":\"option-2\",\"gscheit\":\"steht\",\"willhaben\":\"hier\",\"textarea-1480939008124\":\"testomate 34\"}','testuser','2016-12-05 12:57:31','unfinished',NULL),(49,9,'{\"checkbox-1480935265522\":\"hab_was_angehakt\",\"select-1480938960729\":\"option-2\",\"text-1480938981584\":\"überall 2\",\"date-1480939003156\":\"2002-02-02\",\"checkbox-group-1480938993405[]\":\"option-2\",\"radio-group-1480938967599\":\"option-2\",\"gscheit\":\"überall 2\",\"willhaben\":\"überall 2\",\"textarea-1480939008124\":\"überall 2\"}','testuser','2016-12-05 13:02:08','unfinished',NULL),(50,9,'{\"select-1480938960729\":\"option-1\",\"text-1480938981584\":\"\",\"date-1480939003156\":\"\",\"checkbox-group-1480938993405[]\":\"option-2\",\"radio-group-1480938967599\":\"option-1\",\"gscheit\":\"\",\"willhaben\":\"asdfsdaf\",\"textarea-1480939008124\":\"\"}','testuser','2016-12-05 13:04:16','unfinished',NULL),(51,9,'{\"select-1480938960729\":\"option-1\",\"text-1480938981584\":\"\",\"date-1480939003156\":\"\",\"radio-group-1480938967599\":\"option-2\",\"gscheit\":\"\",\"willhaben\":\"dsfa\",\"textarea-1480939008124\":\"\"}','jlerch','2016-12-05 14:31:06','unfinished',NULL),(52,9,'{\"select-1480938960729\":\"option-1\",\"text-1480938981584\":\"\",\"date-1480939003156\":\"\",\"radio-group-1480938967599\":\"option-1\",\"gscheit\":\"\",\"willhaben\":\"dsafasdf\",\"textarea-1480939008124\":\"\"}','anonymous','2016-12-05 14:31:32','unfinished',NULL),(53,5,'{\"waswillstduessen\":\"ich_bring_selber_was_mit\",\"antwort-weihnachtsfeier\":\"ja\"}','jlerch','2016-12-05 15:01:05','unfinished',NULL),(54,5,'{\"waswillstduessen\":\"rollbraten\",\"antwort-weihnachtsfeier\":\"vielleicht\"}','jlerch','2016-12-08 13:01:10','unfinished',NULL);
/*!40000 ALTER TABLE `formularmanager_forms_response` ENABLE KEYS */;
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
