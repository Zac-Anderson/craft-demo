INSERT INTO users(name, username, email, phone)
VALUES
    ('Phillip Rivers', 'chargers01', 'philip@chargers.com', '858-555-5555'),
    ('Shaq Oneil', 'lakers01', 'shaq@lakers.com', '202-555-5555');

INSERT INTO addresses(street, city, zipcode)
VALUES
    ('123 San Diego Lane', 'San Diego', '92131'),
    ('123 Los Angeles Lane', 'Los Angeles', '90001');

UPDATE users SET address_id = addresses.id FROM addresses
    WHERE addresses.street = '123 San Diego Lane'
    AND users.name = 'Phillip Rivers';

UPDATE users SET address_id = addresses.id FROM addresses
    WHERE addresses.street = '123 Los Angeles Lane'
    AND users.name = 'Shaq Oneil';