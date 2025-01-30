INSERT INTO users (id, username, password, email, first_name, last_name)
VALUES (1, 'Username1', 'Password1', 'email1@gmail.com', 'FirstName1', 'LastName1');

INSERT INTO users (id, username, password, email, first_name, last_name)
VALUES (2, 'Username2', 'Password2', 'email2@gmail.com', 'FirstName2', 'LastName2');

INSERT INTO roles (id, name)
VALUES (1, 'USER');

INSERT INTO roles (id, name)
VALUES (2, 'ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2);

INSERT INTO users_roles (user_id, role_id)
VALUES (2, 1);

INSERT INTO projects (id, name, description, start_date, end_date, status)
VALUES (1, 'Project1', 'Project1 description', '2025-01-01', '2025-01-07', 'INITIATED');

INSERT INTO projects (id, name, description, start_date, end_date, status)
VALUES (2, 'Project2', 'Project2 description', '2025-01-02', '2025-01-08', 'IN_PROGRESS');

INSERT INTO projects (id, name, description, start_date, end_date, status)
VALUES (3, 'Project3', 'Project3 description', '2025-01-03', '2025-01-09', 'COMPLETED');

INSERT INTO projects_users (project_id, user_id)
VALUES (1, 1);

INSERT INTO projects_users (project_id, user_id)
VALUES (2, 1);

INSERT INTO projects_users (project_id, user_id)
VALUES (2, 2);

INSERT INTO projects_users (project_id, user_id)
VALUES (3, 1);