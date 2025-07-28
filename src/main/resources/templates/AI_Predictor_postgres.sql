-- Drop existing tables and sequence
DROP TABLE IF EXISTS predictions CASCADE;
DROP TABLE IF EXISTS app_details CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP SEQUENCE IF EXISTS user_seq;


CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    weight NUMERIC(10, 2),
    height NUMERIC(10, 2),
    training_habits VARCHAR(500)
);



CREATE TABLE predictions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    prediction_data TEXT,
    prediction_result TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE app_details (
    id SERIAL PRIMARY KEY,
    app_name VARCHAR(255),
    version VARCHAR(50),
    release_date TIMESTAMP
);

ALTER TABLE predictions
ALTER COLUMN prediction_result TYPE TEXT;

COMMIT;

