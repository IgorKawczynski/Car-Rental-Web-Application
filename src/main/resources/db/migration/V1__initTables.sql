CREATE TYPE public.car_body_type AS ENUM('COUPE', 'SEDAN', 'SUV', 'STATION_WAGON', 'HATCHBACK', 'CABRIOLET');
CREATE TYPE public.car_type_of_fuel AS ENUM('BENZINE', 'DIESEL', 'ELECTRIC', 'LPG');
CREATE TYPE public.reservation_status AS ENUM('FREE', 'RESERVED', 'TAKEN');

CREATE SEQUENCE IF NOT EXISTS id_seq
AS BIGINT
INCREMENT BY 1
START WITH 10000;

CREATE TABLE IF NOT EXISTS public.CARS(
    ID BIGINT NOT NULL,
    brand VARCHAR(20),
    model VARCHAR(20),
    engine_capacity numeric(2,1),
    body_type public.car_body_type NOT NULL,
    type_of_fuel public.car_type_of_fuel NOT NULL,
    new_car_cost BIGINT,
    production_year int NOT NULL,
    CONSTRAINT cars_id_pkey PRIMARY KEY(ID)
    );

CREATE TABLE IF NOT EXISTS public.USERS(
    ID BIGINT NOT NULL DEFAULT nextval('id_seq')PRIMARY KEY,
    login VARCHAR(28) NOT NULL,
    password VARCHAR(80),
    first_name VARCHAR(60),
    second_name VARCHAR(60),
    phone_number VARCHAR(9),
    email VARCHAR(80) NOT NULL,
    pesel VARCHAR(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.RESERVATIONS(
    ID BIGINT NOT NULL DEFAULT nextval('id_seq')PRIMARY KEY,
    user_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    date_start DATE, --if car is free then that column cannot be NOT NULL
    date_end DATE, --if car is free then that column cannot be NOT NULL
    cost numeric(7,2) NOT NULL,
    payment_in_advance numeric(7,2) NOT NULL,
    CONSTRAINT user_id_pkey FOREIGN KEY(user_id) REFERENCES users(ID),
    CONSTRAINT car_id_pkey FOREIGN KEY(car_id) REFERENCES cars(ID)
    );
