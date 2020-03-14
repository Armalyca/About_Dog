package com.example.about_dogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChooseBreed extends AppCompatActivity {
    ArrayList<String> BREEDS;
    ArrayAdapter arrayAdapter;
    ListView listView;
    String breed_chosen;
    BreedAsync ba = new BreedAsync();
    private GridView slotGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_breed);

        ba.execute("https://dog.ceo/api/breeds/list/all");
        try {
            BREEDS = ba.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.listView_choose);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        arrayAdapter = new ArrayAdapter(ChooseBreed.this,R.layout.content_breed_search,BREEDS);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast toastie = Toast.makeText(ChooseBreed.this, BREEDS.get(i), Toast.LENGTH_SHORT);
                toastie.setGravity(Gravity.TOP, 0, 0);
                toastie.show();

                breed_chosen = BREEDS.get(i);
            }
        });

        Button button_home_v2 = findViewById(R.id.button_home2);
        button_home_v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ChooseBreed.this, MainActivity.class);
                startActivity(intention);
            }
        });

        Button button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breed_chosen == null) {
                    Toast toastie = Toast.makeText(ChooseBreed.this, "Please select a breed", Toast.LENGTH_SHORT);
                    toastie.setGravity(Gravity.TOP, 0, 0);
                    toastie.show();

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                } else {
                    Intent intention = new Intent(ChooseBreed.this, ShowBreed.class);
                    intention.putExtra("BREED_CHOSEN", breed_chosen);
                    startActivity(intention);
                }
            }
        });
    }
}
