package net.alcuria.review.ui.home.section;

import android.view.View;
import android.widget.TextView;

import net.alcuria.review.R;

import androidx.recyclerview.widget.RecyclerView;

class SubjectHeaderViewHolder extends RecyclerView.ViewHolder {

    final TextView title;

    public SubjectHeaderViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.header);
    }
}
