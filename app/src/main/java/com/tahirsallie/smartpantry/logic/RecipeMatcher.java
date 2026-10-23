package com.tahirsallie.smartpantry.logic;

import com.tahirsallie.smartpantry.model.PantryItem;
import com.tahirsallie.smartpantry.model.Recipe;
import com.tahirsallie.smartpantry.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The strict-matching rule: a recipe is suggested only if EVERY ingredient is available. */
public final class RecipeMatcher {

    private static final double EPSILON = 1e-9;   // guards against floating-point rounding

    private RecipeMatcher() {}

    /** Total stock per ingredient (in base units). Duplicate pantry rows are added together. */
    public static Map<String, Double> buildStock(List<PantryItem> pantry) {
        Map<String, Double> stock = new HashMap<>();
        for (PantryItem p : pantry) {
            String key = IngredientNormalizer.key(p.getName(), p.getUnit());
            double base = IngredientNormalizer.toBase(p.getQuantity(), p.getUnit());
            Double current = stock.get(key);
            stock.put(key, (current == null ? 0.0 : current) + base);
        }
        return stock;
    }

    /** How many of the recipe's ingredients are missing or insufficient. */
    public static int countMissing(Recipe recipe, Map<String, Double> stock) {
        int missing = 0;
        for (RecipeIngredient need : recipe.getIngredients()) {
            double required = IngredientNormalizer.toBase(need.getQuantity(), need.getUnit());
            Double have = stock.get(IngredientNormalizer.key(need.getName(), need.getUnit()));
            if (have == null || have + EPSILON < required) {
                missing++;
            }
        }
        return missing;
    }

    /** STRICT: true only when nothing at all is missing (and the recipe has ingredients). */
    public static boolean canMake(Recipe recipe, Map<String, Double> stock) {
        return !recipe.getIngredients().isEmpty() && countMissing(recipe, stock) == 0;
    }

    /** Returns only the recipes the user can make right now. */
    public static List<Recipe> suggest(List<Recipe> allRecipes, List<PantryItem> pantry) {
        Map<String, Double> stock = buildStock(pantry);
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : allRecipes) {
            if (canMake(r, stock)) {
                result.add(r);
            }
        }
        return result;
    }
}
