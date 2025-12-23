-- Assessment table :
CREATE TABLE assessment (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     description TEXT,
     category_id BIGINT,
     status assessment_status NOT NULL DEFAULT 'DRAFT',  -- enum
     created_by BIGINT,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_assessment_category   -- fk's
         FOREIGN KEY (category_id) REFERENCES category(id),

     CONSTRAINT fk_assessment_user
         FOREIGN KEY (created_by) REFERENCES users(id)
);


-- Assessment questions :
CREATE TABLE assessment_questions (
    id BIGSERIAL PRIMARY KEY,
    assessment_id BIGINT NOT NULL,
    question_text TEXT NOT NULL,
    question_type question_type NOT NULL,  -- ENUM
    is_required BOOLEAN,
    order_index INT NOT NULL,
    config JSON,
    is_active BOOLEAN DEFAULT true,


    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_questions_assessment   -- fk's
         FOREIGN KEY (assessment_id) REFERENCES assessment(id),

    CONSTRAINT uq_assessment_question_order
         UNIQUE (assessment_id, order_index)
);



-- Question condition :
CREATE TABLE question_condition (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL,
    depends_on_question_id BIGINT NOT NULL,
    operator condition_operator NOT NULL, -- enum

    CONSTRAINT fk_condition_question  -- fks
        FOREIGN KEY (question_id) REFERENCES assessment_questions(id),

    CONSTRAINT fk_condition_depends_on
        FOREIGN KEY (depends_on_question_id) REFERENCES assessment_questions(id)
);