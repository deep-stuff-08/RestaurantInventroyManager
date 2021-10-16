-- MariaDB dump 10.19  Distrib 10.6.4-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: InvMan
-- ------------------------------------------------------
-- Server version	10.6.4-MariaDB

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
-- Table structure for table `tbl_quantity`
--

DROP TABLE IF EXISTS `tbl_quantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_quantity` (
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_quantity`
--

LOCK TABLES `tbl_quantity` WRITE;
/*!40000 ALTER TABLE `tbl_quantity` DISABLE KEYS */;
INSERT INTO `tbl_quantity` VALUES ('Bell Peppers',200),('Bread Bases',6),('Cheese',700),('Corn',20),('Flour',100),('Garlic Butter',10),('Milk',100),('Mushroom',0),('Onions',300),('Panner',100),('Pasta',800),('Pizza Bases',9),('Potatoes',150),('Tomato Puree',1000),('Tomatoes',200);
/*!40000 ALTER TABLE `tbl_quantity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_recipe`
--

DROP TABLE IF EXISTS `tbl_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_recipe` (
  `rec_id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rec_recipe` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_recipe`
--

LOCK TABLES `tbl_recipe` WRITE;
/*!40000 ALTER TABLE `tbl_recipe` DISABLE KEYS */;
INSERT INTO `tbl_recipe` VALUES (1,'Margerita Pizza','Cheese-100|Pizza Bases-1|Tomato Puree-100'),(2,'Veggie Pizza','Cheese-100|Pizza Bases-1|Tomato Puree-100|Panner-40|Bell Peppers-20|Onions-20'),(3,'Double Cheese Pizza','Cheese-200|Pizza Bases-1|Tomato Puree-100'),(4,'Garlic Bread','Bread Bases-4|Garlic Butter-30|Cheese-100'),(5,'Arrabita Pasta','Pasta-200|Bell Peppers-50|Tomatoes-50|Tomato Puree-100'),(6,'Alfredo Pasta','Cheese-100|Corn-10|Pasta-200|Mushroom-50|Milk-100'),(7,'Onion Rings','Flour-50|Onions-50|Tomato Puree-30'),(8,'Potato Wedges','Potatoes-150|Tomato Puree-30'),(9,'Panner Spicy Pizza','Cheese-100|Pizza Bases-1|Tomato Puree-100|Panner-40|Bell Peppers-20|Onions-20');
/*!40000 ALTER TABLE `tbl_recipe` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-30 23:52:15
