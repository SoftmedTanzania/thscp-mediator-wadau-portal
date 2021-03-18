package tz.go.moh.him.thscp.mediator.wadau.portal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a location
 */
public class Location {

    /**
     * The district.
     */
    @JsonProperty("district")
    private String district;

    /**
     * The latitude.
     */
    @JsonProperty("latitude")
    private double latitude;

    /**
     * The longitude.
     */
    @JsonProperty("longitude")
    private double longitude;

    /**
     * Initializes a new instance of the {@link Location} class.
     */
    public Location() {

    }

    /**
     * Initializes a new instance of the {@link Location} class.
     *
     * @param district  The district.
     * @param latitude  The latitude.
     * @param longitude The longitude.
     */
    public Location(String district, double latitude, double longitude) {
        this.setDistrict(district);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    /**
     * Gets the district.
     *
     * @return Returns the district.
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the district.
     *
     * @param district The district to set.
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * Gets the latitude.
     *
     * @return Returns the latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude.
     *
     * @param latitude The latitude to set.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude.
     *
     * @return Returns the longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude.
     *
     * @param longitude The longitude to set.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
