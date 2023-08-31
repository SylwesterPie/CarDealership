CREATE TABLE car_dealership_user
(
    user_id   SERIAL       NOT NULL,
    user_name VARCHAR(64)  NOT NULL,
    email     VARCHAR(64)  NOT NULL,
    password  VARCHAR(256) NOT NULL,
    active    BOOLEAN      NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE car_dealership_role
(
    role_id SERIAL      NOT NULL,
    role    VARCHAR(64) NOT NULL,
    primary key (role_id)
);

CREATE TABLE car_dealership_user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_car_dealership_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES car_dealership_user (user_id),
    CONSTRAINT fk_car_dealership_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES car_dealership_role (role_id)
);