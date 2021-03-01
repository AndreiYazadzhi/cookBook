package com.example.cookbook.service;

import com.example.cookbook.model.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeService {
    void add(Recipe recipe);

    void fork(Recipe recipe, Recipe fork);

    void delete(Recipe recipe);

    Recipe update(Recipe recipe);

    List<Recipe> getAll();

    Optional<Recipe> get(Long id);

    List showHistoryById(Long id);
}
