DROP TABLE IF EXISTS PRICE;
CREATE TABLE PRICE  (
                        ID int NOT NULL AUTO_INCREMENT,
                        BRAND_ID int,
                        START_DATE TIMESTAMP,
                        END_DATE TIMESTAMP,
                        PRODUCT_ID varchar(255),
                        PRICE DOUBLE PRECISION,
                        PRIORITY int,
                        CURRENCY_CODE varchar(15)
);