package com.codepath.apps.SimpleTwitterClient.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.User;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Steve on 8/6/2016.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "TwitterOffline.db";

    private static final String TWEETBODY = "tweet_body";
    private static final String SCREEN_NAME = "screen_name";
    private static final String USER_NAME = "user_name";
    private static final String GET_CREATED_AT = "get_created_at";
    private static final String PROFILE_PICTURE_URL = "user_profile_image_url";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(
                "create table offlineTweets " +
                        "(tweetid integer primary key, tweet_body text not null, get_created_at text, user_name text, screen_name text, user_profile_image_url text)"
        );
    }

    //debug use only.
    public void refresh()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS offlineTweets");
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS offlineTweets");
        onCreate(db);
    }

    //Add and delete methods ------------------------------------------------
    //insert tweet into DB
    public boolean addTweet(Tweet curTweet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tweetid", curTweet.getId());
        contentValues.put("tweet_body", curTweet.getText());
        contentValues.put("get_created_at", curTweet.getCreatedAt());
        contentValues.put("user_name", curTweet.getUser().getName());
        contentValues.put("screen_name", curTweet.getUser().getScreenName());
        contentValues.put("user_profile_image_url", curTweet.getUser().getProfileImageUrl());

        db.insert("offlineTweets", null, contentValues);
        return true;
    }

    public boolean addTweetArray(ArrayList<Tweet> curTweet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < curTweet.size(); i++) {

            //insert only new records.  Note: may be problematic if we delete tweets in the middle.
            // However for now we're cleaning and re-adding all the records on refresh so it shouldn't be a problem.
            if (!idIsDupe(curTweet.get(i).getId())){
                contentValues.put("tweetid", curTweet.get(i).getId());
                contentValues.put("tweet_body", curTweet.get(i).getText());
                contentValues.put("get_created_at", curTweet.get(i).getCreatedAt());
                contentValues.put("user_name", curTweet.get(i).getUser().getName());
                contentValues.put("screen_name", curTweet.get(i).getUser().getScreenName());
                contentValues.put("user_profile_image_url", curTweet.get(i).getUser().getProfileImageUrl());
                db.insert("offlineTweets", null, contentValues);
            }
        }

        return true;
    }

    //delete a tweet from the db, if needed.
    public boolean deleteTweet()
    {
        return true;
    }

    public boolean idIsDupe(long tweetID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from offlineTweets where tweetid = " + tweetID, null );

        Long id = tweetID;
        if (res.getCount() > 0)
        {
            return true;
        }
        return false;
    }

    //Fetch queries----------------------------------------------


    //fetches all the data from the db
    public ArrayList<Tweet> getAllListItems()
    {

        ArrayList<Tweet> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from offlineTweets", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            Tweet curItem = new Tweet();
            User curUser = new User();
            //set user object
            curUser.setName(res.getString(res.getColumnIndex(USER_NAME)));
            curUser.setScreenName(res.getString(res.getColumnIndex(SCREEN_NAME)));
            curUser.setProfileImageUrl(res.getString(res.getColumnIndex(PROFILE_PICTURE_URL)));
            curItem.setUser(curUser);

            //set tweet items.
            curItem.setText(res.getString(res.getColumnIndex(TWEETBODY)));
            //need a relative time here...
            curItem.setCreatedAt(res.getString(res.getColumnIndex(GET_CREATED_AT)));
            curItem.setText(res.getString(res.getColumnIndex(TWEETBODY)));

            //done with setting, add to array.
            array_list.add(curItem);
            res.moveToNext();
        }
        res.close();

        Collections.reverse(array_list);
        return array_list;
    }

}
