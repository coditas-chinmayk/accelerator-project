package com.example.accelerator.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "assessment_questions")
public class AssessmentQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(nullable = false)
    private String questionType;   // SINGLE_CHOICE, TEXT, etc.

    private Boolean isRequired;

    @Column(nullable = false)
    private Integer orderIndex;

    @Column(columnDefinition = "json")
    private String config;   // options, validations

    private Boolean isActive = true;

    private Instant createdAt;
    private Instant updatedAt;
}
