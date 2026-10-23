package com.tahirsallie.smartpantry.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tahirsallie.smartpantry.R;
import com.tahirsallie.smartpantry.db.DatabaseHelper;
import com.tahirsallie.smartpantry.model.Recipe;
import com.tahirsallie.smartpantry.model.RecipeIngredient;

public class RecipeDetailActivity extends AppCompatActivity {

    /** Intent extra key carrying the id of the recipe to display. */
    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // back arrow
        }

        long recipeId = getIntent().getLongExtra(EXTRA_RECIPE_ID, -1);
        Recipe recipe = (recipeId == -1) ? null : new DatabaseHelper(this).getRecipe(recipeId);

        if (recipe == null) {                       // bad or missing id: fail gracefully
            Toast.makeText(this, R.string.recipe_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(recipe.getName());

        ((TextView) findViewById(R.id.text_detail_name)).setText(recipe.getName());
        ((TextView) findViewById(R.id.text_detail_ingredients))
                .setText(buildIngredientText(recipe));
        ((TextView) findViewById(R.id.text_detail_steps)).setText(recipe.getSteps());
    }

    /** One bullet per ingredient, e.g. "• 3 egg" or "• 0.5 tsp salt". */
    private String buildIngredientText(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ing : recipe.getIngredients()) {
            double q = ing.getQuantity();
            String qty = (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);

            sb.append("\u2022 ").append(qty);
            // "pcs" reads awkwardly ("3 pcs egg"), so leave it out for counted items
            if (!"pcs".equalsIgnoreCase(ing.getUnit())) {
                sb.append(" ").append(ing.getUnit());
            }
            sb.append(" ").append(ing.getName()).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
