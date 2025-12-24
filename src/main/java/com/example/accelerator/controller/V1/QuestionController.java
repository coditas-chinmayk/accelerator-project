package com.example.accelerator.controller.V1;

import com.example.accelerator.dto.QuestionRequestDTO;
import com.example.accelerator.dto.QuestionResponseDTO;
import com.example.accelerator.service.QuestionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@Tag(
        name = "Question Management",
        description = "APIs for managing assessment questions")
public class QuestionController {

    @Autowired
    private QuestionServiceImpl questionService;


    @PostMapping
    @Operation(
            summary = "Create a new question",
            description = "Creates a new question under an assessment")
    public ResponseEntity<QuestionResponseDTO> createQuestion(
            @Valid
            @RequestBody QuestionRequestDTO requestDTO){

        QuestionResponseDTO responseDTO=questionService.createQuestion(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PutMapping("/{questionId}")
    @Operation(
            summary = "Update an existing question",
            description = "Updates question text, type, order, or configuration")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionRequestDTO requestDTO) {

        QuestionResponseDTO response =
                questionService.updateQuestion(questionId, requestDTO);

        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a question",
            description = "Soft deletes a question by marking it inactive")
    public void deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
    }


    @GetMapping("/assessment/{assessmentId}")
    @Operation(
            summary = "Get all questions for an assessment",
            description = "Fetches all active questions of an assessment ordered by position")
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestionsByAssessment(
            @PathVariable Long assessmentId) {

        List<QuestionResponseDTO> questions =
                questionService.getAllQuestionsByAssessment(assessmentId);

        return ResponseEntity.ok(questions);
    }


    @GetMapping("/{questionId}")
    @Operation(
            summary = "Get question by ID",
            description = "Fetches a single active question by its ID")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(
            @PathVariable Long questionId) {

        QuestionResponseDTO response =
                questionService.getQuestionById(questionId);

        return ResponseEntity.ok(response);
    }



}
