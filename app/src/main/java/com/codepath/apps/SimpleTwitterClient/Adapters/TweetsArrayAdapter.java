package com.codepath.apps.SimpleTwitterClient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTwitterClient.Activities.ProfileActivity;
import com.codepath.apps.SimpleTwitterClient.R;
import com.codepath.apps.SimpleTwitterClient.models.Tweets.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


//Take tweet object and turn into views displayed in the recyclerview.
public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {

    private Context mContext;
    private List<Tweet> mTweets;



    //viewholder class extended with a click listener.
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tweetBody;
        public TextView userName;
        public TextView name;
        public TextView relativeTime;
        public ImageView profilePicture;
        public ImageView mediaPicture;


        public OnViewHolderClickListener viewHolderClickListener;

        public ViewHolder(View itemView, OnViewHolderClickListener listener) {
            super(itemView);

            tweetBody = (TextView) itemView.findViewById(R.id.tvTweetBody);
            userName = (TextView) itemView.findViewById(R.id.tvUserName);
            name = (TextView) itemView.findViewById(R.id.tvName);
            relativeTime = (TextView) itemView.findViewById(R.id.tvRelativeTime);
            profilePicture = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            mediaPicture = (ImageView) itemView.findViewById(R.id.ivMediaUrl);


            //set click listener for profile clicks.
            if (listener != null) {
                viewHolderClickListener = listener;
                profilePicture.setOnClickListener(this);
            }
        }

        //sets the listener up with the item click method so we can have data.
        @Override
        public void onClick(View view) {
            int itemPos = getAdapterPosition();
            viewHolderClickListener.onItemClick(view, itemPos);
        }

        //interface for utilizing item click.
        public interface OnViewHolderClickListener {
            void onItemClick(View caller, int position);
        }
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;

    }

    private Context getContext() {
        return mContext;
    }

    //overwrite methods
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetsView = inflater.inflate(R.layout.tweet_layout, parent, false);

        //viewholder with listener
        ViewHolder viewHolder = new ViewHolder(tweetsView, new ViewHolder.OnViewHolderClickListener() {

            @Override
            public void onItemClick(View caller, int position) {
                Tweet tweet = mTweets.get(position);

                //launch profile intent with screename.
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetsArrayAdapter.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        //layout sets.
        TextView tweetBody = holder.tweetBody;
        TextView userName = holder.userName;
        TextView name = holder.name;
        TextView relativeTime = holder.relativeTime;
        ImageView profilePicture = holder.profilePicture;
        ImageView mediaPicture = holder.mediaPicture;

        tweetBody.setText(tweet.getText());
        name.setText(tweet.getUser().getName());
        userName.setText("@" + tweet.getUser().getScreenName());
        relativeTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        //clear out old image for a recycled view
        profilePicture.setImageResource(android.R.color.transparent);

        Glide.with(getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .into(profilePicture);

        if (tweet.getEntities() != null && tweet.getEntities().getMedia().size() != 0) {
            //grab the first one:
            Glide.with(getContext())
                    .load(tweet.getEntities().getMedia().get(0).getMediaUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(mediaPicture);
        } else {
            mediaPicture.setImageResource(0);
        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }


    //parse date
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    //wipe data.
    public void clearData() {
        int size = this.mTweets.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mTweets.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
