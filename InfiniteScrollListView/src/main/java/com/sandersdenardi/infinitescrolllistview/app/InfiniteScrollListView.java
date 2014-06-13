package com.sandersdenardi.infinitescrolllistview.app;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListAdapter;

public class InfiniteScrollListView extends AbsListView {

    private InfiniteOnScrollListener listener;

    public InfiniteScrollListView(Context context, InfiniteOnScrollListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public ListAdapter getAdapter() {
        return null;
    }

    @Override
    public void setSelection(int i) {

    }
}
