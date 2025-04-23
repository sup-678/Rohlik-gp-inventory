INSERT INTO product (name, stock, price) VALUES
('Bread', 100, 2.99),
('Milk', 150, 1.99),
('Eggs', 200, 3.49),
('Cheese', 75, 4.99),
('Apples', 80, 1.49),
('Bananas', 120, 0.99),
('Chicken Breast', 50, 5.99),
('Ground Beef', 60, 6.49),
('Rice', 200, 3.99),
('Pasta', 150, 2.49),
('Tomatoes', 90, 2.99),
('Potatoes', 110, 1.29),
('Onions', 70, 1.09),
('Garlic', 85, 0.89),
('Yogurt', 45, 1.79),
('Butter', 65, 2.39),
('Olive Oil', 40, 6.99),
('Salt', 200, 0.99),
('Sugar', 180, 1.99),
('Coffee', 95, 4.49);

INSERT INTO orders (status, created_at) VALUES ('CREATED', '2023-10-01 09:00:00');
INSERT INTO order_item (product_id, order_id, quantity, price_at_order, unit_price)
VALUES (1, (SELECT MAX(id) FROM orders), 2, (SELECT price FROM product WHERE id = 1), 2.9),
       (2, (SELECT MAX(id) FROM orders), 3, (SELECT price FROM product WHERE id = 2), 3.9);

INSERT INTO orders (status, created_at) VALUES ('PAID', '2023-10-01 10:30:00');
INSERT INTO order_item (product_id, order_id, quantity, price_at_order, unit_price)
VALUES (3, (SELECT MAX(id) FROM orders), 5, (SELECT price FROM product WHERE id = 3), 4.9),
       (4, (SELECT MAX(id) FROM orders), 2, (SELECT price FROM product WHERE id = 4), 5.9);

INSERT INTO orders (status, created_at) VALUES ('CANCELED', '2023-10-01 11:45:00');
INSERT INTO order_item (product_id, order_id, quantity, price_at_order, unit_price)
VALUES (5, (SELECT MAX(id) FROM orders), 4, (SELECT price FROM product WHERE id = 5), 6.9);

INSERT INTO orders (status, created_at) VALUES ('CREATED', CURRENT_TIMESTAMP());
INSERT INTO order_item (product_id, order_id, quantity, price_at_order, unit_price)
VALUES (6, (SELECT MAX(id) FROM orders), 10, (SELECT price FROM product WHERE id = 6), 7.9),
       (7, (SELECT MAX(id) FROM orders), 2, (SELECT price FROM product WHERE id = 7), 8.9);

INSERT INTO orders (status, created_at) VALUES ('CREATED', CURRENT_TIMESTAMP());
INSERT INTO order_item (product_id, order_id, quantity, price_at_order, unit_price)
VALUES (8, (SELECT MAX(id) FROM orders), 3, (SELECT price FROM product WHERE id = 8), 9.9),
       (9, (SELECT MAX(id) FROM orders), 5, (SELECT price FROM product WHERE id = 9), 0.1);