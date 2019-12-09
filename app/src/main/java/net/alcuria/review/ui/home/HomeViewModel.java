package net.alcuria.review.ui.home;

import android.util.Log;

import net.alcuria.review.http.HttpUtil;
import net.alcuria.review.http.ResponseListener;
import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.Subject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Stores and manages UI-related data for the homepage in a lifecycle-conscious way.
 * Must never reference a View, Lifecycle, or any class that has a reference to the
 * activity's context.
 *
 * @author Andrew Keturi
 */
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ResponseData<Subject>> mSubjectResponse;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Set your API key in the settings");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ResponseData<Subject>> getSubjectResponse(String key) {
        if (mSubjectResponse == null) {
            mSubjectResponse = new MutableLiveData<>();
            loadSubjects(key);
        }
        return mSubjectResponse;
    }

    private void loadSubjects(String key) {
        Log.i("Home", "Loading subjects");
        HttpUtil.getInstance().getSubjects(new ResponseListener<ResponseData<Subject>>() {
            @Override
            public void invoke(ResponseData<Subject> response) {
                mSubjectResponse.setValue(response);
            }
        }, key);
    }

    public void setApiKey(String key) {
        if (key == null || key.trim().length() == 0) {
            mText.setValue("Set your API key in the settings");
        } else {
            mText.setValue("We have your API key");
        }
    }
}