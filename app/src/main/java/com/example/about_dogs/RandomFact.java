package com.example.about_dogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RandomFact extends AppCompatActivity {

    ArrayList<String> FACTS;
    FactAsync fa = new FactAsync();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_fact);

        fa.execute("https://dog-api.kinduff.com/api/facts?number=10");
        try {
            FACTS = fa.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView listView= findViewById(R.id.listView);
        listView.setBackgroundResource(R.drawable.custom_shape);

        FactAdapter factAdapter = new FactAdapter();

        listView.setAdapter(factAdapter);

        Button button_home = findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent code for open new activity through intent.
                Intent intent = new Intent(RandomFact.this, MainActivity.class);
                startActivity(intent);

            }
        });

        Button button_refresh = findViewById(R.id.button_refresh_pink);
        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        });
    }

    class FactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return FACTS.size();
        }

        @Override
        public Object getItem(int i){
            return null;
        }

        @Override
        public long getItemId(int i){
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            view = getLayoutInflater().inflate(R.layout.content_fact, null);

            TextView textView_fact = view.findViewById(R.id.textView);
            textView_fact.setText(FACTS.get(i));
            return view;
        }
    }
}
