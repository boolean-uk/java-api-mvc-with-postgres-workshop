CREATE TABLE IF NOT EXISTS Stocks (
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(1024) NOT NULL,
    description VARCHAR(255) NOT NULL
)