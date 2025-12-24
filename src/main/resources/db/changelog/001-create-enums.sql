CREATE TYPE assessment_status AS ENUM (
    'DRAFT',
    'FINAL'
);


CREATE TYPE question_type AS ENUM (
    'SINGLE_CHOICE',
    'MULTI_CHOICE',
    'TEXT'
);


CREATE TYPE condition_operator AS ENUM (
    'EQUALS'
);