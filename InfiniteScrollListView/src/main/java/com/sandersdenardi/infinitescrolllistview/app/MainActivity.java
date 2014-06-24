package com.sandersdenardi.infinitescrolllistview.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sandersdenardi.infinitescrolllistview.app.lib.IInfiniteScrollListener;
import com.sandersdenardi.infinitescrolllistview.app.lib.InfiniteScrollListView;
import com.sandersdenardi.infinitescrolllistview.app.lib.InfiniteScrollOnScrollListener;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements IInfiniteScrollListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    protected InfiniteScrollListView listView;
    protected TextView firstVisibleItemText;
    protected TextView visibleItemCountText;
    protected TextView totalItemCountText;
    private MyAdapter adapter;
    private InfiniteScrollOnScrollListener scrollListener;
    private ListTask listTask;
    private boolean executing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Starting App");
        setContentView(R.layout.activity_main);

        firstVisibleItemText = (TextView) findViewById(R.id.firstVisibleItem);
        visibleItemCountText = (TextView) findViewById(R.id.visibleItemCount);
        totalItemCountText = (TextView) findViewById(R.id.totalItemCount);

        listView = (InfiniteScrollListView) findViewById(R.id.list_view);
        scrollListener = new InfiniteScrollOnScrollListener(this);
        listView.setListener(scrollListener);
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);

        //Populate initial list
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            String str = "Index: " + String.valueOf(i);
            items.add(str);
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

    // Item visibility code
    @Override
    public void onScrollCalled(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstVisibleItemText.setText(String.valueOf(firstVisibleItem));
        visibleItemCountText.setText(String.valueOf(visibleItemCount));
        totalItemCountText.setText(String.valueOf(totalItemCount));
    }

    private class ListTask extends AsyncTask<Integer, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String> items = new ArrayList<String>();
            if (params[0] < 40) {
                for (int i = params[0]; i < (params[0] + 8); i++) {
                    String str = "Index: " + String.valueOf(i);
                    items.add(str);
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
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
