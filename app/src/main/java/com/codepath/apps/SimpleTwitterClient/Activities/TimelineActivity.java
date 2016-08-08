package com.codepath.apps.SimpleTwitterClient.Activities;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTwitterClient.Adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.SimpleTwitterClient.Helpers.DBHelper;
import com.codepath.apps.SimpleTwitterClient.Helpers.Network;
import com.codepath.apps.SimpleTwitterClient.Helpers.ItemClickSupport;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.Adapters.TweetsArrayAdapter;
import com.codepath.apps.SimpleTwitterClient.TwitterApplication;
import com.codepath.apps.SimpleTwitterClient.TwitterClient;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;
import com.facebook.stetho.Stetho;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class TimelineActivity extends AppCompatActivity implements ComposeFragment.onComposeFinishedListener, DetailFragment.onDetailFinishedListener{

    static final String TAG = "STEVEDEBUG";

    private TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;

    private boolean areWeOnline = false;
    private boolean isInitialQuery = true;
    private boolean swipeToRefresh = false;
    private long maxId = 1;

    //helper classes
    Network network;
    DBHelper tweetDB;

    //butterknife binds
    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setTitle(R.string.title_activity_timeline);
        ButterKnife.bind(this);

        //create DB
        tweetDB = new DBHelper(this);
        //network helper
        network = new Network();
        //create array list (data source)
        tweets = new ArrayList<>();
        //construct adapter from datasource
        aTweets = new TweetsArrayAdapter(this,tweets);
        //check and set online state.
        areWeOnline = network.isNetworkAvailable(getApplicationContext()) && network.isOnline();
        //retrieve singleton client from twitter application
        client = TwitterApplication.getRestClient();


        //connect adapter to recycler view
        initrvTweets();
        initSwipeContainer();
        //populate tweets
        populateTimeLine(areWeOnline);


        //stetho debugger
        Stetho.initializeWithDefaults(this);
    }


    //menu items overrides --------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle action item
        switch (item.getItemId()) {
            case R.id.miCompose:
                if (areWeOnline) {
                    composeMessage();
                }
                else {
                    showOfflineAlert();
                }
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
        //might want to check to see if we're online...
        populateTimeLine(areWeOnline);
    }

    @Override
    public void onDetailFinished(boolean hasTweeted)
    {
        if (hasTweeted) {
            isInitialQuery = true;
            //Log.d(TAG, "onComposeFinish: clearing data");
            aTweets.clearData();
            //might want to check to see if we're online...
            populateTimeLine(areWeOnline);
        }
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

        ItemClickSupport.addTo(rvTweets).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //debug only
                //Toast.makeText(getApplicationContext(),"clicked " + position, Toast.LENGTH_SHORT).show();
                showItemDetail(position);
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

    //send api request to get timeline json and fill recyclerview by creating the tweet objects.
    private void populateTimeLine(boolean isOnline) {
        //If we're offline, populate from SQL db.
        if (isOnline) {
            //if it's the first query, pull everything.
            if (isInitialQuery) {
                client.getInitialHomeTimeline(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        ArrayList<Tweet> JSONTweets = Tweet.fromJSONArray(json);

                        //dedupe and set id if there's something in tweets.
                        if (JSONTweets.size() > 0 && !duplicateCheck(JSONTweets, tweets)) {
                            setLastID(JSONTweets.get(JSONTweets.size() - 1));
                            tweets.addAll(JSONTweets);
                            aTweets.notifyDataSetChanged();
                            //add the records to the DB
                            tweetDB.refresh();
                            tweetDB.addTweetArray(JSONTweets);

                        } else {
                            Log.e(TAG, "failed dupe check@onSuccess. Not adding. ");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d(TAG, errorResponse.toString());
                    }
                });
                isInitialQuery = false;
            } else {
                client.getPaginatedHomeTimeline(new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        ArrayList<Tweet> JSONTweets = Tweet.fromJSONArray(json);

                        //dedupe and set id if there's something in tweets.
                        if (JSONTweets.size() > 0 && !duplicateCheck(JSONTweets, tweets)) {
                            setLastID(JSONTweets.get(JSONTweets.size() - 1));
                            tweets.addAll(JSONTweets);
                            aTweets.notifyDataSetChanged();
                            //add the records to the DB
                            tweetDB.addTweetArray(JSONTweets);
                        } else {
                            Log.e(TAG, "failed dupe Paginate check@onSuccess. Not adding. ");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        //super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, errorResponse.toString());
                    }
                }, maxId);
            }
        }
        else
        {
            Toast.makeText(this, "Cannot retrieve new tweets, no internet connection detected", Toast.LENGTH_LONG).show();
            //read from sql db here.
            tweets.addAll(tweetDB.getAllListItems());
            aTweets.notifyDataSetChanged();

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


    private void showItemDetail(int position){

        FragmentManager fm = getSupportFragmentManager();
        DetailFragment detailFragment = new DetailFragment();

        //setup bundle
        Tweet curTweet = tweets.get(position);
        Bundle mybundle = new Bundle();
        mybundle.putString("userName", curTweet.getUser().getScreenName());
        mybundle.putString("tweetBody", curTweet.getText());
        mybundle.putString("name", curTweet.getUser().getName());
        //mybundle.putString("relativeTime", curTweet.getUser().getName());
        mybundle.putString("profilePicture", curTweet.getUser().getProfileImageUrl());

        if (curTweet.getEntities().getMedia().size() != 0) {
            //grab the first one:
            mybundle.putString("mediaPicture", curTweet.getEntities().getMedia().get(0).getMediaUrl());
        }

        detailFragment.setArguments(mybundle);

        detailFragment.show(fm, "fragment_detail");
    }

    //launch the setting dialog
    private void composeMessage(){
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment composeFragment = new ComposeFragment();
        composeFragment.show(fm, "fragment_compose");
    }

    //display message box if offline.
    private void showOfflineAlert()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Need internet connection to compose a tweet. ");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // Listener helper functions ----------------
    //Append more data into the adapter
    private void customLoadMoreDataFromApi(int page) {
        //paginate only if online.
        if (areWeOnline) {
            populateTimeLine(areWeOnline);
        }
    }

    //clear the adapter and refresh all the data based on pull to refresh.
    private void fetchTimelineAsync(int page) {
        swipeToRefresh = true;
        isInitialQuery = true;
        aTweets.clearData();
        populateTimeLine(areWeOnline);
        swipeToRefresh = false;
        swipeContainer.setRefreshing(false);
    }
}
