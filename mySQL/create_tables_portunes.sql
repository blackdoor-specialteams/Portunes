SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `Portunes` DEFAULT CHARACTER SET latin1 ;
USE `Portunes` ;

-- -----------------------------------------------------
-- Table `Portunes`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Portunes`.`User` (
  `userName` VARCHAR(18) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `password` BINARY(32) NULL DEFAULT NULL,
  `salt` BINARY(32) NULL DEFAULT NULL,
  PRIMARY KEY (`userName`),
  INDEX `password` (`password` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `Portunes`.`Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Portunes`.`Admin` (
  `adminName` VARCHAR(18) NOT NULL,
  `userName` VARCHAR(18) NOT NULL,
  PRIMARY KEY (`adminName`, `userName`),
  INDEX `userName_idx` (`userName` ASC),
  CONSTRAINT `adminName`
    FOREIGN KEY (`adminName`)
    REFERENCES `Portunes`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userName`
    FOREIGN KEY (`userName`)
    REFERENCES `Portunes`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `Portunes`.`History`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Portunes`.`History` (
  `length` INT(11) NULL DEFAULT NULL,
  `lastLoginIndex` INT(11) NULL DEFAULT NULL,
  `userName` VARCHAR(18) NOT NULL,
  `hid` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`hid`),
  UNIQUE INDEX `userName_UNIQUE` (`userName` ASC),
  CONSTRAINT `User_userName`
    FOREIGN KEY (`userName`)
    REFERENCES `Portunes`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `Portunes`.`LogIn`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Portunes`.`LogIn` (
  `hid` INT(11) NOT NULL,
  `ip` INT(10) UNSIGNED NULL DEFAULT NULL,
  `month` TINYINT(3) UNSIGNED NULL DEFAULT NULL,
  `day` TINYINT(3) UNSIGNED NULL DEFAULT NULL,
  `year` SMALLINT(5) UNSIGNED NULL DEFAULT NULL,
  `index` INT(11) NOT NULL,
  `hours` TINYINT(3) UNSIGNED NULL DEFAULT NULL,
  `minutes` TINYINT(3) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`hid`, `index`),
  CONSTRAINT `History_hid`
    FOREIGN KEY (`hid`)
    REFERENCES `Portunes`.`History` (`hid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
