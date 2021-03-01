package com.example.cookbook.controller;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.service.RecipeService;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InjectData {
    private final RecipeService recipeService;

    @PostConstruct
    private void inject() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Hamburger");
        recipe.setDescription("Pork with bread");
        recipeService.add(recipe);
        Recipe childRecipe = new Recipe();
        childRecipe.setTitle("Cheeseburger");
        childRecipe.setDescription("Pork with bread and cheese");
        recipeService.fork(childRecipe, recipe);
    }
}
