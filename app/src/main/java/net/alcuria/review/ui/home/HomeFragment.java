package net.alcuria.review.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.alcuria.review.R;
import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.Subject;
import net.alcuria.review.util.PrefUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * A UI controller responsible to display UI data, react to user actions, handle OS communication, etc.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.setApiKey(PrefUtil.getInstance(getContext()).apiKey());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        Log.i("Home", "Creating home view");
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final TextView responseView = root.findViewById(R.id.text_response);
        homeViewModel.getSubjectResponse(PrefUtil.getInstance(getContext()).apiKey()).observe(this, new Observer<ResponseData<Subject>>() {
            @Override
            public void onChanged(ResponseData<Subject> subjectResponse) {
                Log.i("Home", "Got some response");
                responseView.setText(String.valueOf(subjectResponse.totalCount));
            }
        });
        return root;
    }

}