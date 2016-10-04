package com.example.cpalash.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ImageAdapter extends BaseAdapter {
    private Context mcontext;
    private Map<String, Movie> movies;
    private List<String> images;

    public ImageAdapter(Context mcontext) {
        this.mcontext = mcontext;
        try {
            movies = new MovieDatabaseHelper().execute().get();
            images = new ArrayList<>(movies.keySet());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ImageView imageView;
        if (view == null) {
            int height = parent.getHeight()/2;
            imageView = new ImageView(mcontext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

        } else {
            imageView = (ImageView) view;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ImageAdapter", "clickListener called");
                Intent movieDetailIntent = new Intent(v.getContext(), MovieDetail.class);
                String url = images.get(position);
                Movie movieClicked = movies.get(url);
                movieDetailIntent.putExtra("details", movieClicked.getName());
                movieDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(movieDetailIntent);
//
            }
        });
        Picasso picasso = Picasso.with(mcontext);
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso.load(images.get(position)).fit().centerCrop().into(imageView);
        Log.v("Image Adapter", images.get(position));
        return imageView;
    }
}
