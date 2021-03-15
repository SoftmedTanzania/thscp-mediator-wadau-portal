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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDistricts() {
        return districts;
    }

    public void setDistricts(String districts) {
        this.districts = districts;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPartnerIdentification() {
        return partnerIdentification;
    }

    public void setPartnerIdentification(String partnerIdentification) {
        this.partnerIdentification = partnerIdentification;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
