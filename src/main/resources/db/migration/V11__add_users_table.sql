CREATE TABLE users (
    id UUID PRIMARY KEY,
    given_name varchar(255),
    family_name varchar(255)
);

ALTER TABLE segment
    ADD COLUMN user_id UUID,
    ADD CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(id)
;

ALTER TABLE tour
    ADD COLUMN user_id UUID,
    ADD CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(id)
;

ALTER TABLE segment_record
    ADD COLUMN user_id UUID,
    ADD CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(id)
;
