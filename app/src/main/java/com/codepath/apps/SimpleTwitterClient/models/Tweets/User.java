package com.codepath.apps.SimpleTwitterClient.models.Tweets;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String name;
    private String profileSidebarFillColor;
    private Boolean profileBackgroundTile;
    private String profileSidebarBorderColor;
    private String profileImageUrl;
    private String createdAt;
    private String location;
    private Boolean followRequestSent;
    private String idStr;
    private Boolean isTranslator;
    private String profileLinkColor;
    private Entities_ entities;
    private Boolean defaultProfile;
    private String url;
    private Boolean contributorsEnabled;
    private Integer favouritesCount;
    private Integer utcOffset;
    private String profileImageUrlHttps;
    private Long id;
    private Integer listedCount;
    private Boolean profileUseBackgroundImage;
    private String profileTextColor;
    private Integer followersCount;
    private String lang;
    private Boolean _protected;
    private Boolean geoEnabled;
    private Boolean notifications;
    private String description;
    private String profileBackgroundColor;
    private Boolean verified;
    private String timeZone;
    private String profileBackgroundImageUrlHttps;
    private Integer statusesCount;
    private String profileBackgroundImageUrl;
    private Boolean defaultProfileImage;
    private Integer friendsCount;
    private Boolean following;
    private Boolean showAllInlineMedia;
    private String screenName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    //deserialize the user json into user object

    public static User fromJSON(JSONObject json)
    {
        User user = new User();

        try {
            user.name = json.getString("name");
            user.id = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.description = json.getString("description");
            user.followersCount = json.getInt("followers_count");
            user.friendsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }


    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The profileSidebarFillColor
     */
    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }

    /**
     *
     * @param profileSidebarFillColor
     *     The profile_sidebar_fill_color
     */
    public void setProfileSidebarFillColor(String profileSidebarFillColor) {
        this.profileSidebarFillColor = profileSidebarFillColor;
    }

    /**
     *
     * @return
     *     The profileBackgroundTile
     */
    public Boolean getProfileBackgroundTile() {
        return profileBackgroundTile;
    }

    /**
     *
     * @param profileBackgroundTile
     *     The profile_background_tile
     */
    public void setProfileBackgroundTile(Boolean profileBackgroundTile) {
        this.profileBackgroundTile = profileBackgroundTile;
    }

    /**
     *
     * @return
     *     The profileSidebarBorderColor
     */
    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }

    /**
     *
     * @param profileSidebarBorderColor
     *     The profile_sidebar_border_color
     */
    public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
        this.profileSidebarBorderColor = profileSidebarBorderColor;
    }

    /**
     *
     * @return
     *     The profileImageUrl
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     *
     * @param profileImageUrl
     *     The profile_image_url
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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
     *     The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     *     The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     *     The followRequestSent
     */
    public Boolean getFollowRequestSent() {
        return followRequestSent;
    }

    /**
     *
     * @param followRequestSent
     *     The follow_request_sent
     */
    public void setFollowRequestSent(Boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
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
     *     The isTranslator
     */
    public Boolean getIsTranslator() {
        return isTranslator;
    }

    /**
     *
     * @param isTranslator
     *     The is_translator
     */
    public void setIsTranslator(Boolean isTranslator) {
        this.isTranslator = isTranslator;
    }

    /**
     *
     * @return
     *     The profileLinkColor
     */
    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    /**
     *
     * @param profileLinkColor
     *     The profile_link_color
     */
    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }

    /**
     *
     * @return
     *     The entities
     */
    public Entities_ getEntities() {
        return entities;
    }

    /**
     *
     * @param entities
     *     The entities
     */
    public void setEntities(Entities_ entities) {
        this.entities = entities;
    }

    /**
     *
     * @return
     *     The defaultProfile
     */
    public Boolean getDefaultProfile() {
        return defaultProfile;
    }

    /**
     *
     * @param defaultProfile
     *     The default_profile
     */
    public void setDefaultProfile(Boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    /**
     *
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     *     The contributorsEnabled
     */
    public Boolean getContributorsEnabled() {
        return contributorsEnabled;
    }

    /**
     *
     * @param contributorsEnabled
     *     The contributors_enabled
     */
    public void setContributorsEnabled(Boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    /**
     *
     * @return
     *     The favouritesCount
     */
    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    /**
     *
     * @param favouritesCount
     *     The favourites_count
     */
    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    /**
     *
     * @return
     *     The utcOffset
     */
    public Integer getUtcOffset() {
        return utcOffset;
    }

    /**
     *
     * @param utcOffset
     *     The utc_offset
     */
    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    /**
     *
     * @return
     *     The profileImageUrlHttps
     */
    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

    /**
     *
     * @param profileImageUrlHttps
     *     The profile_image_url_https
     */
    public void setProfileImageUrlHttps(String profileImageUrlHttps) {
        this.profileImageUrlHttps = profileImageUrlHttps;
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
     *     The listedCount
     */
    public Integer getListedCount() {
        return listedCount;
    }

    /**
     *
     * @param listedCount
     *     The listed_count
     */
    public void setListedCount(Integer listedCount) {
        this.listedCount = listedCount;
    }

    /**
     *
     * @return
     *     The profileUseBackgroundImage
     */
    public Boolean getProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }

    /**
     *
     * @param profileUseBackgroundImage
     *     The profile_use_background_image
     */
    public void setProfileUseBackgroundImage(Boolean profileUseBackgroundImage) {
        this.profileUseBackgroundImage = profileUseBackgroundImage;
    }

    /**
     *
     * @return
     *     The profileTextColor
     */
    public String getProfileTextColor() {
        return profileTextColor;
    }

    /**
     *
     * @param profileTextColor
     *     The profile_text_color
     */
    public void setProfileTextColor(String profileTextColor) {
        this.profileTextColor = profileTextColor;
    }

    /**
     *
     * @return
     *     The followersCount
     */
    public Integer getFollowersCount() {
        return followersCount;
    }

    /**
     *
     * @param followersCount
     *     The followers_count
     */
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    /**
     *
     * @return
     *     The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang
     *     The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return
     *     The _protected
     */
    public Boolean getProtected() {
        return _protected;
    }

    /**
     *
     * @param _protected
     *     The protected
     */
    public void setProtected(Boolean _protected) {
        this._protected = _protected;
    }

    /**
     *
     * @return
     *     The geoEnabled
     */
    public Boolean getGeoEnabled() {
        return geoEnabled;
    }

    /**
     *
     * @param geoEnabled
     *     The geo_enabled
     */
    public void setGeoEnabled(Boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    /**
     *
     * @return
     *     The notifications
     */
    public Boolean getNotifications() {
        return notifications;
    }

    /**
     *
     * @param notifications
     *     The notifications
     */
    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    /**
     *
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     *     The profileBackgroundColor
     */
    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    /**
     *
     * @param profileBackgroundColor
     *     The profile_background_color
     */
    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    /**
     *
     * @return
     *     The verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     *     The verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     *     The timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     *
     * @param timeZone
     *     The time_zone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     *
     * @return
     *     The profileBackgroundImageUrlHttps
     */
    public String getProfileBackgroundImageUrlHttps() {
        return profileBackgroundImageUrlHttps;
    }

    /**
     *
     * @param profileBackgroundImageUrlHttps
     *     The profile_background_image_url_https
     */
    public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
        this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
    }

    /**
     *
     * @return
     *     The statusesCount
     */
    public Integer getStatusesCount() {
        return statusesCount;
    }

    /**
     *
     * @param statusesCount
     *     The statuses_count
     */
    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    /**
     *
     * @return
     *     The profileBackgroundImageUrl
     */
    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    /**
     *
     * @param profileBackgroundImageUrl
     *     The profile_background_image_url
     */
    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    /**
     *
     * @return
     *     The defaultProfileImage
     */
    public Boolean getDefaultProfileImage() {
        return defaultProfileImage;
    }

    /**
     *
     * @param defaultProfileImage
     *     The default_profile_image
     */
    public void setDefaultProfileImage(Boolean defaultProfileImage) {
        this.defaultProfileImage = defaultProfileImage;
    }

    /**
     *
     * @return
     *     The friendsCount
     */
    public Integer getFriendsCount() {
        return friendsCount;
    }

    /**
     *
     * @param friendsCount
     *     The friends_count
     */
    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    /**
     *
     * @return
     *     The following
     */
    public Boolean getFollowing() {
        return following;
    }

    /**
     *
     * @param following
     *     The following
     */
    public void setFollowing(Boolean following) {
        this.following = following;
    }

    /**
     *
     * @return
     *     The showAllInlineMedia
     */
    public Boolean getShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    /**
     *
     * @param showAllInlineMedia
     *     The show_all_inline_media
     */
    public void setShowAllInlineMedia(Boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }

    /**
     *
     * @return
     *     The screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     *
     * @param screenName
     *     The screen_name
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
