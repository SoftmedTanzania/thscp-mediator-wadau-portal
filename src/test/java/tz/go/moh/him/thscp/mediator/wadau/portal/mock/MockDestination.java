package tz.go.moh.him.thscp.mediator.wadau.portal.mock;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.testing.MockHTTPConnector;
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;
import tz.go.moh.him.thscp.mediator.wadau.portal.domain.WadauRequest;
import tz.go.moh.him.thscp.mediator.wadau.portal.orchestrator.DefaultOrchestratorTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static tz.go.moh.him.thscp.mediator.wadau.portal.Constants.DELTA;

/**
 * Represents a mock destination.
 */
public class MockDestination extends MockHTTPConnector {

    /**
     * Initializes a new instance of the {@link MockDestination} class.
     */
    public MockDestination() {
    }

    /**
     * Gets the response.
     *
     * @return Returns the response.
     */
    @Override
    public String getResponse() {
        try {
            return IOUtils.toString(DefaultOrchestratorTest.class.getClassLoader().getResourceAsStream("success_response.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the status code.
     *
     * @return Returns the status code.
     */
    @Override
    public Integer getStatus() {
        return 200;
    }

    /**
     * Gets the HTTP headers.
     *
     * @return Returns the HTTP headers.
     */
    @Override
    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    /**
     * Handles the message.
     *
     * @param msg The message.
     */
    @Override
    public void executeOnReceive(MediatorHTTPRequest msg) {

        InputStream stream = DefaultOrchestratorTest.class.getClassLoader().getResourceAsStream("request.json");

        Assert.assertNotNull(stream);

        JsonSerializer serializer = new JsonSerializer();

        List<WadauRequest> expected;

        try {
            expected = Arrays.asList(serializer.deserialize(IOUtils.toByteArray(stream), WadauRequest[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<WadauRequest> actual = Arrays.asList(serializer.deserialize(msg.getBody(), WadauRequest[].class));

        Assert.assertNotNull(actual);
        Assert.assertNotNull(expected);

        Assert.assertEquals(2, actual.size());
        Assert.assertEquals(expected.size(), actual.size());

        Assert.assertEquals(expected.get(0).getColour(), actual.get(0).getColour());
        Assert.assertEquals(expected.get(0).getLocations().get(0).getDistrict(), actual.get(0).getLocations().get(0).getDistrict());
        Assert.assertEquals(expected.get(0).getLocations().get(0).getLatitude(), actual.get(0).getLocations().get(0).getLatitude(), DELTA);
        Assert.assertEquals(expected.get(0).getLocations().get(0).getLongitude(), actual.get(0).getLocations().get(0).getLongitude(), DELTA);
        Assert.assertEquals(expected.get(0).getLocations().get(1).getDistrict(), actual.get(0).getLocations().get(1).getDistrict());
        Assert.assertEquals(expected.get(0).getLocations().get(1).getLatitude(), actual.get(0).getLocations().get(1).getLatitude(), DELTA);
        Assert.assertEquals(expected.get(0).getLocations().get(1).getLongitude(), actual.get(0).getLocations().get(1).getLongitude(), DELTA);
        Assert.assertEquals(expected.get(0).getPartnerIdentification(), actual.get(0).getPartnerIdentification());
        Assert.assertEquals(expected.get(0).getScope(), actual.get(0).getScope());
        Assert.assertEquals(expected.get(0).getUuid(), actual.get(0).getUuid());

        Assert.assertEquals(expected.get(1).getColour(), actual.get(1).getColour());
        Assert.assertEquals(expected.get(1).getLocations().get(0).getDistrict(), actual.get(1).getLocations().get(0).getDistrict());
        Assert.assertEquals(expected.get(1).getLocations().get(0).getLatitude(), actual.get(1).getLocations().get(0).getLatitude(), DELTA);
        Assert.assertEquals(expected.get(1).getLocations().get(0).getLongitude(), actual.get(1).getLocations().get(0).getLongitude(), DELTA);
        Assert.assertEquals(expected.get(1).getLocations().get(1).getDistrict(), actual.get(1).getLocations().get(1).getDistrict());
        Assert.assertEquals(expected.get(1).getLocations().get(1).getLatitude(), actual.get(1).getLocations().get(1).getLatitude(), DELTA);
        Assert.assertEquals(expected.get(1).getLocations().get(1).getLongitude(), actual.get(1).getLocations().get(1).getLongitude(), DELTA);
        Assert.assertEquals(expected.get(1).getPartnerIdentification(), actual.get(1).getPartnerIdentification());
        Assert.assertEquals(expected.get(1).getScope(), actual.get(1).getScope());
        Assert.assertEquals(expected.get(1).getUuid(), actual.get(1).getUuid());
    }
}
