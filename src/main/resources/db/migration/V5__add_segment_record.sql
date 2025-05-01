CREATE TABLE segment_record (
    id UUID PRIMARY KEY NOT NULL,
    tour_id UUID NOT NULL,
    segment_id UUID NOT NULL,
    speed_kmh DOUBLE PRECISION NOT NULL,
    duration_s INTEGER NOT NULL,
    time timestamp NOT NULL,
    CONSTRAINT fk_tour FOREIGN KEY(tour_id) REFERENCES tour(id) ON DELETE CASCADE,
    CONSTRAINT fk_segment FOREIGN KEY(segment_id) REFERENCES segment(id) ON DELETE CASCADE
);
