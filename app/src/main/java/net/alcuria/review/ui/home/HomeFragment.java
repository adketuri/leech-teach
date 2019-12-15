package net.alcuria.review.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.alcuria.review.R;
import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.Subject;
import net.alcuria.review.repository.SubjectRepository;
import net.alcuria.review.ui.home.section.SubjectSection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new HomeViewModel(new SubjectRepository());

        View root = inflater.inflate(R.layout.section_recyclerview, container, false);
        Log.i("Home", "Creating home view");

        sectionedAdapter = new SectionedRecyclerViewAdapter();
        homeViewModel.getSubjectData().observe(this, new Observer<ResponseData<Subject>>() {
            @Override
            public void onChanged(ResponseData<Subject> subjectResponse) {
                sectionedAdapter.addSection(new SubjectSection("Test Title", subjectResponse.data, HomeFragment.this));
                sectionedAdapter.notifyDataSetChanged();
                Log.i("Home", "Data set changed");
            }
        });

//        List<Subject> data = new ArrayList<>();
//        Subject s = new Subject();
//        s.id = 123;
//        data.add(s);
//        sectionedAdapter.addSection(new SubjectSection("Test Title 2", data, HomeFragment.this));

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