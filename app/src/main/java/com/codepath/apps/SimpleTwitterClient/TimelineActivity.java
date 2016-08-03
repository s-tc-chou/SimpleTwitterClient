package com.codepath.apps.SimpleTwitterClient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.SimpleTwitterClient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;


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
        rvTweets.setAdapter(aTweets);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        //retrieve singleton client from twitter application
        client = TwitterApplication.getRestClient();
        populateTimeLine();


    }

    //send api request to get timeline json and fill recyclerview by creating the tweet objects
    private void populateTimeLine()
    {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
               //super.onSuccess(statusCode, headers, response);
               //Deserialize json
               //create model and add to adapter
               //load model into listview

               ArrayList<Tweet> JSONTweets = Tweet.fromJSONArray(json);

               tweets = JSONTweets;
               aTweets.notifyDataSetChanged();

                //test the thing first:  tutorial at 1:07:51
               Log.d("STEVEDEBUG", tweets.toString());

           }

           @Override
           public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
               //super.onFailure(statusCode, headers, throwable, errorResponse);
               Log.d("STEVEDEBUG", errorResponse.toString());
           }

        });
    }
}
