use aqarsite;

create table UserAccount (
	ID int primary key,
    userName text not null,
    userPassword text not null,
    fullName text,
    email text not null,
    picture blob,
    phone text
);

create table Advertisement(
	ID int primary key,
    size int,
    title text not null,
    Description text,
    floor int,
    propStatus text,
    propType text,
    adType int,
    accountID int not null,
    foreign key(accountID) references UserAccount(ID)
);

<<<<<<< HEAD
create table AdPhoto(
	ID int primary key,
    adID int not null,
    photo blob not null,
    foreign key(adID) references Advertisement(ID)
);
=======
DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `ID` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `userpassword` varchar(100) NOT NULL,
  `image` blob,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement` (
  `ID` int(11) NOT NULL,
  `Size` int(11) DEFAULT NULL,
  `description` text,
  `floor` int(11) DEFAULT NULL,
  `propstatus` varchar(100) DEFAULT NULL,
  `proptype` varchar(100) DEFAULT NULL,
  `image` blob,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `userID` (`userID`),
  CONSTRAINT `advertisement_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `useraccount` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
>>>>>>> 74e65a524c47fa89172b7513a01fe1b117edd90f

create table AdComment(
	ID int primary key,
    accountID int not null,
    adID int not null,
    AdComment text not null,
    foreign key(adID) references Advertisement(ID),
    foreign key(accountID) references UserAccount(ID)
);

create table Rating(
	adID int not null,
    accountID int not null,
    rateValue int not null,
    foreign key(adID) references Advertisement(ID),
    foreign key(accountID) references UserAccount(ID),
    primary key(adID,accountID)
);

create table Preference(
	accountID  int primary key,
    size int,
    floor int,
    propStatus text,
    propType text,
    foreign key(accountID) references UserAccount(ID)
);

<<<<<<< HEAD
create table Notification(
	ID  int primary key,
    accountID int not null,
    adID int,
    content text not null,
    foreign key(adID) references Advertisement(ID),
    foreign key(accountID) references UserAccount(ID)
);
=======

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (1,'hassan','','',NULL),(2,'haitham','haitham@gmail.com','private',NULL);
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-01  1:15:26
>>>>>>> 74e65a524c47fa89172b7513a01fe1b117edd90f
