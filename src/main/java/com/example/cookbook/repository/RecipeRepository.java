package com.example.cookbook.repository;

import com.example.cookbook.model.Recipe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> getRecipesByParent(Recipe parent);
}
