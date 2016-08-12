package com.codepath.apps.SimpleTwitterClient.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.TwitterApplication;
import com.codepath.apps.SimpleTwitterClient.TwitterClient;
import com.codepath.apps.SimpleTwitterClient.models.UserVerification.TwitterUserProfile;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class ComposeFragment extends DialogFragment {

    private final String TAG = "compose_fragmentSTEVE:";
    private TextWatcher countdown;
    private int textLength = 0;

    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvCounter) TextView tvCounter;
    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.ivTargetImage) ImageView ivTargetImage;
    @BindView(R.id.btnCancel) TextView btnCancel;
    @BindView(R.id.btnTweet) TextView btnTweet;
    @BindView(R.id.etCompose) TextView etCompose;

    //rest API pieces

    private TwitterClient client;
    private TwitterUserProfile userProfile;

    public ComposeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_compose, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client = TwitterApplication.getRestClient();

        //initialize listeners
        initBtnTweetOnClickListener();
        initBtnCancelOnClickListener();
        initTextWatcher();
        populateProfile();
    }


    //initialize textwatcher for the count down character.
    public void initTextWatcher()
    {
        final int maxTextLength = 140;
        tvCounter.setText(maxTextLength + "/140");

        countdown = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (maxTextLength - charSequence.length() < 0) {
                    tvCounter.setTextColor(Color.RED);
                }
                else{
                    tvCounter.setTextColor(Color.parseColor("#808080"));
                }

                //Log.d(TAG, "onTextChanged: " + (maxTextLength - charSequence.length()));
                tvCounter.setText((maxTextLength - charSequence.length()) + "/140");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        etCompose.addTextChangedListener(countdown);
    }

    public void initBtnTweetOnClickListener() {
        //initialize object.
        btnTweet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postTweet();
             }
        });
    }

    public void initBtnCancelOnClickListener(){
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    //call the rest API to post the tweet.  Dismiss the window if it succeeds.
    private void postTweet() {
        String userTweet = etCompose.getText().toString();
        textLength = userTweet.length();

        if (textLength > 140)
        {
            Toast.makeText(getContext(),"Text length must be under 140 characters!", Toast.LENGTH_LONG).show();
        }
        else{
            client.composeTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //Log.d(TAG, "onSuccess success");
                    onComposeFinishedListener listener = (onComposeFinishedListener) getActivity();
                    listener.onComposeFinish();
                    dismiss();
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
    public interface onComposeFinishedListener {
        void onComposeFinish();
    }

    //populate profile information, like who your user is, his profile image, etc.
    private void populateProfile()
    {
        client.getProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                userProfile = userProfile.fromJSON(json);

                //set various fields now that we successfully pulled data.
                tvName.setText(userProfile.getName());
                tvUserName.setText("@"+userProfile.getScreenName());
                Glide
                        .with(getActivity())
                        .load(userProfile.getProfileImageUrl())
                        .into(ivTargetImage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, errorResponse.toString());
            }
        });
    }
}
