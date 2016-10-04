package com.example.cpalash.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ImageAdapter extends BaseAdapter {
    private Context mcontext;
    private List<String> images;

    public ImageAdapter(Context mcontext) {
        this.mcontext = mcontext;
        try {
            images = new MovieDatabaseHelper().execute().get();
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
    public View getView(int position, View view, ViewGroup parent) {
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
        Picasso picasso = Picasso.with(mcontext);
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso.load(images.get(position)).fit().centerCrop().into(imageView);
        Log.v("Image Adapter", images.get(position));
        return imageView;
    }
}
