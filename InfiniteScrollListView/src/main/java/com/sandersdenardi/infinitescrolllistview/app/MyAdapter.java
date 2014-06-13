package com.sandersdenardi.infinitescrolllistview.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sandersdenardi.infinitescrolllistview.app.lib.InfiniteScrollAdapter;
import com.sandersdenardi.infinitescrolllistview.app.lib.MyAdapterObj;

import java.util.ArrayList;

public class MyAdapter extends InfiniteScrollAdapter {

    private ArrayList<MyAdapterObj> items;

    public MyAdapter(Context context) {
        super(context);
        items = new ArrayList<MyAdapterObj>();
    }

    @Override
    public ArrayList getItems() {
        return items;
    }

    @Override
    public void addItems(ArrayList items) {
        if (items.size() > 0) {
            this.items.addAll(items);
        } else {
            super.setDoneLoading();
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getRealItem(int position) {
        return items.get(position);
    }

    @Override
    public View getRealView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.list_item, null);
        ((TextView)v.findViewById(R.id.text_main)).setText(items.get(position).main);
        ((TextView)v.findViewById(R.id.text_sub)).setText(items.get(position).sub);
        return v;
    }

    @Override
    public View getLoadingView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.list_loading, null);
    }
}
