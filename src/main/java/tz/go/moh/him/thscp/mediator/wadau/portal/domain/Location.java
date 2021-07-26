package tz.go.moh.him.thscp.mediator.wadau.portal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import tz.go.moh.him.mediator.core.exceptions.ArgumentException;
import tz.go.moh.him.mediator.core.exceptions.ArgumentNullException;

/**
 * Represents a location
 */
public class Location {

    /**
     * The region.
     */
    @JsonProperty("region")
    private String region;

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
     * @param region  The region.
     * @param district  The district.
     * @param latitude  The latitude.
     * @param longitude The longitude.
     */
    public Location(String region, String district, double latitude, double longitude) {
        this();

        if (StringUtils.isEmpty(region) || StringUtils.isBlank(region) || StringUtils.isWhitespace(region)) {
            throw new ArgumentNullException("region - Value cannot be null");
        }

        if (StringUtils.isEmpty(district) || StringUtils.isBlank(district) || StringUtils.isWhitespace(district)) {
            throw new ArgumentNullException("district - Value cannot be null");
        }

        if (latitude < -90 || latitude > 90) {
            throw new ArgumentException("latitude - The value must be between -90 and 90");
        }

        if (longitude < -180 || longitude > 180) {
            throw new ArgumentException("longitude - The value must be between -180 and 180");
        }

        this.setDistrict(district);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    /**
     * Gets the region.
     *
     * @return Returns the region.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region.
     *
     * @param region The region to set.
     */
    public void setRegion(String region) {
        this.region = region;
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
