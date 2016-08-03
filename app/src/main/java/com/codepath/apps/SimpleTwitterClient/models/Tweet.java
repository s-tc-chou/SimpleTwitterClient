package com.codepath.apps.SimpleTwitterClient.models;

import com.codepath.apps.SimpleTwitterClient.TwitterClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tweet {

    private Object coordinates;
    private Boolean truncated;
    private String createdAt;
    private Boolean favorited;
    private String idStr;
    private Object inReplyToUserIdStr;
    private Entities entities;
    private String text;
    private Object contributors;
    private Long id;
    private Integer retweetCount;
    private Object inReplyToStatusIdStr;
    private Object geo;
    private Boolean retweeted;
    private Object inReplyToUserId;
    private Object place;
    private String source;
    private User user;
    private Object inReplyToScreenName;
    private Object inReplyToStatusId;
    private Boolean possiblySensitive;
//    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    //deserialize JSON and build the tweet object
    public static Tweet fromJSON(JSONObject jsonObject)
    {
        Tweet tweet = new Tweet();

        //extract values from JSON and extract them.
        try {
            tweet.text = jsonObject.getString("text");
            tweet.id = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    //Tweet.fromJSONArray([{...},{...}])
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray)
    {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++)
        {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null)
                {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }


    /**
     *
     * @return
     *     The coordinates
     */
    public Object getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    /**
     *
     * @return
     *     The truncated
     */
    public Boolean getTruncated() {
        return truncated;
    }

    /**
     *
     * @param truncated
     *     The truncated
     */
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    /**
     *
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     *     The favorited
     */
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     *
     * @param favorited
     *     The favorited
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    /**
     *
     * @return
     *     The idStr
     */
    public String getIdStr() {
        return idStr;
    }

    /**
     *
     * @param idStr
     *     The id_str
     */
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    /**
     *
     * @return
     *     The inReplyToUserIdStr
     */
    public Object getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    /**
     *
     * @param inReplyToUserIdStr
     *     The in_reply_to_user_id_str
     */
    public void setInReplyToUserIdStr(Object inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    /**
     *
     * @return
     *     The entities
     */
    public Entities getEntities() {
        return entities;
    }

    /**
     *
     * @param entities
     *     The entities
     */
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    /**
     *
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     *     The contributors
     */
    public Object getContributors() {
        return contributors;
    }

    /**
     *
     * @param contributors
     *     The contributors
     */
    public void setContributors(Object contributors) {
        this.contributors = contributors;
    }

    /**
     *
     * @return
     *     The id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The retweetCount
     */
    public Integer getRetweetCount() {
        return retweetCount;
    }

    /**
     *
     * @param retweetCount
     *     The retweet_count
     */
    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     *
     * @return
     *     The inReplyToStatusIdStr
     */
    public Object getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    /**
     *
     * @param inReplyToStatusIdStr
     *     The in_reply_to_status_id_str
     */
    public void setInReplyToStatusIdStr(Object inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    /**
     *
     * @return
     *     The geo
     */
    public Object getGeo() {
        return geo;
    }

    /**
     *
     * @param geo
     *     The geo
     */
    public void setGeo(Object geo) {
        this.geo = geo;
    }

    /**
     *
     * @return
     *     The retweeted
     */
    public Boolean getRetweeted() {
        return retweeted;
    }

    /**
     *
     * @param retweeted
     *     The retweeted
     */
    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    /**
     *
     * @return
     *     The inReplyToUserId
     */
    public Object getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     *
     * @param inReplyToUserId
     *     The in_reply_to_user_id
     */
    public void setInReplyToUserId(Object inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     *
     * @return
     *     The place
     */
    public Object getPlace() {
        return place;
    }

    /**
     *
     * @param place
     *     The place
     */
    public void setPlace(Object place) {
        this.place = place;
    }

    /**
     *
     * @return
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     *     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     *     The inReplyToScreenName
     */
    public Object getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     *
     * @param inReplyToScreenName
     *     The in_reply_to_screen_name
     */
    public void setInReplyToScreenName(Object inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     *
     * @return
     *     The inReplyToStatusId
     */
    public Object getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     *
     * @param inReplyToStatusId
     *     The in_reply_to_status_id
     */
    public void setInReplyToStatusId(Object inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     *
     * @return
     *     The possiblySensitive
     */
    public Boolean getPossiblySensitive() {
        return possiblySensitive;
    }

    /**
     *
     * @param possiblySensitive
     *     The possibly_sensitive
     */
    public void setPossiblySensitive(Boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    /*public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }*/

}
