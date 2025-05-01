CREATE TABLE track_point (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    heart_rate_bpm INTEGER,
    tour_id UUID NOT NULL,
    time timestamp NOT NULL,
    CONSTRAINT fk_tour FOREIGN KEY(tour_id) REFERENCES tour(id) ON DELETE CASCADE
);
