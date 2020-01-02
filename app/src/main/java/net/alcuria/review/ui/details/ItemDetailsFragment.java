package net.alcuria.review.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.alcuria.review.R;
import net.alcuria.review.calc.LeechSubject;
import net.alcuria.review.http.models.SubjectType;

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
            setHeaders(view);
            ((TextView) view.findViewById(R.id.reading_mnemonic)).setText(subject.subject.readingMnemonic);
            view.findViewById(R.id.hint_reading).<TextView>findViewById(R.id.hint_text).setText(subject.subject.readingHint);
            ((TextView) view.findViewById(R.id.meaning_mnemonic)).setText(subject.subject.meaningMnemonic);
            view.findViewById(R.id.hint_meaning).<TextView>findViewById(R.id.hint_text).setText(subject.subject.meaningHint);
            switch (SubjectType.from(subject.review.subjectType)) {
                case KANJI:
                    break;
                case VOCABULARY:
                    view.<TextView>findViewById(R.id.radical_combination).setText(subject.getComponentSubjects());
                    break;
                case RADICAL:
                    break;
            }
        }
    }

    private void setHeaders(@NonNull View view) {
        int[] headerIds = {R.id.radical_combination_header, R.id.meaning_mnemonic_header, R.id.reading_mnemonic_header, R.id.similar_kanji_header, R.id.found_in_vocabulary_header};
        int[] headerText = {R.string.header_radical_combinations, R.string.header_meaning_mnemonic, R.string.header_reading_mnemonic, R.string.header_visually_similar_kanji, R.string.header_vocab_containing_this_kanji};
        for (int i = 0; i < headerIds.length; i++) {
            final TextView header = view.findViewById(headerIds[i]).findViewById(R.id.header_text);
            header.setText(headerText[i]);
        }
    }
}
