CREATE TABLE live_segment (
    user_id UUID PRIMARY KEY,
    segment_id UUID,
    start_timestamp TIMESTAMP,
    last_timestamp TIMESTAMP,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_segment_id FOREIGN KEY (segment_id) REFERENCES segment(id) ON DELETE CASCADE
);
