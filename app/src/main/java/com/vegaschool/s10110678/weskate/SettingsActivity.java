package com.vegaschool.s10110678.weskate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity{

    Spinner landmarksSpinner;
    ToggleButton distanceType;
    Button saveButton;
    ImageView landmarkImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Distance pref set-up
        distanceType = (ToggleButton) findViewById(R.id.distance_toggle);

        // Spinner set-up
        landmarksSpinner = (Spinner) findViewById(R.id.landmarks_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.landmarks_array, android.R.layout.simple_spinner_item);

        landmarksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch(pos){
                    case 0:
                        landmarkImage.setImageResource(R.drawable.ic_half_pipe);
                        break;
                    case 1:
                        landmarkImage.setImageResource(R.drawable.ic_skatepark);
                        break;
                    case 2:
                        landmarkImage.setImageResource(R.drawable.ic_longboard);
                        break;
                    case 3:
                        landmarkImage.setImageResource(R.drawable.ic_rail);
                        break;
                    case 4:
                        landmarkImage.setImageResource(R.drawable.ic_jumps);
                        break;
                    case 5:
                        landmarkImage.setImageResource(R.drawable.ic_curbs);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        landmarkImage = (ImageView) findViewById(R.id.landmarkPref_iv);
        landmarkImage.setImageResource(R.drawable.ic_curbs);

        saveButton = (Button) findViewById(R.id.save_settings_btn);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        landmarksSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, HomeScreenActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

    }





    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // KILOMETERS PREF
        } else {
            // MILES PREF
        }
    }

}
