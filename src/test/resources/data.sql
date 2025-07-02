-- Insert roles first (needed by app_user_role)
INSERT INTO role (id, label) VALUES
                                 (0, 'ADMIN'),
                                 (1, 'USER');

-- Insert users
INSERT INTO app_user (id, email, name, password) VALUES
                                                     (1, 'mathieugrattard1@gmail.com', 'Mathieu', '$2a$10$ub9k7BsXTvZ1SP/2pnZKuOVeuS52OhQk2bR.aeX3rxpuJU4qm7emW'),
                                                     (2, 'gaetan@gmail.com', 'Gaetan', '$2a$10$j/XNsoPCZORXbAhcvoCo3uWd.6Dqji5HJGMMK2/72N.KkBdpGBXMe');

-- Insert into the join table
INSERT INTO app_user_role (users_id, role_id) VALUES
    (2, 1);

-- Insert todo item
INSERT INTO todo (id, created_date, description, is_done, title, status, user_id, public_id) VALUES
    (1, '2025-06-30', 'There is a new todo !', 'TODO', 'Create todo', 'TODO', 1, 1000);
