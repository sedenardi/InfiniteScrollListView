package com.sandersdenardi.infinitescrolllistview.app.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.ArrayList;

public class InfiniteScrollListView extends ListView {

    private InfiniteScrollOnScrollListener listener;

    public InfiniteScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListener(InfiniteScrollOnScrollListener listener) {
        this.listener = listener;
    }

    public void setAdapter(InfiniteScrollAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setSelection(int position) { }

    public void appendItems(ArrayList items) {
        if (getAdapter() == null) {
            throw new NullPointerException("Can not append items to a null adapter");
        }
        ((InfiniteScrollAdapter)getAdapter()).addItems(items);
        if (items.size() == 0) {
            setOnScrollListener(null);
        } else {
            setOnScrollListener(listener);
            listener.checkForFetchMore(this);
        }
    }

    public int getRealCount() {
        return ((InfiniteScrollAdapter)getAdapter()).getItems().size();
    }
}
