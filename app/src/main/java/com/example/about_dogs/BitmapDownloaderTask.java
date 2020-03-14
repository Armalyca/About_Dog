package com.example.about_dogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

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

public class BitmapDownloaderTask extends AsyncTask<String, Integer, Bitmap> {

    private Vector<String> links = new Vector<>();
    private static ArrayList<Bitmap> arrayOfImages = new ArrayList<Bitmap>();
    private Context ctxt;
    private ListView listView;
    // Create the adapter to convert the array to views
    ImageAdapter adapter;


    public BitmapDownloaderTask (Context ctxt, ListView listView)
    {
        this.ctxt = ctxt;
        this.listView = listView;
        adapter = new ImageAdapter(ctxt);
        this.listView.setAdapter(adapter);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String breed = params[0];
        URL url = null;
        try {
            url = new URL("https://dog.ceo/api/breed/"+breed+"/images/random/5");

            Log.i("Mathilde", url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String s = readStream(in);

            try {

                JSONObject jsonObj = new JSONObject(s);
                JSONArray items = jsonObj.getJSONArray("message");
                for (int i = 0; i < items.length(); i++) {
                    String link = items.getString(i);
                    links.add(link);
                }

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

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctxt);

        for (String url : links) {
            // Request an image response from the provided URL.
            Log.i("URL", url);
            ImageRequest imageRequest = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap response) {
                            // Display the first 500 characters of the response string.
                            Log.i("JFL", "success");
                            adapter.add(response);
                        }

                    }, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("JFL", "That didn't work!");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(imageRequest);
        }
    }


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
}
