package com.example.accelerator.domain.entity;

import com.example.accelerator.domain.enums.ConditionOperator;
import com.example.accelerator.domain.enums.ExpectedValue;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "question_condition")
@Data
public class QuestionCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "question_id")
    private AssessmentQuestion question;

    @ManyToOne(optional = false)
    @JoinColumn(name = "depends_on_question_id")
    private AssessmentQuestion dependsOnQuestion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "operator")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ConditionOperator operator;   // EQUALS

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "expected_value")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ExpectedValue expectedValue;
}
