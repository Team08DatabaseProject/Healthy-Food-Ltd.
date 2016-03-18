DROP TABLE IF EXISTS subscription; -- reoccuring orders
DROP TABLE IF EXISTS menu_relation_order;
DROP TABLE IF EXISTS menu_relation_dish;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS n_order;
DROP TABLE IF EXISTS customer_order;-- look
DROP TABLE IF EXISTS ingredient_in_dish;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS employee_position;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS supplier_relation;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS n_data;
DROP TABLE IF EXISTS data_cache;

CREATE TABLE subscription(
  subscription_id INTEGER NOT NULL AUTO_INCREMENT,
  start_date DATE,
  end_date DATE,
  -- num_days_between INTEGER,
  -- defined_days VARCHAR(255),
  PRIMARY KEY(subscription_id));
  
CREATE TABLE n_order(
  order_id INTEGER NOT NULL AUTO_INCREMENT,
  subscription_id INTEGER DEFAULT NULL,
  customer_requests VARCHAR(100),
  delivery_date DATETIME,
  delivered_date DATETIME DEFAULT NULL,
  price DOUBLE DEFAULT NULL,
  address VARCHAR(255), -- this is the only address field that is not connected to anything. This is written in runtime in java
  PRIMARY KEY(order_id));


CREATE TABLE person(
  person_id INTEGER,
  firstname VARCHAR(255),
  lastname VARCHAR(255),
  phone INTEGER,
  email VARCHAR(40),
  address_id INTEGER,
  PRIMARY KEY(person_id));

CREATE TABLE customer(
  c_id INTEGER,
  person_id INTEGER UNIQUE,  -- connection to person 
  isbusiness TINYINT(1) UNSIGNED, -- boolean
  PRIMARY KEY(c_id));
  
CREATE TABLE employee(
  person_id INTEGER, -- connection to person 
  username VARCHAR(20),
  pos_id INTEGER NOT NULL, -- connection to employee_position
  salary DOUBLE,
  passhash VARCHAR(64),
  PRIMARY KEY(person_id));

CREATE TABLE employee_position(
  pos_id INTEGER,
  description VARCHAR(255),
  n_privilege INTEGER AUTO_INCREMENT NOT NULL ,-- privilege determines what window starts up after login.
  default_salary DOUBLE NOT NULL,
  PRIMARY KEY(pos_id)
);

CREATE TABLE address(
  address_id INTEGER NOT NULL AUTO_INCREMENT,
  address VARCHAR(255),
  zipcode INTEGER,
  PRIMARY KEY(address_id));

CREATE TABLE zipcode(
  place VARCHAR(255),
  zipcode INTEGER,
  PRIMARY KEY(zipcode));

CREATE TABLE customer_order(
  order_id INTEGER,
  c_id INTEGER NOT NULL,
  PRIMARY KEY(order_id));

CREATE TABLE ingredient(
  ingredient_id INTEGER,
  quantity_owned DOUBLE,
  quantity_reserved DOUBLE,
  unit VARCHAR(20),
  price DOUBLE,
  supplier_id INTEGER,
  -- nutrition VARCHAR(100),
  description VARCHAR(255),
  PRIMARY KEY(ingredient_id));

CREATE TABLE ingredient_in_dish(
  ingredient_id INTEGER, -- connected to Ingredient
  dish_id INTEGER, -- connected to dish
  quantity DOUBLE, -- amount of Ingredient needed for the dish, in the unit specify
  PRIMARY KEY(ingredient_id, dish_id));

CREATE TABLE menu(
  menu_id INTEGER,
  description VARCHAR(100),
  type_meal VARCHAR(70) DEFAULT NULL,
  -- portion INTEGER,
  PRIMARY KEY(menu_id));

CREATE TABLE supplier(
  supplier_id INTEGER,
  phone VARCHAR(50),
  address_id INTEGER,
  PRIMARY KEY(supplier_id));

CREATE TABLE dish(
  price DOUBLE,
  dish_id INTEGER,
  NAME VARCHAR(255),
  PRIMARY KEY(dish_id));

