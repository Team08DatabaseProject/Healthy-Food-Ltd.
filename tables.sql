-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Vert: 127.0.0.1
-- Generert den: 18. Mar, 2016 18:52 PM
-- Tjenerversjon: 5.5.47-0ubuntu0.14.04.1
-- PHP-Versjon: 5.5.9-1ubuntu4.14

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET NAMES utf8 */;

--
-- Database: `thomjos`
--

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `zipcode` int(11) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `zipcode` (`zipcode`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin  AUTO_INCREMENT=3 ;

--
-- Dataark for tabell `address`
--

INSERT INTO `address` (`address_id`, `address`, `zipcode`) VALUES
  (1, 'Osloveien 56B', 7017),
  (2, 'Olav Tryggvasons gate 3 ', 7017);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `business_name` varchar(64) DEFAULT NULL,
  `first_name` varchar(64) DEFAULT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `isbusiness` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  KEY `address_id` (`address_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dataark for tabell `customer`
--

INSERT INTO `customer` (`c_id`, `business_name`, `first_name`, `last_name`, `phone`, `email`, `address_id`, `isbusiness`) VALUES
  (1, 'firstBusinessCustomer', 'firstContactPerson', 'afterNameContactPerson', 922992, 'firstBusinessCustomerEmail', 1, 1),
  (2, 'firstOrdinaryCustomer', 'firstcustomer', 'aftercustomer', 93322992, 'firstOrdiaryCustomerEmail', 1, 0);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `data_cache`
--

DROP TABLE IF EXISTS `data_cache`;
CREATE TABLE `data_cache` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataname` varchar(50) DEFAULT NULL,
  `VALUE` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `dish`
--

DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `price` double DEFAULT NULL,
  `dish_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dish_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dataark for tabell `dish`
--

INSERT INTO `dish` (`price`, `dish_id`, `name`) VALUES
  (32, 1, 'Spaghetti'),
  (14, 2, 'Meatballs');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(64) DEFAULT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `pos_id` int(11) NOT NULL,
  `salary` double DEFAULT NULL,
  `passhash` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `username` (`username`),
  KEY `address_id` (`address_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dataark for tabell `employee`
--

INSERT INTO `employee` (`employee_id`, `first_name`, `last_name`, `phone`, `email`, `address_id`, `username`, `pos_id`, `salary`, `passhash`) VALUES
  (1, 'Paul Thomas', 'Korsvold', 99110488, 'paultk@student.hist.no', 1, 'PTKM', 1, 1000, '-11-80-37-128-9630-127-12366787-42-10811914-5-9123-20-38'),
  (2, 'Bob', 'Testdriver', 90133787, 'bobtestdriver@gmail.com', 2, 'testdriver', 3, 200000, 'testhash'),
  (3, 'Important', 'Bossman', 12345678, 'importantbossman@gmail.com', 1, 'testceo', 1, 10000000, 'testceohash'),
  (4, 'Axel', 'Kvistad', 90133787, 'axel.b.kvistad@gmail.com', 2, 'axelbkv', 3, 200000, 'axelerbra'),
  (5, 'Spaghetti', 'Meatballs', 87654321, 'spaghetti.meatballs@gmail.com', 2, 'testchef', 2, 300000, 'testchefhash');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `employee_position`
--

DROP TABLE IF EXISTS `employee_position`;
CREATE TABLE `employee_position` (
  `pos_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `default_salary` double NOT NULL,
  PRIMARY KEY (`pos_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dataark for tabell `employee_position`
--

INSERT INTO `employee_position` (`pos_id`, `description`, `default_salary`) VALUES
  (1, 'CEO', 800000),
  (2, 'Chef', 700000),
  (3, 'Driver', 400000),
  (4, 'Sales', 350000),
  (5, 'Nutrition', 650000);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE `ingredient` (
  `ingredient_id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity_owned` double DEFAULT NULL,
  `quantity_reserved` double DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ingredient_id`),
  KEY `supplier_id` (`supplier_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dataark for tabell `ingredient`
--

INSERT INTO `ingredient` (`ingredient_id`, `quantity_owned`, `quantity_reserved`, `unit`, `price`, `supplier_id`, `description`) VALUES
  (1, 200, 0, 'Kg', 35, 1, 'sugar'),
  (2, 300, 0, 'Kg', 353, 1, 'chocolate'),
  (3, 400, 0, 'Kg', 31, 1, 'flour');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `ingredient_in_dish`
--

DROP TABLE IF EXISTS `ingredient_in_dish`;
CREATE TABLE `ingredient_in_dish` (
  `ingredient_id` int(11) NOT NULL DEFAULT '0',
  `dish_id` int(11) NOT NULL DEFAULT '0',
  `quantity` double DEFAULT NULL,
  PRIMARY KEY (`ingredient_id`,`dish_id`),
  KEY `dish_id` (`dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `ingredient_in_dish`
--

INSERT INTO `ingredient_in_dish` (`ingredient_id`, `dish_id`, `quantity`) VALUES
  (1, 1, 1),
  (2, 2, 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `menu`
--

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `type_meal` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dataark for tabell `menu`
--

INSERT INTO `menu` (`menu_id`, `description`, `type_meal`) VALUES
  (1, 'firstMenu', 'not vegan!');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `menu_relation_dish`
--

DROP TABLE IF EXISTS `menu_relation_dish`;
CREATE TABLE `menu_relation_dish` (
  `dish_id` int(11) NOT NULL DEFAULT '0',
  `menu_id` int(11) NOT NULL DEFAULT '0',
  `quantity` int(11) DEFAULT '1',
  PRIMARY KEY (`dish_id`,`menu_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `menu_relation_dish`
--

INSERT INTO `menu_relation_dish` (`dish_id`, `menu_id`, `quantity`) VALUES
  (1, 1, 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `n_data`
--

DROP TABLE IF EXISTS `n_data`;
CREATE TABLE `n_data` (
  `dataname` varchar(50) DEFAULT NULL,
  `VALUE` varchar(50) DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `n_order`
--

DROP TABLE IF EXISTS `n_order`;
CREATE TABLE `n_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `subscription_id` int(11) DEFAULT NULL,
  `customer_requests` varchar(100) DEFAULT NULL,
  `delivery_date` datetime DEFAULT NULL,
  `delivered_date` datetime DEFAULT NULL,
  `price` double DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `subscription_id` (`subscription_id`),
  KEY `customer_id` (`customer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dataark for tabell `n_order`
--

INSERT INTO `n_order` (`order_id`, `customer_id`, `subscription_id`, `customer_requests`, `delivery_date`, `delivered_date`, `price`, `address`) VALUES
  (1, 1, 1, 'hold the pickle, hold the lettuce, special orders do upset us', '2016-04-23 00:00:00', NULL, 2000, 'johann sollis gate 3');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `subscription`
--

DROP TABLE IF EXISTS `subscription`;
CREATE TABLE `subscription` (
  `subscription_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`subscription_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dataark for tabell `subscription`
--

INSERT INTO `subscription` (`subscription_id`, `start_date`, `end_date`) VALUES
  (1, '2014-12-14', '2016-05-02'),
  (2, '2012-12-14', '2018-05-02'),
  (3, '2014-12-14', '2016-05-02'),
  (4, '2012-12-14', '2018-05-02');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `supplier_id` int(11) NOT NULL AUTO_INCREMENT,
  `business_name` varchar(50) NOT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`),
  KEY `address_id` (`address_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dataark for tabell `supplier`
--

INSERT INTO `supplier` (`supplier_id`, `business_name`, `phone`, `address_id`) VALUES
  (1, 'Freia', '2201943', 1),
  (2, 'Kraft', '2201943', 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `zipcode`
--

DROP TABLE IF EXISTS `zipcode`;
CREATE TABLE `zipcode` (
  `place` varchar(255) DEFAULT NULL,
  `zipcode` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`zipcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `zipcode`
--

INSERT INTO `zipcode` (`place`, `zipcode`) VALUES
  ('Trondheim', 7017),
  ('Trondheim', 7018),
  ('Trondheim', 7019);

--
-- Begrensninger for dumpede tabeller
--

--
-- Begrensninger for tabell `address`
--
ALTER TABLE `address`
ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`zipcode`) REFERENCES `zipcode` (`zipcode`);

--
-- Begrensninger for tabell `customer`
--
ALTER TABLE `customer`
ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`);

--
-- Begrensninger for tabell `employee`
--
ALTER TABLE `employee`
ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`);

--
-- Begrensninger for tabell `ingredient`
--
ALTER TABLE `ingredient`
ADD CONSTRAINT `ingredient_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`);

--
-- Begrensninger for tabell `ingredient_in_dish`
--
ALTER TABLE `ingredient_in_dish`
ADD CONSTRAINT `ingredient_in_dish_ibfk_1` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`ingredient_id`),
ADD CONSTRAINT `ingredient_in_dish_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`);

--
-- Begrensninger for tabell `menu_relation_dish`
--
ALTER TABLE `menu_relation_dish`
ADD CONSTRAINT `menu_relation_dish_ibfk_1` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`),
ADD CONSTRAINT `menu_relation_dish_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`);

--
-- Begrensninger for tabell `n_order`
--
ALTER TABLE `n_order`
ADD CONSTRAINT `n_order_ibfk_1` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`subscription_id`),
ADD CONSTRAINT `n_order_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`c_id`);

--
-- Begrensninger for tabell `supplier`
--
ALTER TABLE `supplier`
ADD CONSTRAINT `supplier_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`);
SET FOREIGN_KEY_CHECKS=1;
