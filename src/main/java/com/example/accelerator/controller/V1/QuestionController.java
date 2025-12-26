package com.example.accelerator.controller.V1;

import com.example.accelerator.dto.QuestionRequestDto;
import com.example.accelerator.dto.QuestionResponseDto;
import com.example.accelerator.service.QuestionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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


    @PostMapping("/create")
    @Operation(
            summary = "Create a new question",
            description = "Creates a new question under an assessment")
    public ResponseEntity<QuestionResponseDto> createQuestion(
            @Valid
            @RequestBody QuestionRequestDto requestDTO){

        QuestionResponseDto responseDTO=questionService.createQuestion(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PutMapping("/update/{questionId}")
    @Operation(
            summary = "Update an existing question",
            description = "Updates question text, type, order, or configuration")
    public ResponseEntity<QuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionRequestDto requestDTO) {

        QuestionResponseDto response =
                questionService.updateQuestion(questionId, requestDTO);

        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/delete/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a question",
            description = "Soft deletes a question by marking it inactive")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Long questionId) {

        return ResponseEntity.ok(questionService.deleteQuestion(questionId));
    }


    @GetMapping("/allAssessment/{assessmentId}")
    @Operation(
            summary = "Get all questions for an assessment",
            description = "Fetches all active questions of an assessment ordered by position")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsByAssessment(
            @PathVariable Long assessmentId) {

        List<QuestionResponseDto> questions =
                questionService.getAllQuestionsByAssessment(assessmentId);

        return ResponseEntity.ok(questions);
    }


    @GetMapping("/byId/{questionId}")
    @Operation(
            summary = "Get question by ID",
            description = "Fetches a single active question by its ID")
    public ResponseEntity<QuestionResponseDto> getQuestionById(
            @PathVariable Long questionId) {

        QuestionResponseDto response =
                questionService.getQuestionById(questionId);

        return ResponseEntity.ok(response);
    }



}
