DROP TABLE IF EXISTS PRICE;
CREATE TABLE PRICE  (
                         id int,
                         brandId int,
                         startDate TIMESTAMP,
                         endDate TIMESTAMP,
                         productId varchar(255),
                         price DOUBLE PRECISION,
                         priority int,
                         currencyCode varchar(15)
);