# --------------------------------------------------------
# --  DDL for schema INTERNET_SHOP
# --------------------------------------------------------
# DROP SCHEMA IF EXISTS INTERNET_SHOP;
# CREATE SCHEMA IF NOT INTERNET_SHOP;
#
# --------------------------------------------------------
# --  DDL for Table CATEGORY
# --------------------------------------------------------
# DROP TABLE IF EXISTS INTERNET_SHOP.CATEGORY;
# CREATE TABLE IF NOT EXISTS INTERNET_SHOP.CATEGORY
# (
#     ID     INT                 NOT NULL AUTO_INCREMENT,
#     NAME   VARCHAR(45)         NOT NULL,
#     RATING INT                 NOT NULL,
#     IMAGE_PATH VARCHAR(45)     NOT NULL,
#     PRIMARY KEY (ID),
#     UNIQUE INDEX IDX_CATEGORY_CATEGORY_ID_UNIQUE (ID ASC),
#     UNIQUE INDEX IDX_CATEGORY_NAME_UNIQUE (NAME ASC)
# );
# --------------------------------------------------------
# --  DDL for Table PRODUCT
# --------------------------------------------------------
# DROP TABLE IF EXISTS INTERNET_SHOP.PRODUCT;
# CREATE TABLE IF NOT EXISTS INTERNET_SHOP.PRODUCT
# (
#     ID          INT          NOT NULL AUTO_INCREMENT,
#     NAME        VARCHAR(200) NOT NULL,
#     DESCRIPTION VARCHAR(400) NOT NULL,
#     PRICE       DECIMAL(7,2) NOT NULL,
#     CATEGORY_ID INT          NOT NULL,
#     IMAGE_PATH  VARCHAR(45)  NOT NULL,
#     PRIMARY KEY (ID),
#     UNIQUE INDEX IDX_PRODUCT_ID_UNIQUE (ID ASC),
#     CONSTRAINT FK_PRODUCT_CATEGORY_ID_CATEGORY_ID
#         FOREIGN KEY (CATEGORY_ID) references INTERNET_SHOP.CATEGORY (ID)
#             ON DELETE CASCADE
#             ON UPDATE CASCADE
# );
# --------------------------------------------------------
# --  DDL for Table USER
# --------------------------------------------------------
# DROP TABLE IF EXISTS INTERNET_SHOP.USER;
# CREATE TABLE IF NOT EXISTS INTERNET_SHOP.USER
# (
#     ID            INT          NOT NULL AUTO_INCREMENT,
#     NAME          VARCHAR(45)  NOT NULL,
#     SURNAME       VARCHAR(45)  NOT NULL,
#     EMAIL         VARCHAR(45)  NOT NULL,
#     PASSWORD      VARCHAR(45)  NOT NULL,
#     LOGIN         VARCHAR(45)  NOT NULL,
#     BIRTH_DATE    DATE
#     BALANCE       DECIMAL (7,2),
#     PRIMARY KEY (ID),
#     UNIQUE INDEX IDX_USER_USER_ID_UNIQUE (ID ASC),
#     UNIQUE INDEX IDX_USER_LOGIN_UNIQUE (LOGIN ASC),
# );
# --------------------------------------------------------
# --  DDL for Table ORDER
# --------------------------------------------------------
# DROP TABLE IF EXISTS INTERNET_SHOP.ORDER;
# CREATE TABLE IF NOT EXISTS INTERNET_SHOP.ORDER
# (
#     ID         INT           NOT NULL AUTO_INCREMENT,
#     PRICE      DECIMAL (7,2) NOT NULL,
#     DATE_ORDER DATE          NOT NULL,
#     USER_ID INT              NOT NULL,
#     PRIMARY KEY (ID),
#     UNIQUE INDEX IDX_ORDER_ID_UNIQUE (ID ASC),
#     CONSTRAINT FK_ORDER_USER_ID_USER_ID
#         FOREIGN KEY (USER_ID) references INTERNET_SHOP.USER (ID)
#             ON DELETE CASCADE
#             ON UPDATE CASCADE
# );
# --------------------------------------------------------
# --  DDL for Table ORDER_PRODUCTS
# --------------------------------------------------------
# DROP TABLE IF EXISTS INTERNET_SHOP.ORDER_PRODUCTS;
# CREATE TABLE IF NOT EXISTS INTERNET_SHOP.ORDER_PRODUCTS
# (
#     ORDER_ID         INT NOT NULL,
#     PRODUCT_ID       INT NOT NULL,
#     PRODUCT_QUANTITY INT NOT NULL,
#     PRIMARY KEY (ORDER_ID, PRODUCT_ID),
#     CONSTRAINT FK_ORDER_PRODUCTS_ORDER_ID_ORDER_ID
#         FOREIGN KEY (ORDER_ID)
#             REFERENCES INTERNET_SHOP.ORDER (ID)
#             ON DELETE CASCADE
#             ON UPDATE CASCADE,
#     CONSTRAINT FK_ORDER_PRODUCTS_PRODUCT_ID_PRODUCT_ID
#         FOREIGN KEY (PRODUCT_ID)
#             REFERENCES PRODUCT (ID)
#             ON DELETE CASCADE
#             ON UPDATE CASCADE
# );
------------------------------------------------------
--  DML for Table INTERNET_SHOP.CATEGORY
------------------------------------------------------
# ALTER TABLE `internet_shop`.`product`
#     CHANGE COLUMN `DESCRIPTION` `DESCRIPTION` VARCHAR(400) NULL DEFAULT NULL ;
#

