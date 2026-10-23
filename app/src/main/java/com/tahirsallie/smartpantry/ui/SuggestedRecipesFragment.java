package com.tahirsallie.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tahirsallie.smartpantry.R;
import com.tahirsallie.smartpantry.db.DatabaseHelper;
import com.tahirsallie.smartpantry.logic.RecipeMatcher;
import com.tahirsallie.smartpantry.model.PantryItem;
import com.tahirsallie.smartpantry.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesFragment extends Fragment
        implements RecipeAdapter.OnRecipeClickListener {

    private DatabaseHelper dbHelper;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView, headerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggested, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());
        recyclerView = view.findViewById(R.id.recycler_suggested);
        emptyView = view.findViewById(R.id.text_empty_suggested);
        headerView = view.findViewById(R.id.text_header);

        adapter = new RecipeAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSuggestions();
    }

    private void loadSuggestions() {
        List<Recipe> allRecipes = dbHelper.getAllRecipes();
        List<PantryItem> pantry = dbHelper.getAllPantryItems();

        // The strict-matching rule decides what appears here
        List<Recipe> suggested = RecipeMatcher.suggest(allRecipes, pantry);
        adapter.setRecipes(suggested);

        boolean empty = suggested.isEmpty();
        emptyView.setVisibility(empty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(empty ? View.GONE : View.VISIBLE);

        headerView.setText(empty
                ? getString(R.string.suggested_header_none)
                : suggested.size() + " of " + allRecipes.size() + " recipes you can make right now");
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(requireContext(), RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.getId());
        startActivity(intent);
    }
}
