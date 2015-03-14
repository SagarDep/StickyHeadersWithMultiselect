package com.example.recyclerviewwithmultiselect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maciejwitowski on 3/14/15.
 */
public class HeadersAdapter implements StickyHeadersAdapter<HeadersAdapter.HeaderViewHolder> {

    private List<String> items = new ArrayList<>();
    private final Set<Long> selectedHeaders = new HashSet<>();

    @Override
    public HeaderViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        final View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.header_view, viewGroup, false);

        return new HeaderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HeaderViewHolder headerViewHolder, int position) {
        String item = items.get(position);

        if(item != null) {
            headerViewHolder.title.setText(item.substring(0, 1).toUpperCase());
        }

        headerViewHolder.checkBox.setChecked(selectedHeaders.contains(getHeaderId(position)));
    }

    @Override
    public long getHeaderId(int position) {
        return getHeaderId(items.get(position));
    }

    public boolean isHeaderSelected(long headerId) {
        return selectedHeaders.contains(headerId);
    }

    public long getHeaderId(String item) {
        return item.charAt(0);
    }

    public void replaceItems(List<String> newWords) {
        if(newWords != null) {
            items = newWords;
        }
    }

    public void updateSelectedHeaders(long headerId) {
        if(selectedHeaders.contains(headerId)) {
            selectedHeaders.remove(headerId);
        } else {
            selectedHeaders.add(headerId);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title)
        TextView title;

        @InjectView(R.id.header_checkbox)
        CheckBox checkBox;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
