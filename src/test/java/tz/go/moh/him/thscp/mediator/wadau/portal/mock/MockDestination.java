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
        return null;
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

//        Assert.assertEquals(expected.getCommonFacilityName(), actual.getCommonFacilityName());
//        Assert.assertEquals(expected.getCouncil(), actual.getCouncil());
//        Assert.assertEquals(expected.getCouncilCode(), actual.getCouncilCode());
//        Assert.assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
//        Assert.assertEquals(expected.getDistrict(), actual.getDistrict());
//        Assert.assertEquals(expected.getFacilityIdNumber(), actual.getFacilityIdNumber());
//        Assert.assertEquals(expected.getFacilityType(), actual.getFacilityType());
    }
}
