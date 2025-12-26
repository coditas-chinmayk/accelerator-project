package com.example.accelerator.service;

import com.example.accelerator.domain.entity.Assessment;
import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.example.accelerator.domain.entity.QuestionCondition;
import com.example.accelerator.dto.*;
import com.example.accelerator.exception.ResourceNotFoundException;
import com.example.accelerator.repository.AssessmentQuestionRepository;
import com.example.accelerator.repository.AssessmentRepository;
import com.example.accelerator.dto.PreviewQuestionVisibilityDto;
import com.example.accelerator.repository.QuestionConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreviewService {
    private final AssessmentRepository assessmentRepository;
    private final AssessmentQuestionRepository questionRepository;
    private final QuestionConditionRepository conditionRepository;

    public PreviewResponseDto getPreview(Long assessmentId) {

        // Validate assessment exists
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found"));

        //  Fetch questions (ACTIVE + ORDERED)
        List<AssessmentQuestion> questions =
                questionRepository.findByAssessmentIdAndIsActiveTrueOrderByOrderIndexAsc(assessmentId);

        // Fetch all conditions for these questions
        List<QuestionCondition> conditions =
                conditionRepository.findByQuestion_Assessment_Id(assessmentId);

        // Convert to map -> For fast lookup
        Map<Long, QuestionCondition> conditionMap = conditions.stream()
                .collect(Collectors.toMap(
                        cond -> cond.getQuestion().getId(),
                        cond -> cond
                ));

        //  Build DTO list
        List<PreviewQuestionDto> previewQuestions = new ArrayList<>();

        for (AssessmentQuestion q : questions) {
            PreviewQuestionDto dto = new PreviewQuestionDto();
            dto.setQuestionId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setQuestionType(q.getQuestionType().name());
            dto.setRequired(q.getIsRequired());
            dto.setOrder(q.getOrderIndex());

            //  INITIAL VISIBILITY RULE
            boolean isConditional = conditionMap.containsKey(q.getId());
            dto.setVisible(!isConditional); // hide conditional questions at start

            //  OPTIONS COME FROM CONFIG JSONB
            Map<String, Object> config = q.getConfig();
            if (config != null && config.containsKey("options")) {
                dto.setOptions((List<String>) config.get("options"));
            } else {
                dto.setOptions(Collections.emptyList());
            }

            previewQuestions.add(dto);
        }

        //  Build Response Object
        PreviewResponseDto response = new PreviewResponseDto();
        response.setAssessmentId(assessment.getId());
        response.setAssessmentName(assessment.getName());
        response.setQuestions(previewQuestions);

        return response;
    }

    public PreviewEvaluationResponseDto evaluate(Long assessmentId, PreviewEvaluationRequestDto request) {

        Map<Long, Object> answers = request.getAnswers();

        List<AssessmentQuestion> questions =
                questionRepository.findByAssessmentIdAndIsActiveTrueOrderByOrderIndexAsc(assessmentId);

        List<QuestionCondition> conditions =
                conditionRepository.findByQuestion_Assessment_Id(assessmentId);

        // Build fast lookup: question -> condition
        Map<Long, QuestionCondition> conditionMap = conditions.stream()
                .collect(Collectors.toMap(
                        qc -> qc.getQuestion().getId(),
                        qc -> qc
                ));

        List<PreviewQuestionVisibilityDto> visibilityList = new ArrayList<>();

        for (AssessmentQuestion q : questions) {

            boolean visible = true;

            // If no condition exists → always visible
            if (conditionMap.containsKey(q.getId())) {

                QuestionCondition cond = conditionMap.get(q.getId());
                Long dependsOnId = cond.getDependsOnQuestion().getId();
                Object expected = cond.getExpectedValue().name(); // ENUM -> String
                Object actualAnswer = answers.get(dependsOnId);

                visible = evaluateVisibility(actualAnswer, expected.toString());
            }

            visibilityList.add(new PreviewQuestionVisibilityDto(q.getId(), visible));
        }

        return new PreviewEvaluationResponseDto(visibilityList);
    }


    /**
     * RULE ENGINE
     */
    private boolean evaluateVisibility(Object actual, String expected) {

        if (actual == null) return false; // parent unanswered → child hidden

        // MULTI CHOICE
        if (actual instanceof List<?>) {
            return ((List<?>) actual).contains(expected);
        }

        // TEXT ANY
        if ("ANY".equals(expected)) {
            return !actual.toString().isBlank();
        }

        // SINGLE CHOICE
        return expected.equalsIgnoreCase(actual.toString());
    }


}

