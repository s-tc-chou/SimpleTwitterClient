package com.codepath.apps.SimpleTwitterClient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.SimpleTwitterClient.models.Tweet;

import java.util.List;

//Take tweet object and turn into views displayed in the recyclerview.
public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder>{

    private Context mContext;
    private List<Tweet> mTweets;

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(View itemView)
        {
            super(itemView);
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

        //inflate a custom view for our tweets.
        View tweetsView = inflater.inflate(R.layout.activity_timeline, parent, false);

        ViewHolder viewHolder = new ViewHolder(tweetsView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetsArrayAdapter.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        //create custom view of tweet feedback.


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
