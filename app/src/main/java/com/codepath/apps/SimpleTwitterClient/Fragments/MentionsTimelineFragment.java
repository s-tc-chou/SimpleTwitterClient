package com.codepath.apps.SimpleTwitterClient.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.SimpleTwitterClient.Activities.TimelineActivity;
import com.codepath.apps.SimpleTwitterClient.Fragments.TweetsListFragment;
import com.codepath.apps.SimpleTwitterClient.Helpers.Network;
import com.codepath.apps.SimpleTwitterClient.TwitterApplication;
import com.codepath.apps.SimpleTwitterClient.TwitterClient;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Steve on 8/10/2016.
 */
public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private boolean areWeOnline = false;
    private boolean isInitialQuery = true;
    private long maxId = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        network = new Network();
        areWeOnline = network.getOnlineStatus(getActivity());

        //retrieve singleton client from twitter application
        client = TwitterApplication.getRestClient();
        populateTimeline(areWeOnline);
    }

    //popoulate the timeline.
    @Override
    protected void populateTimeline(final boolean isOnline) {
        //If we're offline, populate from SQL db.
        //showProgressDialog();
        if (isOnline) {
            //if it's the first query, pull everything.
            if (isInitialQuery) {
                client.getMentionsTimeline(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        addAll(Tweet.fromJSONArray(json), isInitialQuery, isOnline);
                        maxId = getLastId();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.e("Mentions Failed", Log.getStackTraceString(throwable));
                    }
                });
                isInitialQuery = false;
            } else {
                client.getPaginatedMentionsTimeline(new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        addAll(Tweet.fromJSONArray(json), isInitialQuery, isOnline);
                        maxId = getLastId();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.e("PaginateMentions Failed", Log.getStackTraceString(throwable));
                    }
                }, maxId);
            }
        } else {
            Toast.makeText(getActivity(), "Cannot retrieve new tweets, no internet connection detected", Toast.LENGTH_LONG).show();
            //read from sql db here.
            addAll(null, isInitialQuery, isOnline);
        }
        //hideProgressDialog();
    }

    //used for refresh.
    @Override
    protected void fetchDataAsync() {
        isInitialQuery = true;
        clearData();
        populateTimeline(areWeOnline);
        swipeContainer.setRefreshing(false);
    }
}
