package com.sandersdenardi.infinitescrolllistview.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.sandersdenardi.infinitescrolllistview.app.lib.IInfiniteScrollListener;
import com.sandersdenardi.infinitescrolllistview.app.lib.InfiniteScrollListView;
import com.sandersdenardi.infinitescrolllistview.app.lib.InfiniteScrollOnScrollListener;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements IInfiniteScrollListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    protected InfiniteScrollListView listView;
    private InfiniteScrollOnScrollListener scrollListener;
    private ListTask listTask;
    private boolean executing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Starting App");
        setContentView(R.layout.activity_main);
        listView = (InfiniteScrollListView) findViewById(R.id.list_view);
        scrollListener = new InfiniteScrollOnScrollListener(this);
        listView.setListener(scrollListener);
        listView.setAdapter(new MyAdapter(this));

        //Populate initial list
        ArrayList<MyAdapterObj> items = new ArrayList<MyAdapterObj>();
        for (int i = 0; i < 20; i++) {
            String main = "Main index: " + String.valueOf(i);
            String sub = "Sub index: " + String.valueOf(i);
            items.add(new MyAdapterObj(main,sub));
        }
        listView.appendItems(items);
    }

    @Override
    public void endIsNear() {
        if (!executing) {
            Toast.makeText(this, "End is near", Toast.LENGTH_SHORT).show();
            executing = true;
            listTask = new ListTask();
            listTask.execute(listView.getRealCount());
        }
    }

    private class ListTask extends AsyncTask<Integer, Void, ArrayList<MyAdapterObj>> {

        @Override
        protected ArrayList<MyAdapterObj> doInBackground(Integer... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<MyAdapterObj> items = new ArrayList<MyAdapterObj>();
            if (params[0] < 60) {
                for (int i = params[0]; i < (params[0] + 20); i++) {
                    String main = "Main index: " + String.valueOf(i);
                    String sub = "Sub index: " + String.valueOf(i);
                    items.add(new MyAdapterObj(main,sub));
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<MyAdapterObj> result) {
            listView.appendItems(result);
            executing = false;
            if (result.size() > 0) {
                Toast.makeText(getApplicationContext(), "Loaded " + String.valueOf(result.size()) + " items", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getApplicationContext(), "No more items to load", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
