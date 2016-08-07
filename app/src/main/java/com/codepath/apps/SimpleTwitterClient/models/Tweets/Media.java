package com.codepath.apps.SimpleTwitterClient.models.Tweets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steve on 8/6/2016.
 */
public class Media {

    private Integer id;
    private String idStr;
    private List<Integer> indices = new ArrayList<Integer>();
    private String mediaUrl;
    private String mediaUrlHttps;
    private String url;
    private String displayUrl;
    private String expandedUrl;
    private String type;
    //private Sizes sizes;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public static Media fromJSON(JSONObject json)
    {
        Media media = new Media();

        try {
            media.setMediaUrl(json.getString("media_url"));
            media.setMediaUrlHttps(json.getString("media_url_https"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return media;
    }

    public static ArrayList<Media> fromJSONArray(JSONArray jsonArray)
    {
        ArrayList<Media> media = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++)
        {
            try {
                JSONObject mediaJson = jsonArray.getJSONObject(i);
                Media curMedia = Media.fromJSON(mediaJson);
                if(media != null)
                {
                    media.add(curMedia);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return media;
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
     *     The indices
     */
    public List<Integer> getIndices() {
        return indices;
    }

    /**
     *
     * @param indices
     *     The indices
     */
    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    /**
     *
     * @return
     *     The mediaUrl
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     *
     * @param mediaUrl
     *     The media_url
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /**
     *
     * @return
     *     The mediaUrlHttps
     */
    public String getMediaUrlHttps() {
        return mediaUrlHttps;
    }

    /**
     *
     * @param mediaUrlHttps
     *     The media_url_https
     */
    public void setMediaUrlHttps(String mediaUrlHttps) {
        this.mediaUrlHttps = mediaUrlHttps;
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
     *     The displayUrl
     */
    public String getDisplayUrl() {
        return displayUrl;
    }

    /**
     *
     * @param displayUrl
     *     The display_url
     */
    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    /**
     *
     * @return
     *     The expandedUrl
     */
    public String getExpandedUrl() {
        return expandedUrl;
    }

    /**
     *
     * @param expandedUrl
     *     The expanded_url
     */
    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /*    public Sizes getSizes() {
        return sizes;
    }


    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }*/

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
