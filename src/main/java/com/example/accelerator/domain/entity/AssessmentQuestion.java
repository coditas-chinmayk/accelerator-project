package com.example.accelerator.domain.entity;

import com.example.accelerator.domain.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@Table(name = "assessment_questions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @Column(nullable = false, columnDefinition = "TEXT", name = "question_text")
    private String questionText;

    // Enum Injected :
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "question_type")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private QuestionType questionType;

    private Boolean isRequired;

    @Column(nullable = false, name = "order_index")
    private Integer orderIndex;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "config")
    private Map<String, Object> config;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // mappings injected :
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();

    }
}
