ALTER TABLE tour
    ADD COLUMN date TIMESTAMP
;

CREATE INDEX tour_date_index ON tour (date DESC);
