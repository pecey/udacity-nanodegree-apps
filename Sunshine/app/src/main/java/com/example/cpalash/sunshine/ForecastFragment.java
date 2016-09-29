package com.example.cpalash.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ForecastFragment extends Fragment {
    private ArrayAdapter<String> adapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String urlString = getUrl("94043", 7);
        if (item.getItemId() == R.id.action_refresh) {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute(urlString);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        String urlString = getUrl("94043", 7);
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview);
        FetchWeatherTask weatherTask = new FetchWeatherTask();
        weatherTask.execute(urlString);

        final ListView forecastList = (ListView) fragmentView.findViewById(R.id.list_view_forecast);
        forecastList.setAdapter(adapter);
        forecastList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = adapter.getItem(position);
                Intent detailedWeatherInformation = new Intent(getActivity(), DetailActivity.class);
                detailedWeatherInformation.putExtra("weatherDetail", forecast);
                startActivity(detailedWeatherInformation);
            }
        });
        return fragmentView;
    }


    private String getUrl(String postalCode, int numberOfDays) {
        final String API_KEY = this.getResources().getString(R.string.api_key);
        final String BASE = "http://api.openweathermap.org/data/2.5/forecast/daily";

        Uri baseUrl = Uri.parse(BASE);
        Uri finalUrl = baseUrl.buildUpon()
                .appendQueryParameter("q", postalCode)
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("cnt", String.valueOf(numberOfDays))
                .appendQueryParameter("appid", API_KEY)
                .build();

        return finalUrl.toString();
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, List<String>> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected void onPostExecute(List<String> weatherInformation) {
            adapter.clear();
            for (String weatherForADay : weatherInformation) {
                adapter.add(weatherForADay);
            }
        }

        @Override
        protected List<String> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String urlString = params[0];
            HttpURLConnection urlConnection;
            BufferedReader reader;
            try {
                Log.v("URL", urlString);
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int response = urlConnection.getResponseCode();
                Log.v("Response Code", String.valueOf(response));


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String jsonData = reader.readLine();
                Log.v("Data returned", jsonData);

                List<String> weatherInformation = parseJSON(jsonData);

                return weatherInformation;

            } catch (MalformedURLException e) {
                Log.e("Placeholder Fragment", "Malformed URL Error", e);
            } catch (IOException e) {
                Log.e("Placeholder Fragment", "IO Error", e);
            }
            return null;
        }

        private List<String> parseJSON(String jsonData) {
            List<String> weatherInformation = new ArrayList<>();

            final String KEY_TEMP = "temp";
            final String KEY_MAX = "max";
            final String KEY_MIN = "min";
            final String KEY_LIST = "list";
            final String KEY_CONDITION_NAME = "main";
            final String KEY_WEATHER = "weather";

            String maxTemp;
            String minTemp;
            String weatherCondition;
            String displayTime;


            JSONObject weatherForADay;

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Delhi"));
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM d");

            try {
                StringBuilder dataBuilder = new StringBuilder();
                JSONObject json = new JSONObject(jsonData);
                JSONArray weatherDetailsList = json.getJSONArray(KEY_LIST);
                for (int i = 0; i < weatherDetailsList.length(); i++) {

                    dataBuilder.setLength(0);
                    weatherForADay = weatherDetailsList.getJSONObject(i);
                    maxTemp = weatherForADay.getJSONObject(KEY_TEMP).getString(KEY_MAX);
                    minTemp = weatherForADay.getJSONObject(KEY_TEMP).getString(KEY_MIN);
                    weatherCondition = weatherForADay.getJSONArray(KEY_WEATHER).getJSONObject(0).getString(KEY_CONDITION_NAME);

                    calendar.setTimeInMillis(System.currentTimeMillis() + (i * TimeUnit.DAYS.toMillis(1)) + calendar.get(Calendar.ZONE_OFFSET));
                    displayTime = dateFormatter.format(calendar.getTime());

                    dataBuilder.append(displayTime).append(" : ").append(weatherCondition).append("-").append(maxTemp).append("/").append(minTemp);
                    weatherInformation.add(dataBuilder.toString());

                    Log.v(LOG_TAG, dataBuilder.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherInformation;
        }
    }
}