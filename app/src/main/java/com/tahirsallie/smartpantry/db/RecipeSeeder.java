package com.tahirsallie.smartpantry.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/** Pre-loads the recipe collection the first time the database is created. */
public final class RecipeSeeder {

    private RecipeSeeder() {}

    public static void seed(SQLiteDatabase db) {
        addRecipe(db, "Scrambled Eggs",
                "1. Crack the eggs into a bowl and whisk with the salt.\n"
                        + "2. Melt the butter in a pan over low heat.\n"
                        + "3. Pour in the eggs and stir gently until just set.",
                new Object[][]{{"egg", 3, "pcs"}, {"butter", 10, "g"}, {"salt", 0.5, "tsp"}});

        addRecipe(db, "Cheese Toastie",
                "1. Butter the outside of both bread slices.\n"
                        + "2. Place the cheese between the slices.\n"
                        + "3. Toast in a pan for 2-3 minutes per side until golden.",
                new Object[][]{{"bread", 2, "pcs"}, {"cheese", 40, "g"}, {"butter", 10, "g"}});

        addRecipe(db, "Tomato Pasta",
                "1. Boil the pasta in salted water until tender, then drain.\n"
                        + "2. Fry the chopped garlic in oil for 1 minute.\n"
                        + "3. Add the chopped tomatoes and simmer for 10 minutes.\n"
                        + "4. Toss the pasta through the sauce.",
                new Object[][]{{"pasta", 200, "g"}, {"tomato", 4, "pcs"}, {"garlic", 2, "pcs"},
                        {"oil", 2, "tbsp"}, {"salt", 1, "tsp"}});

        addRecipe(db, "Egg Fried Rice",
                "1. Cook the rice, then let it cool.\n"
                        + "2. Fry the chopped onion in oil until soft.\n"
                        + "3. Push the onion aside, scramble the eggs in the pan.\n"
                        + "4. Add the rice and salt and fry for 3-4 minutes.",
                new Object[][]{{"rice", 1, "cup"}, {"egg", 2, "pcs"}, {"oil", 1, "tbsp"},
                        {"onion", 1, "pcs"}, {"salt", 0.5, "tsp"}});

        addRecipe(db, "Pancakes",
                "1. Whisk the flour, sugar, egg and milk into a smooth batter.\n"
                        + "2. Melt a little butter in a pan.\n"
                        + "3. Pour in small rounds of batter, cook until bubbles form, then flip.",
                new Object[][]{{"flour", 150, "g"}, {"milk", 200, "ml"}, {"egg", 1, "pcs"},
                        {"sugar", 1, "tbsp"}, {"butter", 20, "g"}});

        addRecipe(db, "Banana Oat Porridge",
                "1. Heat the oats and milk in a pot, stirring, for 5 minutes.\n"
                        + "2. Slice the banana on top.\n"
                        + "3. Drizzle with honey.",
                new Object[][]{{"oat", 50, "g"}, {"milk", 250, "ml"}, {"banana", 1, "pcs"},
                        {"honey", 1, "tbsp"}});

        addRecipe(db, "Mashed Potatoes",
                "1. Peel and cut the potatoes, then boil until soft.\n"
                        + "2. Drain and mash with the butter and warm milk.\n"
                        + "3. Season with salt.",
                new Object[][]{{"potato", 4, "pcs"}, {"butter", 30, "g"}, {"milk", 60, "ml"},
                        {"salt", 1, "tsp"}});

        addRecipe(db, "Garlic Butter Pasta",
                "1. Boil the pasta in salted water, then drain.\n"
                        + "2. Melt the butter and gently fry the crushed garlic.\n"
                        + "3. Toss the pasta in the garlic butter.",
                new Object[][]{{"pasta", 200, "g"}, {"garlic", 3, "pcs"}, {"butter", 30, "g"},
                        {"salt", 1, "tsp"}});

        addRecipe(db, "Tuna Sandwich",
                "1. Drain the tuna and mix with the mayonnaise.\n"
                        + "2. Spread onto one slice of bread.\n"
                        + "3. Top with the second slice and cut in half.",
                new Object[][]{{"tuna", 100, "g"}, {"bread", 2, "pcs"}, {"mayonnaise", 1, "tbsp"}});

        addRecipe(db, "Veggie Omelette",
                "1. Dice the onion and tomato and fry in oil for 3 minutes.\n"
                        + "2. Whisk the eggs with salt and pour over the vegetables.\n"
                        + "3. Cook until set, fold and serve.",
                new Object[][]{{"egg", 3, "pcs"}, {"onion", 1, "pcs"}, {"tomato", 1, "pcs"},
                        {"oil", 1, "tbsp"}, {"salt", 0.5, "tsp"}});

        addRecipe(db, "Chicken and Rice",
                "1. Brown the chicken pieces in oil.\n"
                        + "2. Add the chopped onion and garlic and cook for 3 minutes.\n"
                        + "3. Add the rice, salt and two cups of water.\n"
                        + "4. Cover and simmer for 20 minutes.",
                new Object[][]{{"chicken", 300, "g"}, {"rice", 1, "cup"}, {"onion", 1, "pcs"},
                        {"garlic", 2, "pcs"}, {"oil", 1, "tbsp"}, {"salt", 1, "tsp"}});

        addRecipe(db, "Potato Wedges",
                "1. Heat the oven to 200 C.\n"
                        + "2. Cut the potatoes into wedges and toss with oil and salt.\n"
                        + "3. Bake for 30-35 minutes, turning once.",
                new Object[][]{{"potato", 3, "pcs"}, {"oil", 2, "tbsp"}, {"salt", 1, "tsp"}});

        addRecipe(db, "Chickpea Salad",
                "1. Drain the chickpeas.\n"
                        + "2. Chop the tomatoes and onion.\n"
                        + "3. Combine and dress with lemon juice and oil.",
                new Object[][]{{"chickpea", 200, "g"}, {"tomato", 2, "pcs"}, {"onion", 1, "pcs"},
                        {"lemon", 1, "pcs"}, {"oil", 1, "tbsp"}});

        addRecipe(db, "Lentil Soup",
                "1. Fry the chopped onion, carrot and garlic for 5 minutes.\n"
                        + "2. Add the lentils, salt and 3 cups of water.\n"
                        + "3. Simmer for 25 minutes until soft.",
                new Object[][]{{"lentil", 150, "g"}, {"carrot", 2, "pcs"}, {"onion", 1, "pcs"},
                        {"garlic", 2, "pcs"}, {"salt", 1, "tsp"}});

        addRecipe(db, "French Toast",
                "1. Whisk the eggs, milk and sugar together.\n"
                        + "2. Dip each bread slice into the mixture.\n"
                        + "3. Fry in butter until golden on both sides.",
                new Object[][]{{"bread", 4, "pcs"}, {"egg", 2, "pcs"}, {"milk", 100, "ml"},
                        {"butter", 10, "g"}, {"sugar", 1, "tbsp"}});

        addRecipe(db, "Honey Lemon Drink",
                "1. Squeeze the lemon into a mug.\n"
                        + "2. Add the honey and top up with hot water.\n"
                        + "3. Stir until dissolved.",
                new Object[][]{{"lemon", 1, "pcs"}, {"honey", 1, "tbsp"}});

        addRecipe(db, "Cheesy Beans on Toast",
                "1. Warm the beans in a small pot.\n"
                        + "2. Toast the bread.\n"
                        + "3. Spoon the beans over the toast and top with grated cheese.",
                new Object[][]{{"bean", 200, "g"}, {"bread", 2, "pcs"}, {"cheese", 30, "g"}});

        addRecipe(db, "Mushroom Rice",
                "1. Fry the chopped onion and mushrooms in butter.\n"
                        + "2. Add the rice, salt and two cups of water.\n"
                        + "3. Cover and simmer for 18 minutes.",
                new Object[][]{{"rice", 1, "cup"}, {"mushroom", 150, "g"}, {"onion", 1, "pcs"},
                        {"butter", 20, "g"}, {"salt", 0.5, "tsp"}});

        addRecipe(db, "Avocado Toast",
                "1. Toast the bread.\n"
                        + "2. Mash the avocado with salt.\n"
                        + "3. Spread on the toast.",
                new Object[][]{{"avocado", 1, "pcs"}, {"bread", 2, "pcs"}, {"salt", 0.5, "tsp"}});

        addRecipe(db, "Cheesy Baked Potato",
                "1. Pierce the potatoes and bake at 200 C for 50 minutes.\n"
                        + "2. Cut open and fluff the inside with butter.\n"
                        + "3. Top with grated cheese and return to the oven for 5 minutes.",
                new Object[][]{{"potato", 2, "pcs"}, {"cheese", 50, "g"}, {"butter", 10, "g"}});
    }

    private static void addRecipe(SQLiteDatabase db, String name, String steps,
                                  Object[][] ingredients) {
        ContentValues rv = new ContentValues();
        rv.put(DatabaseHelper.R_NAME, name);
        rv.put(DatabaseHelper.R_STEPS, steps);
        long recipeId = db.insert(DatabaseHelper.T_RECIPES, null, rv);

        for (Object[] row : ingredients) {
            ContentValues iv = new ContentValues();
            iv.put(DatabaseHelper.RI_RECIPE_ID, recipeId);
            iv.put(DatabaseHelper.RI_NAME, (String) row[0]);
            iv.put(DatabaseHelper.RI_QTY, ((Number) row[1]).doubleValue());
            iv.put(DatabaseHelper.RI_UNIT, (String) row[2]);
            db.insert(DatabaseHelper.T_RING, null, iv);
        }
    }
}
