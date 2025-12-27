package com.example.accelerator.controller.V1;

import com.example.accelerator.dto.ApiResponseDto;
import com.example.accelerator.dto.CategoryCreateRequestDto;
import com.example.accelerator.dto.CategoryResponseDto;
import com.example.accelerator.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category Management", description = "APIs for managing assessment categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create a new category")
    public ApiResponseDto<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryCreateRequestDto request) {

        CategoryResponseDto response = categoryService.createCategory(request);
        return ApiResponseDto.ok("Category created successfully", response);
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ApiResponseDto<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ApiResponseDto.ok("Categories retrieved successfully", categories);
    }
}