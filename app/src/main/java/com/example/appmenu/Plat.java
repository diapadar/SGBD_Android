package com.example.appmenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Plat {
    String nom, desc, tipus, url;
    ArrayList<String> ingredients;

    public Plat(String nom, String desc, String tipus, ArrayList<String> ingredients, String url) {
        this.nom = nom;
        this.desc = desc;
        this.tipus = tipus;
        this.ingredients = ingredients;
        this.url = url;
    }

    static public Plat fromJson(JSONObject response) throws JSONException {
        String nom = response.getString("nom");
        String desc = response.getString("descripcio");
        String tipus = response.getString("tipus");
        String url = response.getString("url");
        ArrayList<String> ingredients = new ArrayList<>();
        JSONArray array = response.getJSONArray("ingredients");
        for(int i=0; i<array.length(); i++){
            ingredients.add(array.getString(i));
        }
        return new Plat(nom, desc, tipus, ingredients, url);

    }
}
