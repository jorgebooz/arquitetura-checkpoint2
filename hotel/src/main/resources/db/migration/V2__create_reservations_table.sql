CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    guest_name VARCHAR(100) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_value DECIMAL(10,2),

    CONSTRAINT fk_reservation_room
        FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,

    CONSTRAINT check_dates_valid
        CHECK (check_in_date < check_out_date)
);

CREATE INDEX idx_reservation_room_dates
ON reservations(room_id, check_in_date, check_out_date);

CREATE INDEX idx_reservation_status
ON reservations(status);