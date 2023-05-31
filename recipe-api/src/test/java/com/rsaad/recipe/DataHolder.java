
package com.rsaad.recipe;

import com.rsaad.recipe.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataHolder {
    public static List<Recipe> returnRecipe(){
        Recipe recipe = Recipe.builder()
                .name("Tomato salad with ricotta, broad beans and salsa verde")
                .yield(1)
                .difficultyLevel("easy")
                .preparationTime("25 min")
                .serves("4")
                .cookingTime("lunch")
                .categories(returnCategorySet())
                .ingredients(returnIngredientSet())
                .directions(returnDirectionSet())
                .build();
        return Arrays.asList(recipe);
    }

    public static Set<Ingredient> returnIngredientSet(){
        Set<Ingredient> ingredientSet = new HashSet<Ingredient>();

        Ingredient ingredient = Ingredient.builder()
                .ingredient("heirloom tomatoes of different shapes and sizes, sliced and cut in different ways")
                .quantity(650)
                .unit("gram")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("red onion, finely sliced")
                .quantity(0.5)
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("podded broad beans")
                .quantity(100)
                .unit("gram")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("ricotta")
                .quantity(50)
                .unit("gram")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("flat-leaf parsley, leaves picked")
                .quantity(1)
                .unit("pack")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("chervil, leaves only")
                .quantity(1)
                .unit("pack")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("garlic, finely chopped")
                .quantity(1)
                .unit("clove")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("Dijon mustard")
                .quantity(1)
                .unit("tsp")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("small capers, drained")
                .quantity(2)
                .unit("tsp")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("extra virgin olive oil")
                .quantity(150)
                .unit("ml")
                .build();
        ingredientSet.add(ingredient);

        ingredient = Ingredient.builder()
                .ingredient("good quality sherry vinegar")
                .quantity(1)
                .unit("tsp")
                .build();
        ingredientSet.add(ingredient);

        return ingredientSet;
    }

    public static Set<Category> returnCategorySet(){
        Category category = Category.builder()
                .id(1L)
                .category("vegetarian")
                .build();

        Set<Category> categorySet = new HashSet<Category>();
        categorySet.add(category);
        return categorySet;
    }

    public static Set<Direction> returnDirectionSet(){
        Set<Direction> directionSet = new HashSet<Direction>();
        Direction direction = Direction.builder()
                .step("toss the sliced tomatoes and onion in a bowl with a bit of salt and set aside")
                .build();
        directionSet.add(direction);

        direction = Direction.builder()
                .step("Finely chop the herbs for the salsa verde. Pound the garlic, Dijon mustard and capers using a pestle and mortar and add to the herbs. Add seasoning and the oil. Add the vinegar, little by little, tasting as you go, until the sauce has the right amount of acidity – it needs to be punchy without losing the grassy, fresh flavour of the herbs.")
                .build();
        directionSet.add(direction);

        direction = Direction.builder()
                .step("Bring a pan of water to the boil and plunge the broad beans in for a couple of mins, then drain, cool in cold water and peel away the shells.")
                .build();
        directionSet.add(direction);

        direction = Direction.builder()
                .step("Divide the tomatoes and onions between plates or spread out on a platter. Dot the ricotta around and spoon over the salsa verde. Scatter with broad beans and serve.")
                .build();
        directionSet.add(direction);

        return directionSet;
    }

    public static List<Category> returnCategoryList() {
        return Arrays.asList(Category.builder()
                .id(1L).category("vegitarian")
                .build());
    }
}
