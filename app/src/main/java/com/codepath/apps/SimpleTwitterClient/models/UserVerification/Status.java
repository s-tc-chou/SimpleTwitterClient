
package com.codepath.apps.SimpleTwitterClient.models.UserVerification;

import com.codepath.apps.SimpleTwitterClient.models.UserVerification.Coordinates;
import com.codepath.apps.SimpleTwitterClient.models.UserVerification.Geo;
import com.codepath.apps.SimpleTwitterClient.models.UserVerification.Place;

import java.util.HashMap;
import java.util.Map;

public class Status {

    private Object contributors;
    private Coordinates coordinates;
    private String createdAt;
    private Boolean favorited;
    private Geo geo;
    private Integer id;
    private String idStr;
    private String inReplyToScreenName;
    private Integer inReplyToStatusId;
    private String inReplyToStatusIdStr;
    private Integer inReplyToUserId;
    private String inReplyToUserIdStr;
    private Place place;
    private Integer retweetCount;
    private Boolean retweeted;
    private String source;
    private String text;
    private Boolean truncated;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * 
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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
     *     The geo
     */
    public Geo getGeo() {
        return geo;
    }

    /**
     * 
     * @param geo
     *     The geo
     */
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
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
     *     The inReplyToScreenName
     */
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     * 
     * @param inReplyToScreenName
     *     The in_reply_to_screen_name
     */
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     * 
     * @return
     *     The inReplyToStatusId
     */
    public Integer getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * 
     * @param inReplyToStatusId
     *     The in_reply_to_status_id
     */
    public void setInReplyToStatusId(Integer inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     * 
     * @return
     *     The inReplyToStatusIdStr
     */
    public String getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    /**
     * 
     * @param inReplyToStatusIdStr
     *     The in_reply_to_status_id_str
     */
    public void setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    /**
     * 
     * @return
     *     The inReplyToUserId
     */
    public Integer getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * 
     * @param inReplyToUserId
     *     The in_reply_to_user_id
     */
    public void setInReplyToUserId(Integer inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     * 
     * @return
     *     The inReplyToUserIdStr
     */
    public String getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    /**
     * 
     * @param inReplyToUserIdStr
     *     The in_reply_to_user_id_str
     */
    public void setInReplyToUserIdStr(String inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    /**
     * 
     * @return
     *     The place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * 
     * @param place
     *     The place
     */
    public void setPlace(Place place) {
        this.place = place;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
