package net.alcuria.review.ui.review;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.alcuria.review.R;
import net.alcuria.review.util.PrefUtil;

import java.util.Locale;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewOverviewFragment extends Fragment {


    public ReviewOverviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Set<Integer> reviews = PrefUtil.getInstance().reviews();
        view.<TextView>findViewById(R.id.review_description).setText(String.format(Locale.ENGLISH, "You have %d items to review", reviews.size()));
        view.findViewById(R.id.start_reviews_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(ReviewOverviewFragmentDirections.actionNavReviewToReviewQuizQuestionFragment());
            }
        });
    }
}
