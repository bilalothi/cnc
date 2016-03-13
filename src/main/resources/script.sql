-- MySQL dump 10.13  Distrib 5.6.19, for Win32 (x86)
--
-- Host: localhost    Database: c2i_db
-- ------------------------------------------------------
-- Server version	5.6.22-enterprise-commercial-advanced-log

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
-- Table structure for table `Alert`
--

DROP TABLE IF EXISTS `Alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Alert` (
  `Alert_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Alert_Type` varchar(20) DEFAULT NULL,
  `Alert_State` varchar(20) DEFAULT NULL,
  `Assigned_To` varchar(45) DEFAULT NULL,
  `Device_Id` varchar(45) DEFAULT NULL,
  `Alert_Received_Timestamp` datetime DEFAULT NULL,
  `Alert_Accepted_Timestamp` datetime DEFAULT NULL,
  `Alert_Assigned_Timestamp` datetime DEFAULT NULL,
  `Alert_Resolved_Timestamp` datetime DEFAULT NULL,
  `Assign_Comments` varchar(500) DEFAULT NULL,
  `Assigned_By` varchar(45) DEFAULT NULL,
  `Resolve_Comments` varchar(500) DEFAULT NULL,
  `Flag` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Alert_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5811 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Alert`
--

--
-- Table structure for table `Device_Info`
--

DROP TABLE IF EXISTS `Device_Info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Device_Info` (
  `Device_Id` varchar(45) NOT NULL,
  `Device_Type` varchar(20) NOT NULL,
  `Device_Type_Char` char(1) DEFAULT NULL,
  `Device_Status` varchar(15) NOT NULL,
  `Device_MAC_Address` varchar(45) DEFAULT NULL,
  `Network_Id` varchar(45) NOT NULL,
  `Battery` int(3) DEFAULT NULL,
  `Sensitivity` int(3) DEFAULT NULL,
  `Link_Quality` int(3) DEFAULT NULL,
  `Primary_Router_Id` varchar(45) DEFAULT NULL,
  `Secondry_Router_Id` varchar(45) DEFAULT NULL,
  `Signal_Strength` int(3) DEFAULT NULL,
  `Video_Stream_Link` varchar(200) DEFAULT NULL,
  `Last_Alert_Time` timestamp NULL DEFAULT NULL,
  `Explodable` bit(1) DEFAULT b'0',
  PRIMARY KEY (`Device_Id`),
  KEY `fk_Device_Info_1_idx` (`Network_Id`),
  CONSTRAINT `fk_device_network_id` FOREIGN KEY (`Network_Id`) REFERENCES `Field_Network` (`Field_Network_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Device_Info`
--

LOCK TABLES `Device_Info` WRITE;
/*!40000 ALTER TABLE `Device_Info` DISABLE KEYS */;
INSERT INTO `Device_Info` VALUES ('0','C2I_SYSTEM','C','ON','','11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0');
/*!40000 ALTER TABLE `Device_Info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Device_Location`
--

DROP TABLE IF EXISTS `Device_Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Device_Location` (
  `Device_Id` varchar(45) NOT NULL,
  `Latitude` double NOT NULL,
  `Longitude` double NOT NULL,
  `Altitude` double NOT NULL,
  PRIMARY KEY (`Device_Id`),
  KEY `fk_location_device_id_idx` (`Device_Id`),
  KEY `fk_location_device_id_idx1` (`Device_Id`),
  CONSTRAINT `fk_location_device_id` FOREIGN KEY (`Device_Id`) REFERENCES `Device_Info` (`Device_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Device_Location`
--

LOCK TABLES `Device_Location` WRITE;
/*!40000 ALTER TABLE `Device_Location` DISABLE KEYS */;
INSERT INTO `Device_Location` VALUES ('0',33.57734,73.06467,544.6);
/*!40000 ALTER TABLE `Device_Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Field_Network`
--

DROP TABLE IF EXISTS `Field_Network`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Field_Network` (
  `Field_Network_Id` varchar(45) NOT NULL,
  `Field_Network_Name` varchar(40) DEFAULT NULL,
  `Field_Network_Type` varchar(15) DEFAULT NULL,
  `Field_Network_Status` varchar(15) NOT NULL,
  PRIMARY KEY (`Field_Network_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Field_Network`
--

LOCK TABLES `Field_Network` WRITE;
/*!40000 ALTER TABLE `Field_Network` DISABLE KEYS */;
INSERT INTO `Field_Network` VALUES ('11','UGS Network','Sensor Network','');
/*!40000 ALTER TABLE `Field_Network` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Message_Log`
--

DROP TABLE IF EXISTS `Message_Log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message_Log` (
  `Message_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Message_Content` varchar(250) DEFAULT NULL,
  `Message_Type` varchar(15) NOT NULL,
  `Message_Timestamp` datetime NOT NULL,
  `Device_Id` varchar(45) NOT NULL,
  `Network_Id` varchar(45) NOT NULL,
  `Device_Type` varchar(45) NOT NULL,
  `Message_Raw_Data` varchar(45) NOT NULL,
  `Device_Mac` varchar(45) DEFAULT NULL,
  `Battery_Status` varchar(45) DEFAULT NULL,
  `Inactive_Devices` varchar(250) DEFAULT NULL,
  `Explode` bit(1) DEFAULT b'0',
  PRIMARY KEY (`Message_Id`),
  KEY `fk_device_id_idx` (`Device_Id`),
  KEY `fk_message_network_id_idx` (`Network_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=17282 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `Message_Type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message_Type` (
  `Key` int(11) NOT NULL,
  `Type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message_Type`
--

LOCK TABLES `Message_Type` WRITE;
/*!40000 ALTER TABLE `Message_Type` DISABLE KEYS */;
INSERT INTO `Message_Type` VALUES (0,'ON'),(1,'LOCATION'),(2,'PRIMARY JOIN REQUEST'),(3,'BATTERY'),(4,'WAKE UP'),(5,'ROUTER NOT FOUND'),(6,'SECONDRY JOIN REQUEST'),(7,'SENSITIVITY'),(8,'SLEEP'),(9,'AUTH FAIL'),(10,'ALERT'),(11,'FAULT'),(12,'NETWORK MISMATCH'),(13,'ROUTER SWITCHED'),(14,'DRIFT DETECTED'),(15,'LINK QUALITY'),(16,'SIGNAL STRENGTH');
/*!40000 ALTER TABLE `Message_Type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PIR_Sensor`
--

DROP TABLE IF EXISTS `PIR_Sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PIR_Sensor` (
  `PIR_Sensor_Id` varchar(45) NOT NULL,
  `PIR_Sensor_Direction` varchar(8) DEFAULT NULL,
  `PIR_Sensor_Sensitivity` int(5) DEFAULT NULL,
  `PIR_Sensor_Node_Id` varchar(45) NOT NULL,
  PRIMARY KEY (`PIR_Sensor_Id`),
  KEY `fk_pir_sensor_node_id_idx` (`PIR_Sensor_Node_Id`),
  CONSTRAINT `fk_pir_sensor_node_id` FOREIGN KEY (`PIR_Sensor_Node_Id`) REFERENCES `Sensor_Node` (`Sensor_Node_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PIR_Sensor`
--

LOCK TABLES `PIR_Sensor` WRITE;
/*!40000 ALTER TABLE `PIR_Sensor` DISABLE KEYS */;
/*!40000 ALTER TABLE `PIR_Sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Router_Node`
--

DROP TABLE IF EXISTS `Router_Node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Router_Node` (
  `Router_Node_Id` varchar(45) NOT NULL,
  `Network_Id` varchar(45) NOT NULL,
  PRIMARY KEY (`Router_Node_Id`),
  KEY `fk_router_network_id_idx` (`Network_Id`),
  CONSTRAINT `fk_router_network_id` FOREIGN KEY (`Network_Id`) REFERENCES `Field_Network` (`Field_Network_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Router_Node`
--

LOCK TABLES `Router_Node` WRITE;
/*!40000 ALTER TABLE `Router_Node` DISABLE KEYS */;
/*!40000 ALTER TABLE `Router_Node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sensor_Node`
--

DROP TABLE IF EXISTS `Sensor_Node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sensor_Node` (
  `Sensor_Node_Id` varchar(45) NOT NULL,
  `Sensor_Node_Type` varchar(15) NOT NULL,
  `Sensor_Node_Router_Id` varchar(45) NOT NULL,
  PRIMARY KEY (`Sensor_Node_Id`),
  KEY `fk_Sensor_Node_1_idx` (`Sensor_Node_Router_Id`),
  CONSTRAINT `fk_sensor_node_router_id` FOREIGN KEY (`Sensor_Node_Router_Id`) REFERENCES `Router_Node` (`Router_Node_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sensor_Node`
--

LOCK TABLES `Sensor_Node` WRITE;
/*!40000 ALTER TABLE `Sensor_Node` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sensor_Node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `User_Id` varchar(45) NOT NULL,
  `First_Name` varchar(20) NOT NULL,
  `Last_Name` varchar(20) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `User_Status` varchar(12) NOT NULL,
  `User_Role` varchar(20) NOT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('ugs','Bilal','Hassan','ugs','ACTIVE','COMMANDER');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_Log`
--

DROP TABLE IF EXISTS `User_Log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User_Log` (
  `User_Log_Id` int(11) NOT NULL AUTO_INCREMENT,
  `User_Log_Message_Type` varchar(10) NOT NULL,
  `User_Log_Message` varchar(120) NOT NULL,
  `User_Log_Message_Timestamp` datetime NOT NULL,
  `User_Id` varchar(45) NOT NULL,
  PRIMARY KEY (`User_Log_Id`),
  KEY `fk_log_user_id_idx` (`User_Id`),
  CONSTRAINT `fk_log_user_id` FOREIGN KEY (`User_Id`) REFERENCES `User` (`User_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_Log`
--

LOCK TABLES `User_Log` WRITE;
/*!40000 ALTER TABLE `User_Log` DISABLE KEYS */;
/*!40000 ALTER TABLE `User_Log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Video_Camera_Node`
--

DROP TABLE IF EXISTS `Video_Camera_Node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Video_Camera_Node` (
  `Video_Camera_Node_Id` varchar(45) NOT NULL,
  `Video_Resolution` varchar(10) DEFAULT NULL,
  `Video_Camera_Network_Id` varchar(45) NOT NULL,
  `Video_Stream_Link` varchar(200) NOT NULL,
  PRIMARY KEY (`Video_Camera_Node_Id`),
  KEY `fk_video_camera_network_id_idx` (`Video_Camera_Network_Id`),
  KEY `fk_video_camera_node_id_idx` (`Video_Camera_Node_Id`),
  CONSTRAINT `fk_video_camera_network_id` FOREIGN KEY (`Video_Camera_Network_Id`) REFERENCES `Field_Network` (`Field_Network_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_video_camera_node_id` FOREIGN KEY (`Video_Camera_Node_Id`) REFERENCES `Device_Info` (`Device_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping routines for database 'c2i_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 11:21:15
