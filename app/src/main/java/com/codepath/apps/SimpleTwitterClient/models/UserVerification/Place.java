
package com.codepath.apps.SimpleTwitterClient.models.UserVerification;

import com.codepath.apps.SimpleTwitterClient.models.UserVerification.BoundingBox;

import java.util.HashMap;
import java.util.Map;

public class Place {

    private Attributes attributes;
    private BoundingBox boundingBox;
    private String country;
    private String countryCode;
    private String fullName;
    private String id;
    private String name;
    private String placeType;
    private String url;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * @return
     *     The boundingBox
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * 
     * @param boundingBox
     *     The bounding_box
     */
    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 
     * @param countryCode
     *     The country_code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 
     * @return
     *     The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * @param fullName
     *     The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
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
     *     The placeType
     */
    public String getPlaceType() {
        return placeType;
    }

    /**
     * 
     * @param placeType
     *     The place_type
     */
    public void setPlaceType(String placeType) {
        this.placeType = placeType;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
