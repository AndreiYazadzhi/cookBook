package com.example.cookbook.service;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.repository.RecipeRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    @PersistenceContext
    private EntityManager em;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void add(Recipe recipe) {
        recipe.setDate(LocalDate.now());
        recipeRepository.save(recipe);
    }

    @Override
    public void fork(Recipe recipe, Recipe fork) {
        recipe.setParent(fork);
        recipe.setDate(LocalDate.now());
        recipeRepository.save(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepository.getRecipesByParent(recipe).stream().forEach(r -> r.setParent(null));
        recipeRepository.delete(recipe);
    }

    @Override
    public Recipe update(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getAll() {
        return recipeRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    @Override
    public Optional<Recipe> get(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public List showHistoryById(Long id) {
        return AuditReaderFactory.get(em).createQuery()
                .forRevisionsOfEntity(Recipe.class, true, false)
                .add(AuditEntity.id().eq(id))
                .getResultList();
    }
}
