package com.example.accelerator.service;

import com.example.accelerator.dto.CategoryCreateRequestDto;
import com.example.accelerator.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    public CategoryResponseDto createCategory(CategoryCreateRequestDto request);
    public List<CategoryResponseDto> getAllCategories();
}
