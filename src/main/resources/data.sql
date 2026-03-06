INSERT INTO product (name, bar_code, price, price_type) VALUES

('Arroz Tipo 1 5kg', '7891020100103', 24.90, 'UNIT'),
('Feijão Carioca 1kg', '7894000200202', 8.49, 'UNIT'),
('Açúcar Refinado 1kg', '7851000300301', 4.79, 'UNIT'),
('Leite Integral 1L', '7891600400400', 5.39, 'UNIT'),
('Óleo de Soja 900ml', '7897000500509', 7.89, 'UNIT'),
('Café Torrado e Moído 500g', '7791000600608', 16.90, 'UNIT'),
('Carne Bovina Alcatra', '7891008700707', 39.90, 'WEIGHT'),
('Frango Inteiro Resfriado', '7801000800806', 12.99, 'WEIGHT'),
('Tomate', '7891000000905', 6.99, 'WEIGHT'),
('Banana Prata', '7091001001004', 4.49, 'WEIGHT'),
('Detergente Líquido 500ml', '7811001101103', 2.39, 'UNIT'),
('Papel Higiênico 12 Rolos', '7811001201202', 18.90, 'UNIT'),
('Manteiga 200g', '7891001301300', 9.79, 'UNIT'),
('Queijo Mussarela', '7891001409400', 34.90, 'WEIGHT'),
('Refrigerante Cola 2L', '7891011501509', 8.99, 'UNIT');


INSERT INTO terminal (id, terminal_code, active)
VALUES ('7c8e6f7e-8c4c-4c5c-9d2e-1d4c92b4d111','TOTEM-01', true);

INSERT INTO terminal (id,terminal_code, active)
VALUES ('3e3c45c9-9e21-4b3a-88a5-9e2a1e1e2222','TOTEM-02', true);

INSERT INTO employee (id,badge_id, name, pin_hash, role, active, failed_attempts)
VALUES (
           '2d1a7a1b-8e1a-4a52-9f42-9c4b1a1a3333',
           1001,
           'Carlos Silva',
           '$2a$10$X5e.uag5s37yia2QinBAb.BWd09F6wl9ym2tNY3Up0N4tfa9osI9e',
           'OPERATOR',
           true,
           0
       );

INSERT INTO employee (id,badge_id, name, pin_hash, role, active, failed_attempts)
VALUES (
           '9b8f2a3d-2e1f-4d1e-9a7b-2b1c44444444',
           1002,
           'Ana Costa',
           '$2a$10$7QJ2hXKc6JkL8WgX5m2xUuM1HcO7x3c2d3HhV0t6pOq8zRk9d9X3e',
           'OPERATOR',
           true,
           0
       );
