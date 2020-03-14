package com.example.about_dogs;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class FactAsync extends AsyncTask <String, Integer, ArrayList<String>>{

    private ArrayList<String> facts = new ArrayList<>();

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        String url = params[0];
        URL url2 = null;
        try {
            url2 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection(); // I use the url passed as a parameter
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String s = readStream(in);

            try {

                JSONObject jsonObj = new JSONObject(s);
                JSONArray items = jsonObj.getJSONArray("facts"); 
                for (int i = 0; i < items.length(); i++) {
                    String link = items.getString(i); //I take every facts in the jsonObject
                    facts.add(link); //I add every facts to the ArrayList of String
                }
                return facts;

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
