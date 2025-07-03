-- Insert roles first (needed by app_user_role)
INSERT INTO role (id, label) VALUES
                                 (0, 'ADMIN'),
                                 (1, 'USER');

-- Insert users
INSERT INTO app_user (id, email, name, password) VALUES
                                                     (99, 'mathieugrattard1@gmail.com', 'Mathieu', '$2a$10$ub9k7BsXTvZ1SP/2pnZKuOVeuS52OhQk2bR.aeX3rxpuJU4qm7emW'),
                                                     (100, 'gaetan@gmail.com', 'Gaetan', '$2a$10$j/XNsoPCZORXbAhcvoCo3uWd.6Dqji5HJGMMK2/72N.KkBdpGBXMe'),
                                                     (101, 'toto@gmail.com', 'Toto', '$2a$10$7oDAukWHrvMDhgL2SC5W9OnhDTWZ.XRzxHfMZDMjnbhvEs7gvX7SC');

-- Insert into the join table
INSERT INTO app_user_role (users_id, role_id) VALUES
    (99, 0),(100, 1), (101, 1);

-- Insert todo item
INSERT INTO todo (id, created_date, deadline, description,  title, status, user_id, priority) VALUES
    (99, '2025-06-30', '2025-07-30', 'toto is toto !', 'toto', 'TODO', 99, 'LOW'),
    (100, '2025-06-29', '2025-07-01', 'tata is tata !', 'tata', 'IN_PROGRESS', 100, 'NORMAL'),
    (102, '2025-06-28', '2025-07-01', 'tata is tata !', 'tete', 'IN_PROGRESS', 99, 'HIGH'),
    (101, '2025-06-20', '2025-06-30', 'tata is tata !', 'titi', 'DONE', 100, 'NORMAL'),
    (103, '2025-06-19', NULL, 'New Todo !', 'TODOTODO', 'TODO', null, 'LOW');
