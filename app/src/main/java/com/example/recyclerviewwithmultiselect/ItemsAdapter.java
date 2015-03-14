package com.example.recyclerviewwithmultiselect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maciejwitowski on 3/14/15.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.WordsViewHolder>
                            implements View.OnClickListener {

    private List<String> items = new ArrayList<>();

    private final Set<Long> selectedIds = new HashSet<>();

    public ItemsAdapter() {
        setHasStableIds(true);
    }

    @Override
    public WordsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);

        WordsViewHolder viewHolder = new WordsViewHolder(view);
        viewHolder.checkBox.setOnClickListener(this);
        return new WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordsViewHolder wordsViewHolder, int position) {
        wordsViewHolder.name.setText(items.get(position));

        wordsViewHolder.checkBox.setChecked(selectedIds.contains(getItemId(position)));
        wordsViewHolder.checkBox.setTag(wordsViewHolder);
    }

    @Override
    public long getItemId(int position) {
        return getItemId(items.get(position));
    }

    private long getItemId(String item) {
        return item.hashCode();
    }

    private String getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceItems(List<String> newItems) {
        if(newItems != null) {
            this.items = newItems;
            notifyDataSetChanged();
        }
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if(viewId == R.id.header_checkbox) {
            WordsViewHolder viewHolder = (WordsViewHolder) view.getTag();
            String item = getItem(viewHolder.getPosition());
            boolean isChecked = viewHolder.checkBox.isChecked();
            updateSelectedItem(item, isChecked);
        }
    }

    public void updateSelectedItem(String item, boolean isSelected) {
        long itemId = getItemId(item);

        if(isSelected) {
            selectedIds.add(itemId);
        } else {
            selectedIds.remove(itemId);
        }
    }

    public class WordsViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.name) TextView name;
        @InjectView(R.id.header_checkbox) CheckBox checkBox;

        public WordsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
