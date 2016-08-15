package com.codepath.apps.SimpleTwitterClient.models.Tweets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Tweet {

//    private Object coordinates;
    private Boolean truncated;
    private String createdAt;
    private Boolean favorited;
    private String idStr;
//    private Object inReplyToUserIdStr;
    private Entities entities;
    private String text;
//    private Object contributors;
    private Long id;
    private Integer retweetCount;
//    private Object inReplyToStatusIdStr;
//    private Object geo;
    private Boolean retweeted;
//    private Object inReplyToUserId;
//    private Object place;
    private String source;
    private User user;
//    private Object inReplyToScreenName;
//    private Object inReplyToStatusId;
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
            tweet.entities = Entities.fromJSON(jsonObject.getJSONObject("entities"));

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

}
