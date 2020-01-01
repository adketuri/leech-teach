package net.alcuria.review.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.alcuria.review.R;
import net.alcuria.review.calc.LeechSubject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ItemDetailsFragment extends Fragment {

    private static final String TAG = "ItemDetailsFragment";
    private LeechSubject subject;

    public ItemDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            ItemDetailsFragmentArgs args = ItemDetailsFragmentArgs.fromBundle(getArguments());
            LeechSubject subject = args.getSubject();
            Log.i(TAG, "onViewCreated: " + subject);
            final TextView character = view.findViewById(R.id.character_detail);
            character.setText(subject.getCharacters());
            final TextView reading = view.findViewById(R.id.reading_detail);
            reading.setText(subject.getReading());
            final TextView meaning = view.findViewById(R.id.meaning_detail);
            meaning.setText(subject.getMeaning());
        }
    }
}
