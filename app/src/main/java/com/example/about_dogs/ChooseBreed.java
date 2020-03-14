package com.example.about_dogs;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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

        //I execute the class I use to get every breeds' name from the JSON Object the API gave at the url I pass as parameter
        ba.execute("https://dog.ceo/api/breeds/list/all");
        try {
            BREEDS = ba.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //I link the objects on the xml file here
        listView = findViewById(R.id.listView_choose);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        //As I only deal with an ArrayList, I use the already existing ArrayAdapter
        arrayAdapter = new ArrayAdapter(ChooseBreed.this,R.layout.content_breed_search,BREEDS);
        listView.setAdapter(arrayAdapter); //I set the adapter on the listView I have

        //I declare what to do if there is a click on the item of my list (my CheckedTextViews)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //I will show a Toast with the name of the breed selected
                Toast toastie = Toast.makeText(ChooseBreed.this, BREEDS.get(i), Toast.LENGTH_SHORT);
                toastie.setGravity(Gravity.TOP, 0, 0);
                toastie.show();

                breed_chosen = BREEDS.get(i); //I save the name of the breed selected
            }
        });

        //Button to go back to the home page
        Button button_home_v2 = findViewById(R.id.button_home2);
        button_home_v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ChooseBreed.this, MainActivity.class);
                startActivity(intention);
            }
        });

        //Button to go to the following activity (to see the random pictures of the breed selected)
        Button button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breed_chosen == null) { //I check if a breed was selected or not
                    //If none breed were selected, I send back to the same activity and display a toast to ask to select a breed
                    Toast toastie = Toast.makeText(ChooseBreed.this, "Please select a breed", Toast.LENGTH_SHORT);
                    toastie.setGravity(Gravity.TOP, 0, 0);
                    toastie.show();

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                } else {
                    //If a breed is selected, I go to the activity to show the pictures of this breed, and I pass the name of the breed as an extra of the intent
                    Intent intention = new Intent(ChooseBreed.this, ShowBreed.class);
                    intention.putExtra("BREED_CHOSEN", breed_chosen);
                    startActivity(intention);
                }
            }
        });
    }
}
