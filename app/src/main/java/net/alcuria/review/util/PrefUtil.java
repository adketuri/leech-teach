package net.alcuria.review.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;

/**
 * Utility functions for preferences.
 *
 * @author Andrew Keturi
 */
public class PrefUtil {

    private static final String API_KEY = "preference_api_key";
    private static final String REVIEWS = "preference_reviews";

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

    /**
     * @return our WK API key
     */
    public final String apiKey() {
        return preferences.getString(API_KEY, null);
    }

    /**
     * @return the list of review ids. Ideally we store this in a room db but for v1 we're going to keep things simple and a little ugly.
     */
    public final Set<Integer> reviews() {
        Set<Integer> reviews = new HashSet<>();
        String[] prefs = preferences.getString(REVIEWS, "").split(",");
        for (String pref : prefs) {
            if (pref.trim().length() > 0) {
                reviews.add(Integer.valueOf(pref));
            }
        }
        return reviews;
    }

    public final void addReview(int id) {
        Set<Integer> reviews = reviews();
        reviews.add(id);
        StringBuilder sb = new StringBuilder();
        for (Integer i : reviews) {
            sb.append(i).append(",");
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(REVIEWS, sb.toString());
        editor.apply();
    }

}
