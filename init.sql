CREATE DATABASE covoiturage_db;
\c covoiturage_db;
CREATE TABLE users (id BIGSERIAL PRIMARY KEY,first_name VARCHAR(80) NOT NULL,last_name VARCHAR(80) NOT NULL,email VARCHAR(160) UNIQUE NOT NULL,password_hash VARCHAR(255) NOT NULL,phone VARCHAR(32),bio TEXT,avatar_url VARCHAR(500),has_vehicle BOOLEAN DEFAULT FALSE,role VARCHAR(16) NOT NULL DEFAULT 'USER',created_at TIMESTAMP NOT NULL DEFAULT NOW());
CREATE TABLE vehicles (id BIGSERIAL PRIMARY KEY,user_id BIGINT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,brand VARCHAR(80) NOT NULL,model VARCHAR(80) NOT NULL,color VARCHAR(40),license_plate VARCHAR(32) UNIQUE NOT NULL,seats INT NOT NULL);
CREATE TABLE posts (id BIGSERIAL PRIMARY KEY,author_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,type VARCHAR(16) NOT NULL,origin VARCHAR(120) NOT NULL,destination VARCHAR(120) NOT NULL,departure_date DATE NOT NULL,departure_time TIME NOT NULL,seats_available INT,price NUMERIC(10,2) NOT NULL,description TEXT,status VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',created_at TIMESTAMP NOT NULL DEFAULT NOW());
CREATE TABLE conversations (id BIGSERIAL PRIMARY KEY,post_id BIGINT REFERENCES posts(id) ON DELETE SET NULL,created_at TIMESTAMP NOT NULL DEFAULT NOW());
CREATE TABLE conversation_participants (conversation_id BIGINT REFERENCES conversations(id) ON DELETE CASCADE,user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,PRIMARY KEY(conversation_id,user_id));
CREATE TABLE messages (id BIGSERIAL PRIMARY KEY,conversation_id BIGINT NOT NULL REFERENCES conversations(id) ON DELETE CASCADE,sender_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,content TEXT NOT NULL,sent_at TIMESTAMP NOT NULL DEFAULT NOW(),is_read BOOLEAN DEFAULT FALSE);
CREATE TABLE notifications (id BIGSERIAL PRIMARY KEY,recipient_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,type VARCHAR(32) NOT NULL,payload JSONB NOT NULL,is_read BOOLEAN DEFAULT FALSE,created_at TIMESTAMP NOT NULL DEFAULT NOW());
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_posts_route_date ON posts(departure_date,origin,destination);
CREATE INDEX idx_messages_conv_sender ON messages(conversation_id,sender_id);
INSERT INTO users (first_name,last_name,email,password_hash,phone,bio,role) VALUES
('Amine','Ben Salah','amine@example.com','$2a$10$abcdefghijklmnopqrstuv','+21611111111','Daily commuter','USER'),
('Leila','Trabelsi','leila@example.com','$2a$10$abcdefghijklmnopqrstuv','+21622222222','Weekend traveler','USER'),
('Youssef','Mansour','youssef@example.com','$2a$10$abcdefghijklmnopqrstuv','+21633333333','Student','USER'),
('Nour','Gharbi','nour@example.com','$2a$10$abcdefghijklmnopqrstuv','+21644444444','Software engineer','USER'),
('Admin','Root','admin@example.com','$2a$10$abcdefghijklmnopqrstuv','+21655555555','Platform admin','ADMIN');
INSERT INTO vehicles (user_id,brand,model,color,license_plate,seats) VALUES (1,'Peugeot','208','Red','123-TUN-1',4),(2,'Volkswagen','Golf','Blue','234-TUN-2',4),(4,'Toyota','Yaris','White','345-TUN-3',4);
INSERT INTO posts (author_id,type,origin,destination,departure_date,departure_time,seats_available,price,description,status) VALUES
(1,'OFFER','Tunis','Sousse','2026-04-20','08:00',3,15.00,'Morning commute','ACTIVE'),
(2,'REQUEST','Sfax','Tunis','2026-04-21','18:30',NULL,20.00,'Need a ride after work','ACTIVE'),
(1,'OFFER','Nabeul','Tunis','2026-04-22','07:45',2,10.00,'Two seats left','ACTIVE'),
(3,'REQUEST','Monastir','Sousse','2026-04-23','09:10',NULL,8.00,'Any ride welcome','ACTIVE'),
(4,'OFFER','Tunis','Bizerte','2026-04-24','06:30',3,12.00,'Daily trip','ACTIVE'),
(2,'OFFER','Sousse','Mahdia','2026-04-24','17:00',1,9.00,'Evening drive','FULL'),
(4,'REQUEST','Ariana','Tunis','2026-04-25','08:10',NULL,5.00,'Short hop','ACTIVE'),
(1,'OFFER','Tunis','Hammamet','2026-04-26','10:00',2,11.00,'Weekend trip','ACTIVE'),
(3,'REQUEST','Gabes','Sfax','2026-04-27','14:00',NULL,13.00,'Need lift','CANCELLED'),
(2,'OFFER','Sousse','Tunis','2026-04-28','19:00',3,14.00,'Return ride','COMPLETED');
INSERT INTO conversations (post_id) VALUES (1),(2);
INSERT INTO conversation_participants (conversation_id,user_id) VALUES (1,1),(1,3),(2,2),(2,4);
INSERT INTO messages (conversation_id,sender_id,content) VALUES
(1,1,'Hi, are you still interested in the Tunis to Sousse ride?'),
(1,3,'Yes, is there one seat available?'),
(1,1,'Yes, pickup at 7:45 near Bab Saadoun.'),
(1,3,'Perfect, see you there!'),
(2,2,'Hello, I can pick you up in Sfax at 18:15.'),
(2,4,'Great, can I bring a small suitcase?'),
(2,2,'Of course, no problem.'),
(2,4,'Thanks, booking confirmed.');
