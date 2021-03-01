package com.example.cookbook.model;

import lombok.Data;

@Data
public class RecipeRequestDto {
    private String title;
    private String description;
    private String text;
}
