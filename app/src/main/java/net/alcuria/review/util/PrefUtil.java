package net.alcuria.review.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

/**
 * Utility functions for preferences.
 *
 * @author Andrew Keturi
 */
public class PrefUtil {

    public static final String API_KEY = "preference_api_key";

    private static PrefUtil instance;
    private final SharedPreferences preferences;

    private PrefUtil(@NonNull final Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void init(Context context) {
        instance = new PrefUtil(context.getApplicationContext());
    }

    public static PrefUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call initialize() first");
        }
        return instance;
    }

    public final String apiKey() {
        return preferences.getString(API_KEY, null);
    }
}
