package com.codepath.apps.SimpleTwitterClient.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.SimpleTwitterClient.Adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.Adapters.TweetsArrayAdapter;
import com.codepath.apps.SimpleTwitterClient.TwitterApplication;
import com.codepath.apps.SimpleTwitterClient.TwitterClient;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;
import com.codepath.apps.SimpleTwitterClient.models.UserVerification.TwitterUserProfile;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.onComposeFinishedListener{

    static final String TAG = "STEVEDEBUG";
    private TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;

    private boolean isInitialQuery = true;
    private boolean swipeToRefresh = false;
    private long maxId = 1;

    //butterknife binds
    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setTitle(R.string.title_activity_timeline);
        ButterKnife.bind(this);

        //create array list (data source)
        tweets = new ArrayList<>();

        //construct adapter from datasource
        aTweets = new TweetsArrayAdapter(this,tweets);
        //connect adapter to recycler view
        initrvTweets();
        initSwipeContainer();


        //retrieve singleton client from twitter application
        client = TwitterApplication.getRestClient();
        populateTimeLine();
    }


    //menu items overrides --------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle action item
        switch (item.getItemId()) {
            case R.id.miCompose:
                composeMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timeline_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //reload everything, which should have new tweet.
    @Override
    public void onComposeFinish()
    {
        isInitialQuery = true;
        //Log.d(TAG, "onComposeFinish: clearing data");
        aTweets.clearData();
        populateTimeLine();
    }


    //-------------initialization functions --------------------

    public void initrvTweets(){
        rvTweets.setAdapter(aTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });
    }

    public void initSwipeContainer(){
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }






    //send api request to get timeline json and fill recyclerview by creating the tweet objects
    private void populateTimeLine() {
        if (isInitialQuery)
        {
            client.getInitialHomeTimeline(new JsonHttpResponseHandler() {
               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                   ArrayList<Tweet> JSONTweets = Tweet.fromJSONArray(json);

                   //dedupe and set id if there's something in tweets.
                   if(JSONTweets.size() > 0 && !duplicateCheck(JSONTweets, tweets)) {
                       setLastID(JSONTweets.get(JSONTweets.size()-1));
                       tweets.addAll(JSONTweets);
                       aTweets.notifyDataSetChanged();

                   }
                   else{
                       Log.d(TAG, "failed dupe check. Not adding. ");
                   }
               }

               @Override
               public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                   Log.d(TAG, errorResponse.toString());
               }
            });
            isInitialQuery = false;
        }
        else
        {
            client.getPaginatedHomeTimeline(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    ArrayList<Tweet> JSONTweets = Tweet.fromJSONArray(json);

                    //dedupe and set id if there's something in tweets.
                    if(JSONTweets.size() > 0 && !duplicateCheck(JSONTweets, tweets)) {
                        setLastID(JSONTweets.get(JSONTweets.size()-1));
                        tweets.addAll(JSONTweets);
                        aTweets.notifyDataSetChanged();
                    }
                    else{
                        Log.d(TAG, "failed dupe check. Not adding. ");
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d(TAG, errorResponse.toString());
                }
            },  maxId);
        }
    }

    /*-----------------------------------------------------Helper functions --------------------------------------------------------------*/
    //check for duplicate listview items.
    private boolean duplicateCheck(List<Tweet> newTweet, List<Tweet> baseTweet)
    {
        boolean isDupe = false;
        //look for just 1 duplicate.  If the record is already in the document list, ignore the whole batch.
        long curID = 0;

        if (newTweet.size() > 0)
        {
            curID = newTweet.get(0).getId();
        }

        for (int i = 0; i < baseTweet.size(); i++)
        {
            long looperID = baseTweet.get(i).getId();
            //Log.d(TAG, curID + " = " + looperID);
            if (looperID == curID)
            {
                isDupe = true;
                break;
            }
        }
        return isDupe;
    }


    private void setLastID(Tweet tweet) {
        maxId = tweet.getId()-1;
    }

    //launch the setting dialog
    public void composeMessage(){
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment composeFragment = new ComposeFragment();
        composeFragment.show(fm, "fragment_compose");
    }


    // Listener helper functions ----------------
    //Append more data into the adapter
    public void customLoadMoreDataFromApi(int page) {
        populateTimeLine();
    }

    public void fetchTimelineAsync(int page) {
        swipeToRefresh = true;
        isInitialQuery = true;
        aTweets.clearData();
        populateTimeLine();
        swipeToRefresh = false;
        swipeContainer.setRefreshing(false);
    }
}
