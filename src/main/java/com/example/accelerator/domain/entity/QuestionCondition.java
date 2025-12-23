package com.example.accelerator.domain.entity;

import com.example.accelerator.domain.enums.ConditionOperator;
import jakarta.persistence.*;

@Entity
@Table(name = "question_condition")
public class QuestionCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "question_id")
    private AssessmentQuestion question;

    @ManyToOne(optional = false)
    @JoinColumn(name = "depends_on_question_id")
    private AssessmentQuestion dependsOnQuestion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConditionOperator operator;   // EQUALS

    @Column(nullable = false)
    private String expectedValue;
}
