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
import net.alcuria.review.repository.SubjectRepository;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

/**
 * A UI controller responsible to display UI data, react to user actions, handle OS communication, etc.
 *
 * @author Andrew Keturi
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new HomeViewModel(new SubjectRepository());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i("Home", "Creating home view");

        final TextView responseView = root.findViewById(R.id.text_response);
        homeViewModel.getSubjectData().observe(this, new Observer<ResponseData<Subject>>() {
            @Override
            public void onChanged(ResponseData<Subject> subjectResponse) {
                responseView.setText(String.valueOf(subjectResponse.totalCount));
            }
        });
        return root;
    }
}