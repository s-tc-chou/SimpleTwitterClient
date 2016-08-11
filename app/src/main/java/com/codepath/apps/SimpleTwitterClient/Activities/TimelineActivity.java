package com.codepath.apps.SimpleTwitterClient.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.SimpleTwitterClient.Adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.SimpleTwitterClient.Helpers.Network;
import com.codepath.apps.SimpleTwitterClient.R;
import com.facebook.stetho.Stetho;



public class TimelineActivity extends AppCompatActivity implements ComposeFragment.onComposeFinishedListener, DetailFragment.onDetailFinishedListener{

    static final String TAG = "STEVEDEBUG";
    Network network;

    private SmartFragmentStatePagerAdapter adapterViewPager;
    private HomeTimelineFragment homeTimelineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setTitle(R.string.title_activity_timeline);

        network = new Network();

        //get view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set view pager adapter for the pager
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);


        //find the pager sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //attach the tabstrip to the viewpager.
        tabStrip.setViewPager(vpPager);



        //stetho debugger
        Stetho.initializeWithDefaults(this);
    }


    //menu items overrides --------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle action item
        switch (item.getItemId()) {
            case R.id.miCompose:
                if (network.getOnlineStatus(this)) {
                    composeMessage();
                }
                else {
                    showOfflineAlert();
                }
                return true;
            case R.id.miProfile:
                if (network.getOnlineStatus(this)) {
                    onProfileView(item);
                }
                else {
                    showOfflineAlert();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timeline_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //reload everything, which should have new tweet.
    @Override
    public void onComposeFinish()
    {
        //Fragment fragment = adapterViewPager.getRegisteredFragment(0);
        homeTimelineFragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);

        //set is initial query.
        homeTimelineFragment.setInitialQuery(true);
        //Log.d(TAG, "onComposeFinish: clearing data");
        homeTimelineFragment.clearData();
        //might want to check to see if we're online...
        homeTimelineFragment.populateTimeline(network.getOnlineStatus(this));
    }

    @Override
    public void onDetailFinished(boolean hasTweeted)
    {
        homeTimelineFragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);

        if (hasTweeted) {
           homeTimelineFragment.setInitialQuery(true);
            //Log.d(TAG, "onComposeFinish: clearing data");
            homeTimelineFragment.clearData();
            //might want to check to see if we're online...
            homeTimelineFragment.populateTimeline(network.getOnlineStatus(this));
        }
    }

    public void onProfileView(MenuItem mi){
        //launch profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }

    //-------------initialization functions --------------------
    private void composeMessage(){
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment composeFragment = new ComposeFragment();
        composeFragment.show(fm, "fragment_compose");
    }


    //display message box if offline.
    private void showOfflineAlert()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Need internet connection to compose a tweet. ");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void callPopulateTimeLine(boolean isOnline)
    {
        homeTimelineFragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
        homeTimelineFragment.populateTimeline(isOnline);
    }

    public void callSetInitialQuery(boolean setter)
    {
        homeTimelineFragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
        homeTimelineFragment.setInitialQuery(setter);
    }

    //return order of the fragments in the view
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter{
        private String tabTitles[] = {"Home Timeline", "Mentions"};

        //adapter gets the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        //control order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new HomeTimelineFragment();
                //FirstFragment.newInstance(0, "Page # 1");
            }
            else if (position == 1){
                return new MentionsTimelineFragment();
            }
            else
            {
                return null;
            }
        }

        //return tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //how many fragments are here to swipe between
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}

