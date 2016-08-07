package com.codepath.apps.SimpleTwitterClient.models.Tweets;

import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entities {

    private List<Object> urls = new ArrayList<Object>();
    private List<Object> hashtags = new ArrayList<Object>();
    private List<Object> userMentions = new ArrayList<Object>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private ArrayList<Media> media = new ArrayList<Media>();


    public static Entities fromJSON(JSONObject jsonObject)
    {
        Entities entity = new Entities();

        try {
            entity.media = Media.fromJSONArray(jsonObject.getJSONArray("media"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entity;
    }

    /**
     *
     * @return
     *     The urls
     */
    public List<Object> getUrls() {
        return urls;
    }

    /**
     *
     * @param urls
     *     The urls
     */
    public void setUrls(List<Object> urls) {
        this.urls = urls;
    }

    /**
     *
     * @return
     *     The hashtags
     */
    public List<Object> getHashtags() {
        return hashtags;
    }

    /**
     *
     * @param hashtags
     *     The hashtags
     */
    public void setHashtags(List<Object> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     *
     * @return
     *     The userMentions
     */
    public List<Object> getUserMentions() {
        return userMentions;
    }

    /**
     *
     * @param userMentions
     *     The user_mentions
     */
    public void setUserMentions(List<Object> userMentions) {
        this.userMentions = userMentions;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /**
     *
     * @return
     *     The media
     */
    public ArrayList<Media> getMedia() {
        return media;
    }

    /**
     *
     * @param media
     *     The media
     */
    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }


}
