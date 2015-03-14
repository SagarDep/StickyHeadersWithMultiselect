package com.example.recyclerviewwithmultiselect;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements OnHeaderClickListener {

    @InjectView(R.id.rv_items) RecyclerView mRvItems;

    ItemsAdapter mItemsAdapter;
    StickyHeadersAdapter mStickyHeadersAdapter;
    StickyHeadersItemDecoration mStickyHeadersItemDecoration;

    float[] mLastClickCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        setupWordsList();
    }

    private void setupWordsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvItems.setLayoutManager(layoutManager);

        mItemsAdapter = new ItemsAdapter();
        mItemsAdapter.replaceItems(ItemsProducer.getItems());
        mRvItems.setAdapter(mItemsAdapter);

        mRvItems.addOnItemTouchListener(new OnRecyclerViewItemTouch());

        setupHeaders();
    }

    private void setupHeaders() {
        mStickyHeadersAdapter = new HeadersAdapter();
        ((HeadersAdapter)mStickyHeadersAdapter).replaceItems(mItemsAdapter.getItems());

        mStickyHeadersItemDecoration = new StickyHeadersBuilder()
                .setAdapter(mItemsAdapter)
                .setRecyclerView(mRvItems)
                .setStickyHeadersAdapter(mStickyHeadersAdapter)
                .setOnHeaderClickListener(this)
                .build();

        mRvItems.addItemDecoration(mStickyHeadersItemDecoration);
    }

    @Override
    public void onHeaderClick(View view, long headerId) {
        if(!(mStickyHeadersAdapter instanceof HeadersAdapter)) return;

        final HeadersAdapter headersAdapter = (HeadersAdapter) mStickyHeadersAdapter;

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.header_checkbox);

        if(checkboxAreaClicked(checkBox)) {
            headersAdapter.updateSelectedHeaders(headerId);

            boolean isSelected = headersAdapter.isHeaderSelected(headerId);

            for(String item : mItemsAdapter.getItems()) {
                long itemsHeaderId = headersAdapter.getHeaderId(item);

                if(itemsHeaderId == headerId) {
                    mItemsAdapter.updateSelectedItem(item, isSelected);
                }
            }

            mItemsAdapter.notifyDataSetChanged();
        }
    }

    // Check whether click was horizontally within checkbox area
    private boolean checkboxAreaClicked(CheckBox checkBox) {
        boolean wasCheckboxClicked = false;

        if(checkBox != null && mLastClickCoordinates.length > 0) {
            float clickedX = mLastClickCoordinates[0];
            float checkboxRight = checkBox.getRight();
            float checkboxLeft = checkBox.getLeft();

            wasCheckboxClicked = (clickedX > checkboxLeft && clickedX < checkboxRight);
        }

        return wasCheckboxClicked;
    }

    private class OnRecyclerViewItemTouch implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mLastClickCoordinates = new float[]{e.getX(), e.getY()};
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
    }
}