INSERT INTO INTERNET_SHOP.CATEGORY (ID, NAME, RATING, IMAGE_PATH)
VALUES (1, 'Mobile phones', 3, 'mobile.jpg');
INSERT INTO INTERNET_SHOP.CATEGORY (ID, NAME, RATING, IMAGE_PATH)
VALUES (2, 'Laptops', 3, 'laptop.jpg');
INSERT INTO INTERNET_SHOP.CATEGORY (ID, NAME, RATING, IMAGE_PATH)
VALUES (3, 'GPS Navigators', 3, 'jps_nav.jpg');
INSERT INTO INTERNET_SHOP.CATEGORY (ID, NAME, RATING, IMAGE_PATH)
VALUES (4, 'Fridges', 3, 'fridge.jpg');
INSERT INTO INTERNET_SHOP.CATEGORY (ID, NAME, RATING, IMAGE_PATH)
VALUES (5, 'Cars', 3, 'car.jpg');
INSERT INTO INTERNET_SHOP.CATEGORY (ID, NAME, RATING, IMAGE_PATH)
VALUES (6, 'Cameras', 5, 'camera.jpg');
------------------------------------------------------
--  DML for Table INTERNET_SHOP.PRODUCT
--------------------------------------------------------
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (1, 'Canon','Good camera', 200.00, 6, 'camera1.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (2, 'Nikon', 'Very good camera', 250.00, 6, 'camera2.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (3, 'BMW', 'Good car', 2300.00, 5, 'car1.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (4, 'Nissan', 'Very good car', 2450.00, 5, 'car2.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (5, 'LG', 'Good fridge', 900.00, 4, 'fridge1.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (6, 'SAMSUNG', 'Very good fridge', 1050.00, 4, 'fridge2.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (7, 'HP', 'Good laptop', 800.00, 2, 'laptop1.png');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (8, 'DELL', 'Very good laptop', 950.00, 2, 'laptop2.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (9, 'HTC', 'Good mobile phone', 500.00, 1, 'mobile1.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (10, 'ZTE', 'Very good mobile phone', 550.00, 1, 'mobile2.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (11, 'Navitel', 'Good navigator', 300.00, 3, 'navigator1.jpg');
INSERT INTO INTERNET_SHOP.PRODUCT (ID, NAME, DESCRIPTION, PRICE, CATEGORY_ID, IMAGE_PATH)
VALUES (12, 'Prestigio', 'Very good navigator', 450.00, 3, 'navigator2.jpg');
--------------------------------------------------------
-- --  DML for Table INTERNET_SHOP.USER
-- --------------------------------------------------------
INSERT INTO INTERNET_SHOP.USER (NAME, SURNAME, EMAIL, PASSWORD, LOGIN, BIRTH_DATE, BALANCE)
VALUES ('admin', 'admin', 'admin@gmail.com', 'admin', 'admin', '1994-11-06', '100.00');
INSERT INTO INTERNET_SHOP.USER (NAME, SURNAME, EMAIL, PASSWORD, LOGIN, BIRTH_DATE, BALANCE)
VALUES ('denis', 'andreev', 'denis@gmail.com', 'vcxz123', 'vcxz', '1994-11-06', '80.00');

-- --------------------------------------------------------


