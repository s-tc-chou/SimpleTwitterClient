package com.codepath.apps.SimpleTwitterClient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

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
	public static final String REST_CONSUMER_KEY = "NRSsJXZNWySaj9rSU6TYNHCm0";
	public static final String REST_CONSUMER_SECRET = "5IJGqk8eI3YSTPmz9vTmpsgcGMiJefBVlffWATumrC17FXcQGU";
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)


	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	//everything here is endpoint

	//Get us the home timeline
	public void getInitialHomeTimeline(AsyncHttpResponseHandler handler)
	{
		String apiURL = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",10);
		params.put("since_id", 1);

		//execute the request
		getClient().get(apiURL,params,handler);
	}

	//paginating timeline
	public void getPaginatedHomeTimeline(AsyncHttpResponseHandler handler, long maxId)
	{
		String apiURL = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",10);
		params.put("since_id", 1);
		params.put("max_id", maxId);

		//execute the request
		getClient().get(apiURL,params,handler);
	}

	//Get us the current user profile.
	public void getProfile(AsyncHttpResponseHandler handler)
	{
		String apiURL = getApiUrl("account/verify_credentials.json");

		//execute the request
		getClient().get(apiURL,handler);
	}



	//compose tweet
	public void composeTweet(AsyncHttpResponseHandler handler, String text)
	{
		String apiURL = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status",text);

		//execute the request
		getClient().post(apiURL,params,handler);
	}

}