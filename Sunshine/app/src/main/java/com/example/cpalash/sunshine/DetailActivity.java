package com.example.cpalash.sunshine;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("DetailActivity", "created");
        setContentView(R.layout.fragment_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detailed_view_forecast, new DetailFragment())
                    .commit();
        }
    }

        public static class DetailFragment extends Fragment {
            public DetailFragment() {

            }


            @Override
            public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }

            @Override
            public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                inflater.inflate(R.menu.forecastfragment, menu);
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
                Intent intent = getActivity().getIntent();
                String forecast = intent.getStringExtra("weatherDetail");
                Log.v("Details", forecast);
                TextView details = (TextView)(rootView.findViewById(R.id.forecast_detail));
                details.setText(forecast);
                return rootView;
            }


        }

}

