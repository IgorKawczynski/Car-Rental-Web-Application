DROP table IF EXISTS public.RESERVATIONS;

CREATE TABLE IF NOT EXISTS public.RESERVATIONS(
    ID BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    date_start date, --if car is free then that column cannot be NOT NULL
    date_end date, --if car is free then that column cannot be NOT NULL
    cost numeric(7,2) NOT NULL,
    payment_in_advance numeric(7,2) NOT NULL,
    CONSTRAINT reservations_id_pkey PRIMARY KEY(ID),
    CONSTRAINT user_id_pkey FOREIGN KEY(user_id) REFERENCES users(ID),
    CONSTRAINT car_id_pkey FOREIGN KEY(car_id) REFERENCES cars(ID)
    );