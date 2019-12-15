package net.alcuria.review.ui.home.section;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.alcuria.review.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectItemViewHolder extends RecyclerView.ViewHolder {

    final View rootView;
    final ImageView imgItem;
    final TextView tvItem;

    public SubjectItemViewHolder(@NonNull View view) {
        super(view);

        rootView = view;
        imgItem = view.findViewById(R.id.subject_image);
        tvItem = view.findViewById(R.id.reading);
    }
}
