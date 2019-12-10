package com.example.appmenu;

import java.util.ArrayList;

public class API_get {
    public ArrayList<Plat> primers;
    public ArrayList<Plat> segons;
    public ArrayList<Plat> postres;

    public boolean isEmpty(){
        return (primers.isEmpty() && segons.isEmpty() && postres.isEmpty());
    }
}
