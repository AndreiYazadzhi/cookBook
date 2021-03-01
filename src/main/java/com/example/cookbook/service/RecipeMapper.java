package com.example.cookbook.service;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.RecipeRequestDto;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public Recipe fromDto(RecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setDescription(dto.getDescription());
        recipe.setTitle(dto.getTitle());
        recipe.setText(dto.getText());
        return recipe;
    }
}
