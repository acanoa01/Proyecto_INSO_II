/*
*SCRIPT PARA CREAR BBDD QUE UTILIZARA LA APLICACION KIDHUB
*/
DROP DATABASE planazzo;
CREATE DATABASE IF NOT EXISTS planazzo;

use planazzo;

/*
*Primero borra todas las tablas por si existieran en la bbdd
*/
SET FOREIGN_KEY_CHECKS = 0;
SET GROUP_CONCAT_MAX_LEN=32768;
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
  FROM information_schema.tables
  WHERE table_schema = (SELECT DATABASE());
SELECT IFNULL(@tables,'dummy') INTO @tables;

SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;

/*
*Creamos todas las tablas y sus columnas
*/

CREATE TABLE IF NOT EXISTS `Rols` (
  `RolID` int(11) NOT NULL AUTO_INCREMENT,
  `UserType` char(1) NOT NULL,
  `Description` varchar(100) NOT NULL,
  constraint `PK_Rols` PRIMARY KEY (`RolID`)
) ;

DELETE FROM `Rols`;
/*!40000 ALTER TABLE `Rols` DISABLE KEYS */;
INSERT INTO `Rols` (`RolID`, `UserType`, `Description`) VALUES
	(1, 'U', 'Usuario no registrado'),
	(2, 'C', 'Usuario registrado'),
	(3, 'A', 'Administrador');
