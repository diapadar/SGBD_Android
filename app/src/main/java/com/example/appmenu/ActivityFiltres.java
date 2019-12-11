package com.example.appmenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityFiltres extends AppCompatActivity {

    Button boto;
    ChipGroup filterChipGroup, tipusChipGroup;
    ProgressBar spinner;

    API api;

    String[] array_tipus = {"Italiana", "Japonesa", "Espanyola", "Mexicana", "Xinesa"};
    String[] array_filtres = {"Gluten", "Carn", "Peix", "Ous", "Verdura", "Fruits secs", "Làctics"};
    ArrayList<String> filtres = new ArrayList<>();
    ArrayList<String> tipus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtres);

        spinner = findViewById(R.id.filtres_spinner);
        boto = findViewById(R.id.filtres_boto);
        tipusChipGroup = findViewById(R.id.filter_chip_group1);
        filterChipGroup = findViewById(R.id.filter_chip_group2);

        createAPI();

        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

        createTipusChips();
        createFilterChips();

    }

    private void createAPI(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GLOBALS.url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(API.class);
    }

    private void createTipusChips(){
        for (String array_filtre : array_tipus) {
            Chip chip = new Chip(this);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setText(array_filtre);
            chip.setChipStrokeColorResource(R.color.black);
            chip.setChipStrokeWidth(2);

            tipusChipGroup.addView(chip);

            chip.setOnCheckedChangeListener(new Chip.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        tipus.add(compoundButton.getText().toString());
                    } else {
                        tipus.remove(compoundButton.getText().toString());
                    }
                }
            });
        }
    }

    private void createFilterChips(){
        for (String array_filtre : array_filtres) {
            Chip chip = new Chip(this);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setText(array_filtre);
            chip.setChipStrokeColorResource(R.color.black);
            chip.setChipStrokeWidth(2);

            filterChipGroup.addView(chip);

            chip.setOnCheckedChangeListener(new Chip.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        filtres.add(compoundButton.getText().toString());
                    } else {
                        filtres.remove(compoundButton.getText().toString());
                    }
                }
            });
        }
    }

    private void sendRequest(){
        API_send send = new API_send();
        send.estilsCuina = tipus;
        send.restriccions = filtres;

        Call<API_get> call = api.getPlats(send);
        call.enqueue(new Callback<API_get>() {
            @Override
            public void onResponse(Call<API_get> call, Response<API_get> response) {
                if(response.isSuccessful()) {
                    Log.i("RETROFIT_ok", response.body().toString());
                    GLOBALS.response = response.body();
                    startActivity(new Intent(getApplicationContext(), ActivityRecomanacio.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Error de la resposta", Toast.LENGTH_SHORT).show();
                    Log.e("RETROFIT_err", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<API_get> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de connexio", Toast.LENGTH_SHORT).show();
                Log.e("RETROFIT_fail", t.toString());
            }
        });
    }

    /*private void getPlats(){
        spinner.setVisibility(View.VISIBLE);
        boto.setVisibility(View.GONE);

        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            final JSONObject jsonBody = new JSONObject();
            final JSONArray array = new JSONArray();
            for(String s : tipus){
                array.put(s);
            }
            final JSONArray array2 = new JSONArray();
            for(String s : filtres){
                array2.put(s);
            }
            jsonBody.put("estilsCuina", tipus);
            jsonBody.put("restriccions", filtres);


            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, GLOBALS.url, jsonBody , new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray plats = response.getJSONArray("primers");
                                for (int i = 0; i < plats.length(); i++) {
                                    GLOBALS.primers.add(Plat.fromJson(plats.getJSONObject(i)));
                                }
                                plats = response.getJSONArray("segons");
                                for (int i = 0; i < plats.length(); i++) {
                                    GLOBALS.segons.add(Plat.fromJson(plats.getJSONObject(i)));
                                }
                                plats = response.getJSONArray("postres");
                                for (int i = 0; i < plats.length(); i++) {
                                    GLOBALS.postres.add(Plat.fromJson(plats.getJSONObject(i)));
                                }
                                spinner.setVisibility(View.GONE);
                                boto.setVisibility(View.VISIBLE);
                                startActivity(new Intent(getApplicationContext(), ActivityRecomanacio.class));
                            } catch (JSONException e) {
                                Log.e("VOLLEY_parse_response", e.toString());
                                spinner.setVisibility(View.GONE);
                                boto.setVisibility(View.VISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY_err_response", error.toString());
                            spinner.setVisibility(View.GONE);
                            boto.setVisibility(View.VISIBLE);
                        }
                    }){

                @Override
                public byte[] getBody() {
                    return jsonBody.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(jsObjRequest);

        } catch (JSONException e) {
            Log.e("VOLLEY_parse_body_json", e.toString());
            spinner.setVisibility(View.GONE);
            boto.setVisibility(View.VISIBLE);
        }

    }*/

}
