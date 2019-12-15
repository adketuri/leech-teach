package net.alcuria.review.ui.home.section;

import android.view.View;

import net.alcuria.review.R;
import net.alcuria.review.calc.LeechSubject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class SubjectSection extends Section {

    private final String title;
    private final List<LeechSubject> list;
    private final ClickListener clickListener;

    public SubjectSection(@NonNull final String title, @NonNull final List<LeechSubject> list,
                          @NonNull final ClickListener clickListener) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.section_header)
                .build());
        this.title = title;
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new SubjectItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SubjectItemViewHolder itemHolder = (SubjectItemViewHolder) holder;
        final LeechSubject subject = list.get(position);
        itemHolder.reading.setText(subject.getReading());
        itemHolder.meaning.setText(subject.getMeaning());
        itemHolder.imgItem.setImageResource(R.drawable.ic_menu_camera);
        itemHolder.rootView.setOnClickListener(v -> clickListener.onItemRootViewClicked(title, itemHolder.getAdapterPosition()));
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SubjectHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        final SubjectHeaderViewHolder headerHolder = (SubjectHeaderViewHolder) holder;
        headerHolder.title.setText(title);
    }

    public interface ClickListener {
        void onItemRootViewClicked(@NonNull final String sectionTitle, final int itemAdapterPosition);
    }
}
