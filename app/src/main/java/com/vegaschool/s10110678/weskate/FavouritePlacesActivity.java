package com.vegaschool.s10110678.weskate;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FavouritePlacesActivity extends AppCompatActivity implements View.OnClickListener{

    public ListView favouritePlacesList;
    public List<LocationClass> listStorage = new ArrayList<LocationClass>();
    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favourite_locations);

        listStorage.add(new LocationClass(6,7,"TestLocation1"));
        listStorage.add(new LocationClass(-6,7,"TestLocation2"));
        listStorage.add(new LocationClass(6,-7,"TestLocation3"));
        listStorage.add(new LocationClass(-6,-7,"TestLocation3"));

        adapter = new ArrayAdapter(this, R.layout.list_view_layout, listStorage);

        favouritePlacesList = (ListView) findViewById(R.id.favourite_locations_lv);

        favouritePlacesList.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

    }
}
