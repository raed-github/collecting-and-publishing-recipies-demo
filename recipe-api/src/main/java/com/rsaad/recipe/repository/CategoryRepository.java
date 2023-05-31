package com.rsaad.recipe.repository;

import com.rsaad.recipe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategory(String recipeName);

}
