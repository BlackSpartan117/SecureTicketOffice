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
INSERT INTO `certificado` VALUES ('2022-06-04',1004,'TicketESCOM TicketESCOM TicketESCOM','2022-06-04','0Ç\"0\r	*ÜHÜ˜\r\0Ç\00Ç\nÇ\0ä\Á\≈\‚ßa]\Á†7\⁄¸öö˝he∑Voﬂ¶ﬁªt\ËΩ¸ƒöXHm¸≥õˆ?“∑ÛõS\Ë©Ãå\'–•9~≤®TU§∏îÕÜ9U\Z˛\ÔbÕ§kV·ΩúÉ!ZA\È?\»\⁄Jx^\‡h11Æ6ôL\r≤˚0ÒêZ˝©rg∫j∞)}mı,zT{\ŒR\⁄bJr\no}n˝7€øEô,ÕíRØ$≥úQzhèF\¬n˜PS\Îvvºì\„\\hqdäêÀ£Uo>w]éãÒ^w\Êˆáñ8¿ûR4\»\Ï\Õ$†6wlR\‡DT4ku®\⁄2°4Bö\ÊE≥4™n¿b\«\»	k”ó2_)ùˆxˆ<Ñc\0','πI,¡Yõü\‚•\‡jîf@(¯ÆìÙqKf\‘(âßYÜß/\Áù\"\Z	\—}ï˚]Vwqˆª˝•¨©ßÄhõl	Gµ\≈Ûﬁª\‹\Â†zæ\«UóãWõoAäÅ\“#p˝{¡›Ö©\Á%nWö[ÕàwüôLv}˚w\‘\\/#a\·˝\‘¸\Ï—ÇC¶!I\ÿ7\Ëˆ˚mùw0w\ﬁa≤˙¨ıˆ˛y,\Ã?Oëg%w:\≈<m$s\"SÙhØÿ∞Ùéâ\≈¸∫Ø#ah∑˝\'/A&ˆêˇ|\ﬂ\…U¨˘∑ò\ \‡ˆJc/\›¯ï-\√mD€±êèíÀ®Òj\–˘\¬ ≥∂kaN,Q^¡˘oÛ\‚Ä˙\’]=Ngø'),('2022-06-04',1005,'Omar Rodriguez Lopez','2022-06-04','0Ç\"0\r	*ÜHÜ˜\r\0Ç\00Ç\nÇ\0äı\ƒ¯]+p∑π*HÙ\\\Í\◊\’1∏n¶Åën\Ã\ÍGLB\“¸cI.\€\Á¡πŒà\ﬁg©úÛ1NvÙOy≥K\“%O\Ï\ËE\\U†ß˙\Á51m==,5\›\“]qnª!u®˜[¿1Fk£≈¥˝\0ñ¶[r`|πgù\Â—áÚ\ŸÒö\À8\”Za\–f=ì\'\‡Vmó@	^§∞o~\nF∑\—\…ph9H.rä++\ÀÜ…¨\ﬁ\«,ºg*)\∆{¶kÜ\≈fE\»\Í3Ä`.ı\≈“≠∂q˝©f\ \…I\È\\g˙Å.¢\ÏI/=&\‘\Z>ì\\≤t ®±\"åsN1ôë\◊e\ \›Âí∏;$m˘ò°&\0','t;s-£`\≈Ûë~Xf:%I&V˝rá§´Ui;@§\Ëı\Î≤\‚2Ò\Â5Xa:\›oª5\Í``´)\ÃK\ÎˆæîoÇ‘å>=˚´1,\‡\Œy{xI¯\∆∂E\Î˛tsAáP…°VXß/Ü\Ï4ïò?V…Ü*ú®âåV*£Gò\“Qº%ä.èã\–t\n†C\Ó[dê\—k\Ÿ&øíà\"z4AÄ\r\‰\07A\"öY[Nìz≠F\ÿ\…–Ä:]l.J›£(\Z\”6n\Â\Ÿ\…\rΩ>1Æˆ≤—¶ﬂÑH|†˝\ƒ\»Vù=\Z’ä/ûÄ±ù6b1F˜\ÔTá\◊@\÷d˛`g\“\Ÿdb\›¿6&t\…˛\Â≠|ñ\√Z>`\Z}');
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
