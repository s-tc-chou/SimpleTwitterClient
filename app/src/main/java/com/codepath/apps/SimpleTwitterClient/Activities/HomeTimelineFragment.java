package com.codepath.apps.SimpleTwitterClient.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private boolean areWeOnline = false;
    private boolean isInitialQuery = true;
    private long maxId = 1;

    Network network;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        network = new Network();
        areWeOnline = network.getOnlineStatus(getActivity());

        //retrieve singleton client from twitter application
        client = TwitterApplication.getRestClient();
        populateTimeline(areWeOnline);
    }

    @Override
    protected void populateTimeline(final boolean isOnline) {
        //If we're offline, populate from SQL db.
        if (isOnline) {
            //if it's the first query, pull everything.
            if (isInitialQuery) {
                client.getInitialHomeTimeline(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        //ArrayList<Tweet> JSONTweets = Tweet.fromJSONArray(json);
                        addAll(Tweet.fromJSONArray(json), isInitialQuery, isOnline);
                        maxId = getLastId();
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
                        addAll(Tweet.fromJSONArray(json), isInitialQuery, isOnline);
                        maxId = getLastId();
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
            Toast.makeText(getActivity(), "Cannot retrieve new tweets, no internet connection detected", Toast.LENGTH_LONG).show();
            //read from sql db here.
            addAll(null, isInitialQuery, isOnline);

        }
    }

    public void setInitialQuery(boolean setter)
    {
        isInitialQuery = setter;
    }

    public boolean getInitialQuery()
    {
        return isInitialQuery;
    }

}
