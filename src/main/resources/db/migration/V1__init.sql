CREATE TABLE segment (
    id UUID PRIMARY KEY,
    point_start GEOGRAPHY(Point),
    point_end GEOGRAPHY(Point)
);

CREATE TABLE tour (
    id UUID PRIMARY KEY,
    track GEOGRAPHY(LineString)
);
