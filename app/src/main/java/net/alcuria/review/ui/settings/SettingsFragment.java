package net.alcuria.review.ui.settings;

import android.os.Bundle;

import net.alcuria.review.R;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_screen, rootKey);
    }

}
