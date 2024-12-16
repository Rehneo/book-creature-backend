CREATE TYPE file_import_status AS ENUM ('SUCCESS', 'FAILED');

CREATE TABLE file_imports
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER                  NOT NULL REFERENCES users (id),
    status      file_import_status       NOT NULL,
    added_count INTEGER                  NOT NULL DEFAULT 0,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
)

