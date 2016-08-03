package com.codepath.apps.SimpleTwitterClient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 *
 *
 */



//need to create a new tweet.

// base URL:
//home timeline url: GET
//count=25
// & since_id=1; 1 return all tweets


public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "K8WWeUFy5Q4a6cQzi6Mo9z8W8";
	public static final String REST_CONSUMER_SECRET = "tTCVkldE8sMA8EaTUzHPgwYnEpEZjzHWlECxvRnYqpQNxV2jvL";
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	//everything here is endpoint

	//Get us the home timeline
	public void getHomeTimeline(AsyncHttpResponseHandler handler)
	{
		String apiURL = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("since_id", 1);

		//execute the request
		getClient().get(apiURL,params,handler);

	}

	//compose tweet

	public void composeTweet(AsyncHttpResponseHandler handler)
	{

	}

}