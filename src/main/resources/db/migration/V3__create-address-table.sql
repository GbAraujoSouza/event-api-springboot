CREATE TABLE address
(
    id       UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    state    VARCHAR(255) NOT NULL,
    city     VARCHAR(255) NOT NULL,
    event_id UUID,
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);