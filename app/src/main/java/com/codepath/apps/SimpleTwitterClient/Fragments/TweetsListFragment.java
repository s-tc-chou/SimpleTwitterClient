package com.codepath.apps.SimpleTwitterClient.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTwitterClient.Activities.DetailActivity;
import com.codepath.apps.SimpleTwitterClient.Activities.ProfileActivity;
import com.codepath.apps.SimpleTwitterClient.Activities.TimelineActivity;
import com.codepath.apps.SimpleTwitterClient.Adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.SimpleTwitterClient.Adapters.TweetsArrayAdapter;
import com.codepath.apps.SimpleTwitterClient.Helpers.DBHelper;
import com.codepath.apps.SimpleTwitterClient.Helpers.ItemClickSupport;
import com.codepath.apps.SimpleTwitterClient.Helpers.Network;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;

import org.parceler.Parcels;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Steve on 8/9/2016.
 */
public abstract class TweetsListFragment extends Fragment {

    static final String TAG = "STEVEDEBUG";
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;

    private boolean areWeOnline = false;

    //helper classes
    Network network;
    DBHelper tweetDB;
    private long maxId = 1;

    private ProgressDialog pd;

    //butterknife binds
    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        ButterKnife.bind(this, v);
        //connect adapter to recycler view
        initrvTweets();
        initSwipeContainer();
        initProgressDialog();

        return v;
    }

    //creation lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create array list (data source)
        tweets = new ArrayList<>();
        //construct adapter from datasource
        aTweets = new TweetsArrayAdapter(getActivity(),tweets);
        //create DB
        tweetDB = new DBHelper(getActivity());
        //network helper
        network = new Network();
        areWeOnline = network.getOnlineStatus(getActivity());
    }

    //Initializer helpers ---------------------------------
    //Initialize the recycler view pieces, including scroll listener and click listener.
    public void initrvTweets(){
        rvTweets.setAdapter(aTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });

        //click listener to load details.
        ItemClickSupport.addTo(rvTweets).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //Toast.makeText(getActivity(),"clicked " + position, Toast.LENGTH_SHORT).show();

                Tweet tweet = tweets.get(position);
                //maybe use parcelable to pss in stuff to details.
                Intent i = new Intent(getActivity(), DetailActivity.class);

                //Note: Wanted to use parceler, but don't have to figure out how to deserialize objects inside the parcelable.


                if (tweet.getEntities() != null && tweet.getEntities().getMedia().size() != 0) {
                    //grab the first one:
                    Glide.with(getContext())
                            .load(tweet.getEntities().getMedia().get(0).getMediaUrl());
                }


                i.putExtra("screen_name", tweet.getUser().getScreenName());
                i.putExtra("tweet_body", tweet.getText());
                i.putExtra("name", tweet.getUser().getName());
                i.putExtra("profile_picture", tweet.getUser().getProfileImageUrl());
                i.putExtra("post_id", tweet.getId());

                    //get a media picture if there is one
                if (tweet.getEntities() != null && tweet.getEntities().getMedia().size() != 0) {
                    i.putExtra("media_picture", tweet.getEntities().getMedia().get(0).getMediaUrl());
                }

                startActivity(i);
            }
        });
    }

    // Listener helper functions ----------------
    //Append more data into the adapter
    private void customLoadMoreDataFromApi(int page) {
        //paginate only if online.
        populateTimeline(areWeOnline);
    }
    public void initSwipeContainer(){
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataAsync();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    //addall interface for timeline Activity
    public void addAll(ArrayList<Tweet> newTweets, boolean isInitialQuery, boolean isOnline) {
        if (isOnline) {
            if (newTweets.size() > 0 && !duplicateCheck(newTweets, tweets)) {
                setLastID(newTweets.get(newTweets.size() - 1));
                tweets.addAll(newTweets);
                aTweets.notifyDataSetChanged();

                //needs to differentiate between paginate and not.  Delete everything if it's a refresh.
                if (isInitialQuery) {
                    tweetDB.refresh();
                }
                tweetDB.addTweetArray(newTweets);
            } else {
                Log.i(TAG, "failed dupe check@onSuccess. Not adding. ");
            }
        } else {
            tweets.addAll(tweetDB.getAllListItems());
            aTweets.notifyDataSetChanged();
        }
    }


    //Misc Helper functions-----------------------------------------------------
    private void setLastID(Tweet tweet) {
        maxId = tweet.getId() - 1;
        //Log.d(TAG, "maxID" + (tweet.getId() -1));
    }

    public long getLastId() {
        return maxId;
    }

    public void clearData() {
        aTweets.clearData();
    }

    private void initProgressDialog() {
        //progress dialog setters.
        pd = new ProgressDialog(getContext());
        pd.setTitle("Loading Tweets...");
        pd.setMessage("Please wait while tweets are loaded.");
        pd.setCancelable(false);
        Log.d(TAG, "initProgressDialog: done ");
    }

    public void showProgressDialog(){
        if (pd != null) {
            pd.show();
        }
        else {
            Log.d(TAG, "showProgressDialog: pd was null when trying to show progress");
        }
    }

    public void hideProgressDialog(){
        if (pd != null) {
            pd.dismiss();
        }
        else {
            Log.d(TAG, "showProgressDialog: pd was null when trying to dismiss progress");
        }
    }

    //check for duplicate listview items.
    private boolean duplicateCheck(List<Tweet> newTweet, List<Tweet> baseTweet) {
        boolean isDupe = false;
        //look for just 1 duplicate.  If the record is already in the document list, ignore the whole batch.
        long curID = 0;

        if (newTweet.size() > 0) {
            curID = newTweet.get(0).getId();
        }

        for (int i = 0; i < baseTweet.size(); i++) {
            long looperID = baseTweet.get(i).getId();
            if (looperID == curID) {
                isDupe = true;
                break;
            }
        }
        return isDupe;
    }


    //abstract classes to extend---------------------------------------------------
    protected abstract void populateTimeline(boolean isOnline);

    protected abstract void fetchDataAsync();


}
