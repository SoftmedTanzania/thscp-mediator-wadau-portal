package tz.go.moh.him.thscp.mediator.wadau.portal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Wadau (Stakeholder) request.
 */
public class WadauRequest {

    /**
     * The colour.
     */
    @JsonProperty("color")
    private String colour;

    /**
     * The list of districts.
     */
    @JsonProperty("districts")
    private String districts;

    /**
     * The list of latitudes.
     */
    @JsonProperty("latitude")
    private String latitude;

    /**
     * The list of longitudes.
     */
    @JsonProperty("longitude")
    private String longitude;

    /**
     * The partner identification.
     */
    @JsonProperty("partnerIdentification")
    private String partnerIdentification;

    /**
     * The scope.
     */
    @JsonProperty("scope")
    private String scope;

    /**
     * The colour.
     */
    @JsonProperty("uuid")
    private String uuid;

    /**
     * Initializes a new instance of the {@link WadauRequest} class.
     */
    public WadauRequest() {
    }

    /**
     * Gets the colour.
     * @return Returns the colour.
     */
    public String getColour() {
        return colour;
    }

    /**
     * Sets the colour.
     * @param colour The colour to set.
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Gets the districts.
     * @return Returns the districts.
     */
    public String getDistricts() {
        return districts;
    }

    /**
     * Sets the districts.
     * @param districts The districts to set.
     */
    public void setDistricts(String districts) {
        this.districts = districts;
    }

    /**
     * Gets the latitude. This is represented as an array but also as a string from the sending system.
     * i.e. "['-4.123', '-5.123', '-6.123']"
     * @return Returns the latitude.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude.
     * @param latitude The latitude to set.
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude. This is represented as an array but also as a string from the sending system.
     * i.e. "['40.123', '39.123', '38.123']"
     * @return Returns the longitude.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude.
     * @param longitude The longitude to set.
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the partner identification.
     * @return Returns the partner identification.
     */
    public String getPartnerIdentification() {
        return partnerIdentification;
    }

    /**
     * Sets the partner identification.
     * @param partnerIdentification The partner identification to set.
     */
    public void setPartnerIdentification(String partnerIdentification) {
        this.partnerIdentification = partnerIdentification;
    }

    /**
     * Gets the scope.
     * @return Returns the scope.
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets the scope.
     * @param scope The scope to set.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets the UUID.
     * @return Returns the UUID.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the UUID.
     * @param uuid The UUID to set.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
