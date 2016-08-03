package com.codepath.apps.SimpleTwitterClient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.SimpleTwitterClient.models.Tweet;

import java.util.List;

//Take tweet object and turn into views displayed in the recyclerview.
public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder>{

    private Context mContext;
    private List<Tweet> mTweets;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tweetBody;


        public ViewHolder(View itemView)
        {
            super(itemView);

            tweetBody = (TextView) itemView.findViewById(R.id.tvTweetBody);
        }
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets)
    {
        mContext = context;
        mTweets = tweets;

    }

    private Context getContext()
    {
        return mContext;
    }

    //overwrite methods


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetsView = inflater.inflate(R.layout.tweet_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(tweetsView);

        /*@Override
        public void onItemClick(View caller, int position) {
        //launch something to compose a tweet
            launchComposeActivity();
        }*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetsArrayAdapter.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        //create custom view of tweet feedback.

        TextView textView = holder.tweetBody;
        textView.setText("test");

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
