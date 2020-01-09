package net.alcuria.review.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.alcuria.review.R;
import net.alcuria.review.calc.LeechCalculator;
import net.alcuria.review.calc.LeechSubject;
import net.alcuria.review.ui.home.section.SubjectSection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A UI controller responsible to display UI data, react to user actions, handle OS communication, etc.
 *
 * @author Andrew Keturi
 */
public class HomeFragment extends Fragment implements SubjectSection.ClickListener {

    private SectionedRecyclerViewAdapter sectionedAdapter;
    private NavController navController;
    private HomeViewModel homeViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (homeViewModel == null) {
            homeViewModel = new HomeViewModel();
        }

        View root = inflater.inflate(R.layout.section_recyclerview, container, false);
        Log.i("Home", "Creating home view");

        sectionedAdapter = new SectionedRecyclerViewAdapter();
        homeViewModel.getCalculator().observe(this, calculator -> {
            sectionedAdapter.removeAllSections();
            for (LeechCalculator.LeechLevel level : LeechCalculator.LeechLevel.values()) {
                sectionedAdapter.addSection(new SubjectSection(level.getTitle(calculator.getSubjects(level).size()), calculator.getSubjects(level), HomeFragment.this));
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onItemRootViewClicked(@NonNull String sectionTitle, int itemAdapterPosition) {
        LeechSubject subject = homeViewModel.getCalculator().getValue().getSubjects(LeechCalculator.LeechLevel.from(sectionTitle)).get(itemAdapterPosition - 1);
        HomeFragmentDirections.ActionNavHomeToItemDetails action = HomeFragmentDirections.actionNavHomeToItemDetails(subject);
        navController.navigate(action);
    }
}