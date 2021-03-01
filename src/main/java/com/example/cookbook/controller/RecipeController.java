package com.example.cookbook.controller;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.RecipeRequestDto;
import com.example.cookbook.service.RecipeMapper;
import com.example.cookbook.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @GetMapping("/")
    public List<Recipe> getAll() {
        return recipeService.getAll();
    }

    @GetMapping("/history/{id}")
    public List<String> getHistory(@PathVariable Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List list = recipeService.showHistoryById(id);
        List<String> history = new ArrayList<>();
        for (Object object : list) {
            history.add(mapper.writeValueAsString(object));
        }
        return history;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return new ResponseEntity<>(recipeService.get(id).orElseThrow(() ->
                new NoSuchElementException("Recipe with id " + id + " is not found")),
                HttpStatus.OK);
    }

    @RequestMapping("/add")
    public ResponseEntity<Recipe> create(@RequestBody RecipeRequestDto dto) {
        Recipe recipe = recipeMapper.fromDto(dto);
        recipeService.add(recipe);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody RecipeRequestDto dto) {
        Optional<Recipe> recipeFromDb = recipeService.get(id);
        if (recipeFromDb.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Recipe recipe = recipeMapper.fromDto(dto);
        recipe.setId(id);
        recipeService.update(recipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PutMapping("/fork/{id}")
    public ResponseEntity<Recipe> fork(@PathVariable Long id, @RequestBody RecipeRequestDto dto) {
        Optional<Recipe> recipeFromDb = recipeService.get(id);
        if (recipeFromDb.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Recipe recipe = recipeMapper.fromDto(dto);
        recipe.setParent(recipeFromDb.get());
        recipeService.add(recipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        Recipe recipe = recipeService.get(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not exist with id :" + id));
        recipeService.delete(recipe);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
