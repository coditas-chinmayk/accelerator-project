package com.example.accelerator.service;

import com.example.accelerator.domain.entity.Assessment;
import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.example.accelerator.domain.entity.Category;
import com.example.accelerator.domain.entity.User;
import com.example.accelerator.domain.enums.AssessmentStatus;
import com.example.accelerator.dto.AssessmentCreateRequestDto;
import com.example.accelerator.dto.AssessmentUpdateRequestDto;
import com.example.accelerator.dto.AssessmentDetailResponseDto;
import com.example.accelerator.dto.AssessmentListItemDto;
import com.example.accelerator.dto.QuestionResponseDto;
import com.example.accelerator.repository.AssessmentRepository;
import com.example.accelerator.repository.CategoryRepository;
import com.example.accelerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public AssessmentDetailResponseDto createAssessment(AssessmentCreateRequestDto request) {
        User currentUser = getCurrentUser();

        Category category = categoryRepository.findByNameIgnoreCase(request.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategory()));

        Assessment assessment = Assessment.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .status(AssessmentStatus.DRAFT)
                .createdBy(currentUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Assessment saved = assessmentRepository.save(assessment);

        return toAssessmentDetailDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentListItemDto> getAllAssessments() {
        return assessmentRepository.findAll().stream()
                .map(this::toAssessmentListItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AssessmentDetailResponseDto getAssessmentById(Long id) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found with id: " + id));

        return toAssessmentDetailDto(assessment);
    }

    @Override
    public AssessmentDetailResponseDto updateAssessment(Long id, AssessmentUpdateRequestDto request) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found with id: " + id));

        User currentUser = getCurrentUser();

        Category category = categoryRepository.findByNameIgnoreCase(request.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategory()));

        assessment.setName(request.getName());
        assessment.setDescription(request.getDescription());
        assessment.setCategory(category);
        assessment.setUpdatedBy(currentUser);
        assessment.setUpdatedAt(LocalDateTime.now());

        Assessment updated = assessmentRepository.save(assessment);

        return toAssessmentDetailDto(updated);
    }

    private AssessmentListItemDto toAssessmentListItemDto(Assessment assessment) {
        return AssessmentListItemDto.builder()
                .id(assessment.getId())
                .name(assessment.getName())
                .category(assessment.getCategory().getName())
                .status(assessment.getStatus().name())
                .updatedAt(assessment.getUpdatedAt())
                .createdByName(assessment.getCreatedBy().getName())
                .build();
    }

    private static AssessmentDetailResponseDto toAssessmentDetailDto(Assessment assessment) {
        List<QuestionResponseDto> questionDtos = assessment.getQuestions() != null
                ? assessment.getQuestions().stream()
                .map(AssessmentServiceImpl::toQuestionResponseDto)
                .toList()
                : List.of();

        return AssessmentDetailResponseDto.builder()
                .id(assessment.getId())
                .name(assessment.getName())
                .description(assessment.getDescription())
                .category(assessment.getCategory().getName())
                .status(assessment.getStatus().name())
                .createdById(assessment.getCreatedBy().getId())
                .createdByName(assessment.getCreatedBy().getName())
                .updatedById(assessment.getUpdatedBy() != null ? assessment.getUpdatedBy().getId() : null)
                .updatedByName(assessment.getUpdatedBy() != null ? assessment.getUpdatedBy().getName() : null)
                .questions(questionDtos)
                .createdAt(assessment.getCreatedAt())
                .updatedAt(assessment.getUpdatedAt())
                .build();
    }

    private static QuestionResponseDto toQuestionResponseDto(AssessmentQuestion assessmentQuestion) {
        return QuestionResponseDto.builder()
                .id(assessmentQuestion.getId())
                .questionText(assessmentQuestion.getQuestionText())
                .questionType(assessmentQuestion.getQuestionType())
                .isRequired(assessmentQuestion.getIsRequired())
                .orderIndex(assessmentQuestion.getOrderIndex())
                .config(assessmentQuestion.getConfig())
                .isActive(assessmentQuestion.getIsActive())
                .build();
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }
}