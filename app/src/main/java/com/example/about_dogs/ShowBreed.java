package com.example.about_dogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ShowBreed extends AppCompatActivity {

    String breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_breed);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                breed = null;
            } else {
                breed = extras.getString("BREED_CHOSEN"); //I get back the breed I passed with the intent
            }
        } else {
            breed = (String) savedInstanceState.getSerializable("BREED_CHOSEN");
        }

        //I link the objects on the xml file here, and set the textView
        TextView textView_fact = findViewById(R.id.textView_title2);
        textView_fact.setText("Random pictures of "+breed+"s");
        ListView listViewImages = findViewById(R.id.listImages);

        //I declare and execute the class I use to get every pictures from the JSON Object the API give us for the breed we research
        BitmapDownloaderTask downloader = new BitmapDownloaderTask(this, listViewImages);
        downloader.execute(breed);

        //Button to go back to the home page
        Button button_home_v2 = findViewById(R.id.button_home2);
        button_home_v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ShowBreed.this, MainActivity.class);
                startActivity(intention);
            }
        });

        //Button to go back to the activity precedent (to choose a breed)
        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ShowBreed.this, ChooseBreed.class);
                startActivity(intention);
            }
        });

        //Button to refresh the activity, the pictures being random there is a big possibility to see new picture (if there is enough pictures for that breed)
        Button button_refresh = findViewById(R.id.button_refresh);
        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                getIntent().putExtra("BREED_CHOSEN", breed); //I want to refresh to see the same breed so I send the same breed as an extra with the intent
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

}
