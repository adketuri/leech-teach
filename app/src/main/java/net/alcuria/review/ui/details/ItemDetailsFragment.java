package net.alcuria.review.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.alcuria.review.R;
import net.alcuria.review.calc.LeechSubject;

import androidx.fragment.app.Fragment;

public class ItemDetailsFragment extends Fragment {

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

}
