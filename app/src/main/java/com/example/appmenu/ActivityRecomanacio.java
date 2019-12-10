package com.example.appmenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityRecomanacio extends AppCompatActivity implements Adapter1.ItemClickListener, Adapter2.ItemClickListener, Adapter3.ItemClickListener {

    Adapter1 adapter1;
    Adapter2 adapter2;
    Adapter3 adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomanacio);

        Button boto;
        TextView buit1, buit2, buit3;

        boto = findViewById(R.id.main_boto);
        RecyclerView recyclerView1 = findViewById(R.id.main_llista1);
        RecyclerView recyclerView2 = findViewById(R.id.main_llista2);
        RecyclerView recyclerView3 = findViewById(R.id.main_llista3);
        buit1 = findViewById(R.id.main_buit1);
        buit2 = findViewById(R.id.main_buit2);
        buit3 = findViewById(R.id.main_buit3);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter1 = new Adapter1(this, GLOBALS.response.primers);
        adapter2 = new Adapter2(this, GLOBALS.response.segons);
        adapter3 = new Adapter3(this, GLOBALS.response.postres);

        adapter1.setClickListener(this);
        adapter2.setClickListener(this);
        adapter3.setClickListener(this);

        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);

        if(GLOBALS.response.primers.isEmpty()) buit1.setVisibility(View.VISIBLE);
        if(GLOBALS.response.segons.isEmpty()) buit2.setVisibility(View.VISIBLE);
        if(GLOBALS.response.postres.isEmpty()) buit3.setVisibility(View.VISIBLE);

        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GLOBALS.response.isEmpty()) Toast.makeText(getApplicationContext(),"No se rick", Toast.LENGTH_SHORT).show(); //easter egg 1
                else {
                    Intent i = new Intent(getApplicationContext(),ActivityFinal.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    public void onItemClick1(View view, int position) {
        mostrarDescPlat(position, GLOBALS.response.primers);
    }

    @Override
    public void onItemClick2(View view, int position) {
        mostrarDescPlat(position, GLOBALS.response.segons);
    }

    @Override
    public void onItemClick3(View view, int position) {
        mostrarDescPlat(position, GLOBALS.response.postres);
    }

    private void mostrarDescPlat(int position, ArrayList<Plat> array){
        if (array.size()< position){
            String msg = "";
            String ret = System.getProperty("line.separator");
            msg = msg.concat(array.get(position).desc + ret);
            msg = msg.concat("Tipus de cuina: " + array.get(position).tipus + ret);
            msg = msg.concat("Ingredients:" + ret);
            for(String s: array.get(position).ingredients)
                msg = msg.concat("-" + s + ret);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(msg);
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else Toast.makeText(getApplicationContext(), "Error en el plat", Toast.LENGTH_SHORT).show();
    }
}

