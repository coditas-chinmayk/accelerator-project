-- Create enum for expected_value
CREATE TYPE expected_value AS ENUM (
    'YES',
    'NO'
);

-- Alter expected_value column to enum type
ALTER TABLE question_condition
ALTER COLUMN expected_value
TYPE expected_value
USING expected_value::expected_value;
