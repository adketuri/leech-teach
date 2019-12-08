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

    private static PrefUtil sInstance;
    private final SharedPreferences mPreferences;

    private PrefUtil(@NonNull final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PrefUtil getInstance(@NonNull final Context context) {
        if (sInstance == null) {
            sInstance = new PrefUtil(context.getApplicationContext());
        }
        return sInstance;
    }

    public final String apiKey() {
        return mPreferences.getString(API_KEY, null);
    }
}
