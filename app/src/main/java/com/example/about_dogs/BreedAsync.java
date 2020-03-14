package com.example.about_dogs;

import android.os.AsyncTask;

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
import java.util.Iterator;
import java.util.Vector;

public class BreedAsync extends AsyncTask<String, Integer, ArrayList<String>> {

    private ArrayList<String> breeds_name = new ArrayList<>();

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
                JSONObject message = jsonObj.getJSONObject("message");
                Iterator<String> keys = message.keys();
                while (keys.hasNext()) {
                    breeds_name.add(keys.next()); //I add every name of breed to the ArrayList of String
                }
                return breeds_name;

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
