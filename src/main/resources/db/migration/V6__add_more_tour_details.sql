ALTER TABLE tour
    ADD COLUMN name VARCHAR(200) NOT NULL DEFAULT 'Tour',
    ADD COLUMN average_speed_kmh FLOAT NOT NULL DEFAULT 0.0,
    ADD COLUMN average_heart_rate_bpm INTEGER
;
