package net.alcuria.review.ui.review;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.alcuria.review.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewQuizAnswerFragment extends Fragment {

    public ReviewQuizAnswerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_quiz_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.incorrect_answer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(view, true);
            }
        });
        view.findViewById(R.id.correct_answer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(view, false);
            }
        });
    }

    private void answer(View view, boolean incorrect) {
        System.out.println("Answered, incorrect? " + incorrect);
        Navigation.findNavController(view).navigate(ReviewQuizAnswerFragmentDirections.actionAnswerFragmentToQuestionFragment());
    }

}
