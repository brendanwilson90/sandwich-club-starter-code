package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichDetailJson = new JSONObject(json);
            JSONObject names = sandwichDetailJson.getJSONObject("name");
            sandwich.setMainName(names.getString("mainName"));

            //get and parse the alsoKnownAs list into an arraylist
            JSONArray aka = names.getJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(createStringList(aka));

            //Set other properties
            sandwich.setDescription(sandwichDetailJson.getString("description"));
            sandwich.setPlaceOfOrigin(sandwichDetailJson.getString("placeOfOrigin"));
            sandwich.setImage(sandwichDetailJson.getString("image"));

            //Get and parse the ingredients into an arraylist
            JSONArray ingredientsArray = sandwichDetailJson.getJSONArray("ingredients");
            sandwich.setIngredients(createStringList(ingredientsArray));

        } catch (JSONException e){
            return null;
        }

        return sandwich;


    }
    //COMPLETED: Create String List parser method
    private static List<String> createStringList(JSONArray jsonArray) {
        if(jsonArray == null){
            return null;
        }
        List<String> returnList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++){
            returnList.add(jsonArray.optString(i));
        }
        return returnList;
    }
}
