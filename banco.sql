-- MySQL dump 10.13  Distrib 5.7.18, for Win64 (x86_64)
--
-- Host: localhost    Database: banco
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `certificado`
--

DROP TABLE IF EXISTS `certificado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `certificado` (
  `cumple` date NOT NULL,
  `idCliente` int(11) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `expiracion` date NOT NULL,
  `publicKey` blob NOT NULL,
  `firmaBank` blob NOT NULL,
  PRIMARY KEY (`idCliente`),
  CONSTRAINT `certificado_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificado`
--

LOCK TABLES `certificado` WRITE;
/*!40000 ALTER TABLE `certificado` DISABLE KEYS */;
INSERT INTO `certificado` VALUES ('2022-06-04',1004,'TicketESCOM TicketESCOM TicketESCOM','2022-06-04','0�\"0\r	*�H��\r\0�\00�\n�\0�\�\�\�a]\�7\�����he�Voߦ޻t\��ĚXHm����?ҷ�S\�̌\'Х9~��TU���͆9U\Z�\�bͤkV὜�!ZA\�?\�\�Jx^\�h11��6�L\r��0�Z��rg�j�)}m�,zT{\�R\�bJr\no}n�7ۿE�,͒R�$��Qzh�F\�n�PS\�vv��\�\\hqd��ˣUo>w]���^w\����8��R4\�\�\�$�6wlR\�DT4ku�\�2�4B�\�E�4�n�b\�\�	kӗ2_)��x�<�c\0','�I,�Y��\�\�j�f@(����qKf\�(��Y��/\�\"\Z	\�}��]Vwq��������h�l	G�\��޻\�\�z�\�U��W�oA��\�#p��{�݅�\�%nW�[͈w��Lv}�w\�\\/#a\��\��\�тC�!I\�7\���m�w0w\��a������y,\�?O�g%w:\�<m$s\"S�h�ذ�\����#ah��\'/A&���|\�\�U����\�\��Jc/\����-\�mD۱���˨�j\��\� ��kaN,Q^��o�\��\�]=Ng�'),('2022-06-04',1005,'Omar Rodriguez Lopez','2022-06-04','0�\"0\r	*�H��\r\0�\00�\n�\0��\��]+p��*H�\\\�\�\�1�n���n\�\�GLB\��cI.\�\���Έ\�g���1Nv�Oy�K\�%O\�\�E\\U���\�51m==,5\�\�]qn�!u��[�1Fk�Ŵ�\0��[r`|�g�\�ч�\��\�8\�Za\�f=�\'\�Vm�@	^��o~\nF�\�\�ph9H.r�++\��ɬ\�\�,�g*)\�{�k�\�fE\�\�3�`.�\�ҭ�q��f\�\�I\�\\g��.�\�I/=&\�\Z>�\\�tʨ�\"�sN1��\�e\�\�咸;$m���&\0','t;s-�`\���~Xf:%I&V�r���Ui;@�\��\�\�2�\�5Xa:\�o�5\�``�)\�K\����o�Ԍ>=��1,\�\�y{xI�\��E\��tsA�PɡVX�/�\�4��?VɆ*����V*�G�\�Q�%�.��\�t\n�C\�[d�\�k\�&���\"z4A�\r\�\07A\"�Y[N�z�F\�\�Ѐ:]l.Jݣ(\Z\�6n\�\�\�\r�>1���Ѧ߄H|��\�\�V�=\ZՊ/����6b1F�\�T�\�@\�d�`g\�\�db\��6&t\��\�|�\�Z>`\Z}');
/*!40000 ALTER TABLE `certificado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuenta` (
  `nombre` varchar(20) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apellido` varchar(50) NOT NULL,
  `noTarjetaCredito` varchar(20) NOT NULL,
  `cvv` varchar(3) NOT NULL,
  `saldo` double NOT NULL,
  `vigencia` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES ('TicketESCOM',1004,'TicketESCOM TicketESCOM','1234567890123458','669',2309,'2022-06-04'),('Omar',1005,'Rodriguez Lopez','1234567890123459','670',7791,'2022-06-04');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-05  4:02:49
