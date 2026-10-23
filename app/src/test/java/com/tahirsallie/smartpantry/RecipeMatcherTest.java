package com.tahirsallie.smartpantry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.tahirsallie.smartpantry.logic.IngredientNormalizer;
import com.tahirsallie.smartpantry.logic.RecipeMatcher;
import com.tahirsallie.smartpantry.model.PantryItem;
import com.tahirsallie.smartpantry.model.Recipe;
import com.tahirsallie.smartpantry.model.RecipeIngredient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeMatcherTest {

    private static PantryItem item(String name, double qty, String unit) {
        return new PantryItem(0, name, qty, unit, "");
    }

    private static Recipe recipe(String name, RecipeIngredient... ingredients) {
        Recipe r = new Recipe(1, name, "steps");
        r.setIngredients(new ArrayList<>(Arrays.asList(ingredients)));
        return r;
    }

    private static List<Recipe> suggest(Recipe r, PantryItem... pantry) {
        return RecipeMatcher.suggest(Arrays.asList(r), Arrays.asList(pantry));
    }

    @Test
    public void allIngredientsPresent_recipeIsSuggested() {
        Recipe r = recipe("Toastie",
                new RecipeIngredient("bread", 2, "pcs"),
                new RecipeIngredient("cheese", 40, "g"));
        assertEquals(1, suggest(r, item("Bread", 4, "pcs"), item("Cheese", 100, "g")).size());
    }

    @Test
    public void fourOfFiveIngredients_recipeIsExcluded() {
        Recipe r = recipe("Chicken and Rice",
                new RecipeIngredient("chicken", 300, "g"),
                new RecipeIngredient("rice", 1, "cup"),
                new RecipeIngredient("onion", 1, "pcs"),
                new RecipeIngredient("garlic", 2, "pcs"),
                new RecipeIngredient("oil", 1, "tbsp"));
        // oil is missing
        assertTrue(suggest(r,
                item("Chicken", 500, "g"), item("Rice", 2, "cup"),
                item("Onion", 2, "pcs"), item("Garlic", 4, "pcs")).isEmpty());
    }

    @Test
    public void insufficientQuantity_recipeIsExcluded() {
        Recipe r = recipe("Scrambled Eggs", new RecipeIngredient("egg", 3, "pcs"));
        assertTrue(suggest(r, item("Eggs", 2, "pcs")).isEmpty());
    }

    @Test
    public void exactQuantity_recipeIsSuggested() {
        Recipe r = recipe("Scrambled Eggs", new RecipeIngredient("egg", 3, "pcs"));
        assertEquals(1, suggest(r, item("Eggs", 3, "pcs")).size());
    }

    @Test
    public void singularVsPlural_stillMatches() {
        Recipe r = recipe("Salad", new RecipeIngredient("tomato", 2, "pcs"));
        assertEquals(1, suggest(r, item("Tomatoes", 2, "pcs")).size());
    }

    @Test
    public void caseAndWhitespace_areIgnored() {
        Recipe r = recipe("Salad", new RecipeIngredient("tomato", 1, "pcs"));
        assertEquals(1, suggest(r, item("  TOMATO ", 1, "pcs")).size());
    }

    @Test
    public void unitDifferences_areConverted() {
        Recipe r = recipe("Pancakes", new RecipeIngredient("flour", 150, "g"));
        assertEquals(1, suggest(r, item("Flour", 1, "kg")).size());   // 1 kg = 1000 g

        Recipe soup = recipe("Soup", new RecipeIngredient("milk", 250, "ml"));
        assertEquals(1, suggest(soup, item("Milk", 1, "l")).size());  // 1 l = 1000 ml
    }

    @Test
    public void duplicatePantryRows_areAddedTogether() {
        Recipe r = recipe("Scrambled Eggs", new RecipeIngredient("egg", 3, "pcs"));
        assertEquals(1, suggest(r, item("Egg", 2, "pcs"), item("Eggs", 2, "pcs")).size());
    }

    @Test
    public void incompatibleUnitFamilies_doNotMatch() {
        Recipe r = recipe("Toastie", new RecipeIngredient("cheese", 40, "g"));
        assertTrue(suggest(r, item("Cheese", 2, "pcs")).isEmpty());   // grams vs pieces
    }

    @Test
    public void emptyPantry_nothingSuggested() {
        Recipe r = recipe("Toastie", new RecipeIngredient("bread", 2, "pcs"));
        assertTrue(RecipeMatcher.suggest(Arrays.asList(r), new ArrayList<PantryItem>()).isEmpty());
    }

    @Test
    public void recipeWithNoIngredients_isNeverSuggested() {
        Recipe r = recipe("Empty");
        assertFalse(RecipeMatcher.canMake(r, RecipeMatcher.buildStock(new ArrayList<>())));
    }

    @Test
    public void normalizeName_handlesCommonPlurals() {
        assertEquals("tomato", IngredientNormalizer.normalizeName("Tomatoes"));
        assertEquals("potato", IngredientNormalizer.normalizeName("potatoes"));
        assertEquals("berry", IngredientNormalizer.normalizeName("Berries"));
        assertEquals("egg", IngredientNormalizer.normalizeName("eggs"));
        assertEquals("cheese", IngredientNormalizer.normalizeName("cheese"));
        assertEquals("", IngredientNormalizer.normalizeName(null));
    }
}
