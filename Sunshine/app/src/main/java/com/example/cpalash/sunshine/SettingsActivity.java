package com.example.cpalash.sunshine;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreference(findPreference(getString(R.string.location_key)));
        bindPreference(findPreference(getString(R.string.unit_key)));
    }

    private void bindPreference(Preference selectedPreference){
        selectedPreference.setOnPreferenceChangeListener(this);
        onPreferenceChange(selectedPreference, PreferenceManager.getDefaultSharedPreferences(selectedPreference.getContext())
                .getString(selectedPreference.getKey(),""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.v("Preference Changed",preference.toString());
        Log.v("Preference Value", newValue.toString());
        String value = newValue.toString();
        preference.setSummary(value);
        return true;
    }
}
