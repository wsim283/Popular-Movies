package example.com.popularmovies.Settings;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.com.popularmovies.R;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);
        Preference sortPref = findPreference(getString(R.string.pref_sort_key));
        bindPreferenceSummaryToValue(sortPref);
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ListPreference listPreference = (ListPreference) preference;
        int prefIndex = listPreference.findIndexOfValue(newValue.toString());
        if (prefIndex >= 0) {
            preference.setSummary(listPreference.getEntries()[prefIndex]);
        }
        return true;
    }
}
