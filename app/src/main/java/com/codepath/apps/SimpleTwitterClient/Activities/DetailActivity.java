package com.codepath.apps.SimpleTwitterClient.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.TwitterApplication;
import com.codepath.apps.SimpleTwitterClient.TwitterClient;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Steve on 8/14/2016.
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTweetBody)
    TextView tvTweetBody;
    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.ivMediaUrl)
    ImageView ivMediaUrl;
    @BindView(R.id.btnRespond)
    Button btnRespond;
    @BindView(R.id.etResponse)
    EditText etResponse;

    private TwitterClient client;
    private String postID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        client = TwitterApplication.getRestClient();

        populateFields();
        initBtnTweetOnClickListener();
    }

    private void populateFields() {
        //need to get data from intent and pass in.
        String screenName = getIntent().getStringExtra("screen_name");
        String tweetBody = getIntent().getStringExtra("tweet_body");
        String name = getIntent().getStringExtra("name");
        String profilePicture = getIntent().getStringExtra("profile_picture");
        String mediaPicture = getIntent().getStringExtra("media_picture");
        postID = getIntent().getStringExtra("post_id");
        //mybundle.putString("relativeTime", curTweet.getUser().getName());

        tvUserName.setText("@" + screenName);
        tvName.setText(name);
        tvTweetBody.setText(tweetBody);
        etResponse.setText("@" + screenName);

        Glide.with(this)
                .load(profilePicture)
                .into(ivProfileImage);
        if (mediaPicture != null) {
            //Log.d("onBindViewHolder: ", tweet.getEntities().getMedia().get(0).getMediaUrl());
            Glide.with(this)
                    .load(mediaPicture)
                    .placeholder(R.drawable.placeholder)
                    .into(ivMediaUrl);
        }
    }

    public void initBtnTweetOnClickListener() {
        //initialize object.
        btnRespond.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postTweet();
            }
        });
    }


    private void postTweet() {
        String userTweet = etResponse.getText().toString();
        int textLength = userTweet.length();

        if (textLength > 140)
        {
            Toast.makeText(this,"Text length must be under 140 characters!", Toast.LENGTH_LONG).show();
        }
        else{
            client.composeTweetReply(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(DetailActivity.this, errorResponse.toString(), Toast.LENGTH_LONG).show();
                    finish();
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            }, userTweet, postID);
        }
    }

}