CREATE TABLE menu_relation_dish(
  dish_id INTEGER,
  menu_id INTEGER,
  quantity INTEGER DEFAULT '1',
  PRIMARY KEY(dish_id, menu_id));


CREATE TABLE n_data(
  dataname VARCHAR(50),
  VALUE VARCHAR(50),
  TIMESTAMP TIMESTAMP
  -- gross_profit ,
  -- profit ,
  -- expenses ,
  -- update_jclients ,
);

CREATE TABLE data_cache(
  id INTEGER(11) NOT NULL AUTO_INCREMENT,
  dataname VARCHAR(50),
  VALUE INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id)
) ENGINE=MYISAM DEFAULT CHARSET=latin1;

-- ****************************************************************************
-- ****************************************************************************
-- ****************************************************************************
-- ****************************************************************************

-- Not sure if this was supposed to be called "menu_relation_order" or really "order_relation_dish"

-- CREATE TABLE order_relation_dish(
--  dish_id INTEGER,
--  order_id INTEGER,
--  quantity INTEGER DEFAULT '1',
--  PRIMARY KEY(dish_id, order_id));


-- Commented because of the comments above /\

-- ALTER TABLE menu_relation_order
-- ADD FOREIGN KEY(menu_id)
-- REFERENCES menu(menu_id),
-- ADD FOREIGN KEY(order_id)
-- REFERENCES n_order(order_id);

-- ****************************************************************************
-- ****************************************************************************
-- ****************************************************************************
-- ****************************************************************************
 
ALTER TABLE person
ADD FOREIGN KEY(address_id)
REFERENCES address(address_id);

ALTER TABLE address
ADD FOREIGN KEY(zipcode)
REFERENCES zipcode(zipcode);

ALTER TABLE n_order
ADD FOREIGN KEY(subscription_id)
REFERENCES subscription(subscription_id),
ADD FOREIGN KEY(order_id)
REFERENCES customer_order(order_id);

ALTER TABLE supplier
ADD FOREIGN KEY(address_id)
REFERENCES address(address_id);

ALTER TABLE ingredient
ADD FOREIGN KEY(supplier_id)
REFERENCES supplier(supplier_id);

ALTER TABLE menu_relation_dish
ADD FOREIGN KEY(dish_id)
REFERENCES dish(dish_id),
ADD FOREIGN KEY(menu_id)
REFERENCES menu(menu_id);

ALTER TABLE employee
ADD FOREIGN KEY(person_id)
REFERENCES person(person_id);

ALTER TABLE customer_order
ADD FOREIGN KEY(c_id)
REFERENCES customer(c_id);

ALTER TABLE ingredient_in_dish
ADD FOREIGN KEY(ingredient_id)
REFERENCES ingredient(ingredient_id),
ADD FOREIGN KEY(dish_id)
REFERENCES dish(dish_id);










-- DUMMY DATA

INSERT INTO zipcode VALUES ('Trondheim', 7017);
INSERT INTO zipcode VALUES ('Trondheim', 7018);
INSERT INTO zipcode VALUES ('Trondheim', 7019);

INSERT INTO address VALUES (DEFAULT, 'Osloveien 56B', 7017);
INSERT INTO address VALUES (DEFAULT, 'Olav Tryggvasons gate 3 ', 7017);

INSERT INTO person VALUES (DEFAULT, 'Paul Thomas', 'Korsvold', '99110488', 'paultk@student.hist.no', 1 );

INSERT INTO employee_position VALUES (DEFAULT, 'DRIVER', DEFAULT, 1000);

INSERT INTO employee VALUES (1, 'PTKM', 1, 1000, '-11-80-37-128-9630-127-12366787-42-10811914-5-9123-20-38');

INSERT INTO employee_position (pos_id, description, default_salary) VALUES
  (1, 'CEO', 800000),
  (2, 'Chef', 700000),
  (3, 'Driver', 400000),
  (4, 'Sales', 350000),
  (5, 'Nutrition', 650000);




