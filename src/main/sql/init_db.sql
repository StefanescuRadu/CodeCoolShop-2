
-- Dropping tables

DROP TABLE IF EXISTS "Products";
DROP TABLE IF EXISTS "ProductsCategories";
DROP TABLE IF EXISTS "Suppliers";

-- Create tables

CREATE TABLE "Products"(
                         id SERIAL NOT NULL,
                         name TEXT,
                         price INT,
                         currency TEXT,
                         description TEXT,
                         product_category TEXT,
                         supplier TEXT
);

CREATE TABLE "ProductsCategories"(
                                     id SERIAL NOT NULL,
                                     name TEXT,
                                     department TEXT,
                                     description TEXT
);

CREATE TABLE "Suppliers"(
                            id SERIAL NOT NULL,
                            name TEXT,
                            description TEXT
);

-- Insert elements into products

INSERT INTO
    "Products"(name,price,currency,description,product_category,supplier)
VALUES(
          'Amazon Fire',
           49.9,
          'USD',
          'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.',
          'Tablet',
          'Amazon'
      ),
      (
          'Lenovo IdeaPad Miix 700',
           479,
          'USD',
          'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.',
          'Tablet',
          'Lenovo'
      ),
      (
          'Amazon Fire HD 8',
           89,
          'USD',
          'Amazon\''s latest Fire HD 8 tablet is a great value for media consumption.',
          'Tablet',
          'Amazon'
      ),
               (
          'Lenovo Laptop',
           500,
          'USD',
          'Lenovo latest laptop',
          'Laptop',
          'Lenovo'
      );

-- Insert elements into productsCategory


INSERT INTO
    "ProductsCategories"(name,department,description)
VALUES(
          'Tablet',
          'Hardware',
          'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.'
      ),
      (
          'Laptop',
          'Hardware',
          'Description of a laptop.'
      );

-- Insert into suppliers

INSERT INTO
    "Suppliers"(name,description)
VALUES(
          'Amazon',
          'Digital content and services'

      ),
      (
          'Lenovo',
          'Computers'

      );

-- Select elements from products

SELECT *
FROM "Products";

-- Select elements from productsCategories

SELECT *
FROM "ProductsCategories";

-- Select elements from suppliers

SELECT *
FROM "Suppliers";