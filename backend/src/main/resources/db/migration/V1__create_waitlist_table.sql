-- V1__create_waitlist_table.sql
CREATE TABLE waitlist_entry (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL
);