/*!40000 ALTER TABLE `Rols` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `Menus` (
  `MenuID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Type` enum('S','I') NOT NULL,
  `State` bit(1) NOT NULL DEFAULT b'1',
  `RolID` int(11) NOT NULL,
  `MenuID_Menu` int(11) DEFAULT NULL,
  `Url` varchar(100) DEFAULT NULL,
  `Icon` varchar(50) DEFAULT NULL,
  constraint `PK_Menus` PRIMARY KEY (`MenuID`),
  KEY `FK_Menus_RolID` (`RolID`),
  KEY `FK_Menus_MenuID_Menu` (`MenuID_Menu`),
  CONSTRAINT `FK_Menus_RolID` FOREIGN KEY (`RolID`) REFERENCES `Rols` (`RolID`),
  CONSTRAINT `FK_Menus_MenuID_Menu` FOREIGN KEY (`MenuID_Menu`) REFERENCES `Menus` (`MenuID`)
);

INSERT INTO `Menus` (`MenuID`, `Name`, `Type`, `State`, `RolID`, `Url`, `Icon`) VALUES
	(1, 'Obtener plan', 'I', 1, '2', '/planazzo/faces/privado/cliente/index.xhtml#plan', 'pi pi-eye');
INSERT INTO `Menus` (`MenuID`, `Name`, `Type`, `State`, `RolID`, `Url`, `Icon`) VALUES
	(2, 'Crear plan', 'I', 1, '2', '/planazzo/faces/privado/cliente/createPlan.xhtml', 'pi pi-plus-circle');
INSERT INTO `Menus` (`MenuID`, `Name`, `Type`, `State`, `RolID`, `Url`, `Icon`) VALUES
	(3, 'Mis planes', 'I', 1, '2', '/planazzo/faces/privado/cliente/profile.xhtml', 'pi pi-heart');
INSERT INTO `Menus` (`MenuID`, `Name`, `Type`, `State`, `RolID`, `Url`, `Icon`) VALUES
	(4, 'Crear plan', 'I', 1, '3', '/planazzo/faces/privado/administrador/createPlan.xhtml', 'pi pi-plus-circle');
INSERT INTO `Menus` (`MenuID`, `Name`, `Type`, `State`, `RolID`, `Url`, `Icon`) VALUES
	(5, 'Editar plan', 'I', 1, '3', '/planazzo/faces/privado/cliente/editPlan.xhtml', 'pi pi-pencil');
INSERT INTO `Menus` (`MenuID`, `Name`, `Type`, `State`, `RolID`, `Url`, `Icon`) VALUES
	(6, 'Obtener plan', 'I', 1, '1', '/planazzo/faces/index.xhtml#plan', 'pi pi-eye');

CREATE TABLE IF NOT EXISTS `Users`(
	`UserID` int(11) NOT NULL AUTO_INCREMENT,
	`Username` VARCHAR(20) not null UNIQUE,
	`Email` varchar(50) not null UNIQUE,
	`Password` VARCHAR(100) not null,
	`Name` varchar(50) not null,
	`Type` VARCHAR(20) not null,
	`RolID` int(11) NOT NULL,
	constraint `PK_Users` PRIMARY KEY(`UserID`),
	KEY `FK_Users_RolID` (`RolID`),
	CONSTRAINT `FK_Users_RolID` FOREIGN KEY (`RolID`) REFERENCES `Rols` (`RolID`));

INSERT INTO `Users` (`UserID`, `Username`, `Email`, `Password`, `Name`, `Type`, `RolID`) VALUES
	(1, 'admin', 'admin@admin.es', '$2a$10$KL7ZDhd1wmtUJxFCzMau0eIFsK8AtqRlZPg5DpCQQoiI6/kL8UDm.', 'admin', 'A', 3);
INSERT INTO `Users` (`UserID`, `Username`, `Email`, `Password`, `Name`, `Type`, `RolID`) VALUES
	(2, 'user', 'user@user.es', '$2a$10$KL7ZDhd1wmtUJxFCzMau0eIFsK8AtqRlZPg5DpCQQoiI6/kL8UDm.', 'user', 'C', 2);

CREATE TABLE IF NOT EXISTS `Clients`(
	`ClientID` int(11) NOT NULL AUTO_INCREMENT,
	`UserID` int(11) NOT NULL,
	`City` VARCHAR(30) null,
	`Age` int(2) null,
	constraint `PK_Clients` PRIMARY KEY(`ClientID`),
	KEY `FK_Clients_UserID` (`UserID`),
    CONSTRAINT `FK_Clients_UserID` FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`));

INSERT INTO `Clients` (`ClientID`, `UserID`, `City`, `Age`) VALUES
	(1, 2, 'Leon', 12);

CREATE TABLE IF NOT EXISTS `Admins`(
	`AdminID` int(11) NOT NULL AUTO_INCREMENT,
	`UserID` int(11) NOT NULL,
	`AdminCode` VARCHAR(10) null,
	constraint `PK_Admins` PRIMARY KEY(`AdminID`),
	KEY `FK_Admins_UserID` (`UserID`),
    CONSTRAINT `FK_Admins_UserID` FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`));	

INSERT INTO `Admins` (`AdminID`, `UserID`, `AdminCode`) VALUES
	(1, 1, '1234567890');

CREATE TABLE IF NOT EXISTS `Plans`(
	`PlanID` int(11) not null AUTO_INCREMENT,
	`AdminID` int(11) null,
	`ClientID` int(11) null,
	`Likes` int(16) null DEFAULT 0,
	`Name` VARCHAR(50) not null,
	`Description` VARCHAR(1000) not null,
	`Image` VARCHAR(100) DEFAULT 'adventureClouds.jpg',
	`Type` enum('En casa', 'Gastronómico', 'Fiesta', 'Otro') not null,
	`Price` enum('€', '€€', '€€€', '€€€€') not null,
	`City` VARCHAR(30) not null,
	`Age` enum('3-12 años', '12-17 años', '18-24 años', '24-35 años', '35-60 años', '60-70 años', 'Otro') not null,
	`Companion` enum('En familia', 'Con amigos', 'Solo', 'En pareja') not null,
	`Verified` bit(1) not null DEFAULT 0,
	constraint `PK_Plans` PRIMARY KEY(`PlanID`),
	KEY `FK_Plans_ClientID` (`ClientID`), 
  	KEY `FK_Plans_AdminID` (`AdminID`),
  	CONSTRAINT `FK_Plans_ClientID` FOREIGN KEY (`ClientID`) REFERENCES `Clients` (`ClientID`),
  	CONSTRAINT `FK_Plans_AdminID` FOREIGN KEY (`AdminID`) REFERENCES `Admins` (`AdminID`));

INSERT INTO `Plans` (`PlanID`, `AdminID`, `ClientID`, `Likes`, `Name`, `Description`, `Image`, `Type`, `Price`, `City`, `Age`, `Companion`, `Verified`) VALUES
	(1, 1, 1, 10, 'Ir a la playa', 'deadwad adaw daw dawd a', 'adventureClouds.jpg', 'En casa', '€', 'LEON', '18-24 años', 'En familia', 1);
INSERT INTO `Plans` (`PlanID`, `AdminID`, `ClientID`, `Likes`, `Name`, `Description`, `Image`, `Type`, `Price`, `City`, `Age`, `Companion`, `Verified`) VALUES
	(2, 1, 1, 101, 'Ir a el cine', 'deadwad adaw daw dawd a', 'adventureClouds.jpg', 'En casa', '€', 'LEON', '18-24 años', 'En familia', 1);
INSERT INTO `Plans` (`PlanID`, `AdminID`, `ClientID`, `Likes`, `Name`, `Description`, `Image`, `Type`, `Price`, `City`, `Age`, `Companion`, `Verified`) VALUES
	(3, 1, 1, 102, 'Ir a correr', 'deadwad adaw daw dawd a', 'adventureClouds.jpg', 'En casa', '€', 'LEON', '18-24 años', 'En familia', 1);
INSERT INTO `Plans` (`PlanID`, `AdminID`, `ClientID`, `Likes`, `Name`, `Description`, `Image`, `Type`, `Price`, `City`, `Age`, `Companion`, `Verified`) VALUES
	(4, 1, 1, 0, 'No hacer nada', 'deadwad adaw daw dawd a', 'adventureClouds.jpg', 'En casa', '€', 'LEON', '18-24 años', 'En familia', 1);
INSERT INTO `Plans` (`PlanID`, `AdminID`, `ClientID`, `Likes`, `Name`, `Description`, `Image`, `Type`, `Price`, `City`, `Age`, `Companion`, `Verified`) VALUES
	(5, 1, 1, 0, 'Ir al espacio', 'deadwad adaw daw dawd a', 'adventureClouds.jpg', 'En casa', '€', 'LEON', '18-24 años', 'En familia', 0);

CREATE TABLE IF NOT EXISTS `ClientPlan`(
	`ClientPlanID` int(11) NOT NULL AUTO_INCREMENT,
	`ClientID` int(11) NOT NULL,
	`PlanID` int(11) not null,
	constraint `PK_ClietPlan` PRIMARY KEY(`ClientPlanID`),
	KEY `FK_ClientPlan_ClientID` (`ClientID`),
  	KEY `FK_ClientPlan_PlanID` (`PlanID`),
  	CONSTRAINT `FK_ClientPlan_ClientID` FOREIGN KEY (`ClientID`) REFERENCES `Clients` (`ClientID`),
  	CONSTRAINT `FK_ClientPlan_PlanID` FOREIGN KEY (`PlanID`) REFERENCES `Plans` (`PlanID`));

CREATE TABLE IF NOT EXISTS `Reviews`(
	`ReviewID` int(11) NOT NULL AUTO_INCREMENT,
	`ClientID` int(11) not null,
	`PlanID` int(11) not null,
	`Comment` VARCHAR(500) not null,
	`Title` VARCHAR(30) not null,
	`Valoration` int(1) NOT NULL,
	constraint `PK_Reviews` PRIMARY KEY(`ReviewID`),
	KEY `FK_Reviews_ClientID` (`ClientID`),
  	KEY `FK_Reviews_PlanID` (`PlanID`),
  	CONSTRAINT `FK_Reviews_ClientID` FOREIGN KEY (`ClientID`) REFERENCES `Clients` (`ClientID`),
  	CONSTRAINT `FK_Reviews_PlanID` FOREIGN KEY (`PlanID`) REFERENCES `Plans` (`PlanID`));

CREATE TABLE IF NOT EXISTS `Favourite`(
	`FavouriteID` int(11) NOT NULL AUTO_INCREMENT,
	`PlanID` int(11) not null,
	`ClientID` int(11) not null,
	constraint `PK_Favourite` PRIMARY KEY(`FavouriteID`),
  	KEY `FK_Favourite_PlanID` (`PlanID`),
	KEY `FK_Favourite_ClientID` (`ClientID`),
	CONSTRAINT `FK_Favourite_ClientID` FOREIGN KEY (`ClientID`) REFERENCES `Clients` (`ClientID`),
  	CONSTRAINT `FK_Favourite_PlanID` FOREIGN KEY (`PlanID`) REFERENCES `Plans` (`PlanID`));

