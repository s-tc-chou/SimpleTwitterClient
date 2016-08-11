package com.codepath.apps.SimpleTwitterClient.Activities;

import android.content.DialogInterface;
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

import com.codepath.apps.SimpleTwitterClient.Adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.SimpleTwitterClient.Adapters.TweetsArrayAdapter;
import com.codepath.apps.SimpleTwitterClient.Helpers.DBHelper;
import com.codepath.apps.SimpleTwitterClient.Helpers.ItemClickSupport;
import com.codepath.apps.SimpleTwitterClient.Helpers.Network;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;

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
        Log.d(TAG, "" + areWeOnline);

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
        /*ItemClickSupport.addTo(rvTweets).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //debug only
                Toast.makeText(getActivity(),"clicked " + position, Toast.LENGTH_SHORT).show();
                showItemDetail(position);
            }
        });*/
    }

    // Listener helper functions ----------------
    //Append more data into the adapter
    private void customLoadMoreDataFromApi(int page) {
        //paginate only if online.
        populateTimeline(areWeOnline);
    }

    //TO DO: fix
   /* private void showItemDetail(int position){

        FragmentManager fm = getFragmentManager();
        DetailFragment tweetDetailFragment = new DetailFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        //DetailFragment detailFragment = new DetailFragment();

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

        tweetDetailFragment.setArguments(mybundle);

        fragmentTransaction.replace(R.id.fragment_timeline, tweetDetailFragment);
        fragmentTransaction.commit();

        //tweetDetailFragment.show(fm, "fragment_detail");
    }*/




    public void initSwipeContainer(){
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //fetchTimelineAsync(0);
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    //clear the adapter and refresh all the data based on pull to refresh.
    private void fetchTimelineAsync(int page) {
        ((TimelineActivity)getActivity()).callSetInitialQuery(true);
        aTweets.clearData();
        ((TimelineActivity) getActivity()).callPopulateTimeLine(areWeOnline);
        swipeContainer.setRefreshing(false);
    }


    //addall interface for timeline Activity
    public void addAll(ArrayList<Tweet> newTweets, boolean isInitialQuery, boolean isOnline){
        if (isOnline) {
            if (newTweets.size() > 0 && !duplicateCheck(newTweets, tweets)) {
                setLastID(newTweets.get(newTweets.size() - 1));
                tweets.addAll(newTweets);
                aTweets.notifyDataSetChanged();

                //needs to differentiate between paginate and not.
                if (isInitialQuery) {
                    tweetDB.refresh();
                }
                tweetDB.addTweetArray(newTweets);
            } else {
                Log.e(TAG, "failed dupe check@onSuccess. Not adding. ");
            }
        }
        else
        {
            tweets.addAll(tweetDB.getAllListItems());
            aTweets.notifyDataSetChanged();
        }
    }


    //Misc Helper functions-----------------------------------------------------
    private void setLastID(Tweet tweet) {
        maxId = tweet.getId()-1;
    }

    public long getLastId()
    {
        return maxId;
    }

    public void clearData()
    {
        aTweets.clearData();
    }

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

    protected abstract void populateTimeline(boolean isOnline);


}
