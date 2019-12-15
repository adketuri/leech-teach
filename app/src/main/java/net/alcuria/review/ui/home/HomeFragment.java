package net.alcuria.review.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.alcuria.review.R;
import net.alcuria.review.calc.LeechCalculator;
import net.alcuria.review.repository.SubjectRepository;
import net.alcuria.review.ui.home.section.SubjectSection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A UI controller responsible to display UI data, react to user actions, handle OS communication, etc.
 *
 * @author Andrew Keturi
 */
public class HomeFragment extends Fragment implements SubjectSection.ClickListener {

    private HomeViewModel homeViewModel;
    private SectionedRecyclerViewAdapter sectionedAdapter;
    private SubjectRepository repository = new SubjectRepository();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new HomeViewModel(repository);

        View root = inflater.inflate(R.layout.section_recyclerview, container, false);
        Log.i("Home", "Creating home view");

        sectionedAdapter = new SectionedRecyclerViewAdapter();
        homeViewModel.getCalculator().observe(this, calculator -> {
            for (LeechCalculator.LeechLevel level : LeechCalculator.LeechLevel.values()) {
                sectionedAdapter.addSection(new SubjectSection(level.getTitle(), calculator.getSubjects(level), HomeFragment.this));
            }
            sectionedAdapter.notifyDataSetChanged();
            Log.i("Home", "Data set changed");
        });

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionedAdapter);
        return root;
    }

    @Override
    public void onItemRootViewClicked(@NonNull String sectionTitle, int itemAdapterPosition) {
        Toast.makeText(
                getContext(),
                String.format("Clicked on position #%s of Section %s", sectionedAdapter.getPositionInSection(itemAdapterPosition), sectionTitle),
                Toast.LENGTH_SHORT
        ).show();
    }
}