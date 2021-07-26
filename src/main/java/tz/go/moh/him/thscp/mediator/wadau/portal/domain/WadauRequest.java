package tz.go.moh.him.thscp.mediator.wadau.portal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Wadau (Stakeholder) request.
 */
public class WadauRequest {

    /**
     * The name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The description.
     */
    @JsonProperty("description")
    private String description;

    /**
     * The colour.
     */
    @JsonProperty("color")
    private String colour;

    /**
     * The locations.
     */
    @JsonProperty("locations")
    private List<Location> locations;

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
        this.setLocations(new ArrayList<>());
    }

    /**
     * Gets the colour.
     *
     * @return Returns the colour.
     */
    public String getColour() {
        return colour;
    }

    /**
     * Sets the colour.
     *
     * @param colour The colour to set.
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Gets the locations.
     *
     * @return Returns the locations.
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Sets the locations.
     *
     * @param locations The locations to set.
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    /**
     * Gets the partner identification.
     *
     * @return Returns the partner identification.
     */
    public String getPartnerIdentification() {
        return partnerIdentification;
    }

    /**
     * Sets the partner identification.
     *
     * @param partnerIdentification The partner identification to set.
     */
    public void setPartnerIdentification(String partnerIdentification) {
        this.partnerIdentification = partnerIdentification;
    }

    /**
     * Gets the scope.
     *
     * @return Returns the scope.
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets the scope.
     *
     * @param scope The scope to set.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets the UUID.
     *
     * @return Returns the UUID.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the UUID.
     *
     * @param uuid The UUID to set.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the Name.
     *
     * @return Returns the Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name The Name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Description.
     *
     * @return Returns the Description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Description.
     *
     * @param description The Description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
