# ************************************************************
# Sequel Pro SQL dump
# Version 4004
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.1.60-log)
# Database: leap4net
# Generation Time: 2013-02-05 05:03:30 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Invitation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Invitation`;

CREATE TABLE `Invitation` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `deadLine` datetime DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBE1153B992D8654C` (`owner`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table leap_invitation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `leap_invitation`;

CREATE TABLE `leap_invitation` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `deadLine` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4A8CFCD092D8654C` (`owner`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table leap_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `leap_order`;

CREATE TABLE `leap_order` (
  `id` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB7755BB792D8654C` (`owner`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table leap_refund
# ------------------------------------------------------------

DROP TABLE IF EXISTS `leap_refund`;

CREATE TABLE `leap_refund` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3B9E982FCF334BD1` (`target`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table leap_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `leap_role`;

CREATE TABLE `leap_role` (
  `id` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table leap_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `leap_user`;

CREATE TABLE `leap_user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `activate` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK588266428CB887BA` (`role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table Role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Role`;

CREATE TABLE `Role` (
  `id` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table sidways_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sidways_order`;

CREATE TABLE `sidways_order` (
  `id` varchar(255) NOT NULL,
  `oid` varchar(255) DEFAULT NULL,
  `pid` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `prepare` bit(1) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table test
# ------------------------------------------------------------

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table trueq
# ------------------------------------------------------------

DROP TABLE IF EXISTS `trueq`;

CREATE TABLE `trueq` (
  `a` char(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table User
# ------------------------------------------------------------

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` varchar(255) NOT NULL,
  `created` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK285FEB8CB887BA` (`role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
