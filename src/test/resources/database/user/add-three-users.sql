INSERT INTO users (id, username, password, email, first_name, last_name)
VALUES (1, 'user1', 'user1password', 'user1@gmail.com', 'User1', 'User1lastname');

INSERT INTO users (id, username, password, email, first_name, last_name)
VALUES (2, 'user2', 'user2password', 'user2@gmail.com', 'User2', 'User2lastname');

INSERT INTO users (id, username, password, email, first_name, last_name)
VALUES (3, 'user3', 'user3password', 'user3@gmail.com', 'User3', 'User3lastname');

INSERT INTO roles (id, name)
VALUES (1, 'USER');

INSERT INTO roles (id, name)
VALUES (2, 'ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (2, 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (3, 2);