package com.example.about_dogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

class ImageAdapter extends ArrayAdapter<Bitmap> {

    public ImageAdapter(Context context) {
        super(context, 0);
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Bitmap image = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_breed_result, parent, false);

        ImageView imageView_result = convertView.findViewById(R.id.imageView);

        // Populate the data into the template view using the data object
        imageView_result.setImageBitmap(image);

        // Return the completed view to render on screen
        return convertView;
    }
}
