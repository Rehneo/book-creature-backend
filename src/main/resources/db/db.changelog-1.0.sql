BEGIN;

CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE TYPE book_creature_type AS ENUM ('HOBBIT', 'ELF', 'HUMAN');

CREATE TYPE request_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(128) NOT NULL UNIQUE,
    password_hash VARCHAR(128) NOT NULL,
    role user_role NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE coordinates (
    id BIGSERIAL PRIMARY KEY,
    x FLOAT,
    Y BIGINT NOT NULL
);

CREATE TABLE rings (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL CHECK (name <> ''),
    power BIGINT NOT NULL CHECK (power > 0),
    editable BOOLEAN NOT NULL DEFAULT TRUE,
    owner INTEGER NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE magic_cities(
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL CHECK (name <> ''),
    area BIGINT NOT NULL CHECK (area > 0),
    population BIGINT NOT NULL CHECK (population > 0),
    establishment_date TIMESTAMP WITH TIME ZONE,
    governor book_creature_type NOT NULL,
    capital BOOLEAN NOT NULL,
    population_density INTEGER CHECK (population_density IS NULL OR population_density > 0),
    editable BOOLEAN NOT NULL DEFAULT TRUE,
    owner INTEGER NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE book_creatures(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL CHECK (name <> ''),
    coordinates_id BIGINT NOT NULL REFERENCES coordinates(id) ON DELETE RESTRICT,
    creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
    age BIGINT NOT NULL CHECK(age > 0),
    type book_creature_type,
    location_id INTEGER NOT NULL REFERENCES magic_cities(id) ON DELETE RESTRICT,
    attack_level INTEGER CHECK (attack_level IS NULL OR attack_level > 0),
    defense_level BIGINT CHECK (defense_level IS NULL OR defense_level > 0),
    ring_id BIGINT NOT NULL REFERENCES rings(id) ON DELETE RESTRICT,
    editable BOOLEAN NOT NULL DEFAULT TRUE,
    owner INTEGER NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE
);


CREATE TABLE admin_requests (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    request_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    approved_by INTEGER REFERENCES users(id),
    approval_date TIMESTAMP WITH TIME ZONE,
    status request_status NOT NULL DEFAULT 'PENDING'
);


COMMIT;