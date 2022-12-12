package com.vegaschool.s10110678.weskate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreenActivity extends AppCompatActivity {

    Button favouritesButton;
    Button searchButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        favouritesButton = (Button) findViewById(R.id.favourite_places_btn);
        searchButton = (Button) findViewById(R.id.search_btn);
        settingsButton = (Button) findViewById(R.id.setting_btn);

        favouritesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, FavouritePlacesActivity.class);
                HomeScreenActivity.this.startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, SettingsActivity.class);
                HomeScreenActivity.this.startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, MapsActivity2.class);
                HomeScreenActivity.this.startActivity(intent);
            }
        });
    }
}