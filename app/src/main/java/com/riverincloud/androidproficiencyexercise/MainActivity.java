package com.riverincloud.androidproficiencyexercise;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Di on 1/04/2015.
 */
public class MainActivity extends ActionBarActivity {

    private final String TAG = this.getClass().getSimpleName();

    private final String JSON_URL = "https://dl.dropboxusercontent.com/u/746330/facts.json";
    private String listTitle = "";
    private List<Row> rowList = new ArrayList<Row>();

    private TextView titleTextView;
    private ListView rowListView;
    private RowAdapter rowAdapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        titleTextView = (TextView) findViewById(R.id.listTitle);
        titleTextView.setText("Loading...");
        rowListView = (ListView) findViewById(R.id.listView);

        parseJsonFeed();

        rowAdapter = new RowAdapter(this, rowList);
        rowListView.setAdapter(rowAdapter);
        Log.d(TAG, "***** rowListView.setAdapter(rowAdapter) called");

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        rowList.clear();
                        rowAdapter.notifyDataSetChanged();
                        titleTextView.setText("Reloading...");
                        parseJsonFeed();
                        // Stop the refreshing indicator
                        swipeLayout.setRefreshing(false);
                    }
                });
    }

    private void parseJsonFeed() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(JSON_URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "***** JsonObjectRequest - response.toString(): " + response.toString());

                        if(response != null) {
                            try {
                                listTitle = response.getString("title");

                                JSONArray rowsJson = response.getJSONArray("rows");
                                Log.d(TAG, "***** rowsJson.toString(): " + rowsJson.toString());

                                for(int i = 0; i < rowsJson.length() ; i++) {
                                    JSONObject rowJson = rowsJson.getJSONObject(i);
                                    String title = rowJson.getString("title");
                                    String description = rowJson.getString("description");
                                    String imageHref = rowJson.getString("imageHref");
                                    if(title != "null" ||
                                            description != "null" ||
                                            imageHref != "null") {
                                        Row row = new Row(title, description, imageHref);
                                        Log.d(TAG, "***** row " + i + ": " + row.toString());
                                        rowList.add(row);
                                    }
                                }
                                titleTextView.setText(listTitle);
                                rowAdapter.notifyDataSetChanged();

                            } catch(Exception e) {
                                titleTextView.setText("Loading failed!");
                                e.printStackTrace();
                                Log.e(TAG, "***** Fail to parse json: " + e.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "***** VolleyError: " + error.getMessage(), error);
            }
        });

        // Access the RequestQueue through my singleton class.
        MySingleton.getMySingleton(this).addToRequestQueue(jsObjRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
