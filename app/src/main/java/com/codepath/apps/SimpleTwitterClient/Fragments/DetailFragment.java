package com.codepath.apps.SimpleTwitterClient.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.TwitterApplication;
import com.codepath.apps.SimpleTwitterClient.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


//Fragment detail for timeline.
public class DetailFragment extends DialogFragment {

    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvTweetBody) TextView tvTweetBody;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.ivMediaUrl) ImageView ivMediaUrl;
    @BindView(R.id.btnRespond) Button btnRespond;
    @BindView(R.id.etResponse) EditText etResponse;

    private TwitterClient client;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.activity_detail, container, false);
        client = TwitterApplication.getRestClient();

        ButterKnife.bind(this, view);
        initBtnTweetOnClickListener();
        populateFields();
        return view;
    }

    //populates the fields using data from bundle.
    private void populateFields()
    {
        //grab data out of bundle and set the detailed fields.
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            String screenName = bundle.getString("userName");
            String tweetBody = bundle.getString("tweetBody");
            String name = bundle.getString("name");
            String profilePicture = bundle.getString("profilePicture");
            String mediaPicture = bundle.getString("mediaPicture");
            //mybundle.putString("relativeTime", curTweet.getUser().getName());

            tvUserName.setText("@"+screenName);
            tvName.setText(name);
            tvTweetBody.setText(tweetBody);

            Glide.with(getContext())
                    .load(profilePicture)
                    .into(ivProfileImage);
            if (mediaPicture != null) {
                //grab the first one:
                //Log.d("onBindViewHolder: ", tweet.getEntities().getMedia().get(0).getMediaUrl());
                Glide.with(getContext())
                        .load(mediaPicture)
                        .placeholder(R.drawable.placeholder)
                        .into(ivMediaUrl);
            }
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
            Toast.makeText(getContext(),"Text length must be under 140 characters!", Toast.LENGTH_LONG).show();
        }
        else{
            client.composeTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //Log.d(TAG, "onSuccess success");
                    onDetailFinishedListener listener = (onDetailFinishedListener) getActivity();
                    listener.onDetailFinished(true);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(getContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            }, userTweet);
        }
    }

    //interface for killing fragment.
    public interface onDetailFinishedListener {
        void onDetailFinished(boolean hasTweeted);
    }
}
