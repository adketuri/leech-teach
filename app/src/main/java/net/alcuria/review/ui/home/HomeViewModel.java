package net.alcuria.review.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Stores and manages UI-related data for the homepage in a lifecycle-conscious way.
 */

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Set your API key in the settings");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setApiKey(String key) {
        if (key == null || key.trim().length() == 0) {
            mText.setValue("Set your API key in the settings");
        } else {
            mText.setValue("We have your API key");
        }
    }
}