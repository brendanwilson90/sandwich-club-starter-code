package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLogTags;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView descriptionText;
    private TextView placeOfOriginText;
    private TextView akaText;
    private TextView ingredientsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Set place of origin
        placeOfOriginText = findViewById(R.id.origin_tv);
        String origin = sandwich.getPlaceOfOrigin();
        if(origin != null && !origin.equals("")){
            placeOfOriginText.setText(origin);
        } else {
            placeOfOriginText.setText(R.string.origin_not_found);
        }

        //Set Also Known As
        akaText = findViewById(R.id.also_known_tv);
        List<String> akaList = sandwich.getAlsoKnownAs();
        if(akaList != null && !akaList.isEmpty()){
            for(String aka : akaList){
                akaText.append((aka) + "\n\n");
            }
            akaText.setText(akaText.getText().toString().trim());
        } else {
            akaText.setText(R.string.aka_not_found);
        }

        //Set Description
        descriptionText = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (description != null && description != ""){
            descriptionText.setText(description);
        } else {
            descriptionText.setText(R.string.description_not_found);
        }

        //Set ingredients
        ingredientsText = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList != null && !ingredientsList.isEmpty()){
            for(String ingredient : ingredientsList){
                ingredientsText.append((ingredient) + "\n\n");
            }
            ingredientsText.setText(ingredientsText.getText().toString().trim());
        } else {
            akaText.setText(R.string.ingredients_not_found);
        }
    }
}
