package com.example.about_dogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ShowBreed extends AppCompatActivity {

    String breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_breed);

        TextView textView_fact = findViewById(R.id.textView_title2);



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                breed = null;
            } else {
                breed = extras.getString("BREED_CHOSEN");
            }
        } else {
            breed = (String) savedInstanceState.getSerializable("BREED_CHOSEN");
        }

        textView_fact.setText("Random pictures of "+breed+"s");
        ListView listViewImages = findViewById(R.id.listImages);


        BitmapDownloaderTask downloader = new BitmapDownloaderTask(this, listViewImages);
        downloader.execute(breed);


        Button button_home_v2 = findViewById(R.id.button_home2);
        button_home_v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ShowBreed.this, MainActivity.class);
                startActivity(intention);
            }
        });

        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ShowBreed.this, ChooseBreed.class);
                startActivity(intention);
            }
        });

        Button button_refresh = findViewById(R.id.button_refresh);
        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                getIntent().putExtra("BREED_CHOSEN", breed);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

}
