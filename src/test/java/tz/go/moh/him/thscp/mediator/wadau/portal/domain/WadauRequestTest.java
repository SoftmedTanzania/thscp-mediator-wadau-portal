package tz.go.moh.him.thscp.mediator.wadau.portal.domain;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static tz.go.moh.him.thscp.mediator.wadau.portal.TestConstants.DELTA;

/**
 * Contains tests for the {@link WadauRequestTest} class.
 */
public class WadauRequestTest {

    /**
     * Tests the deserialization of an Wadau request.
     */
    @Test
    public void testDeserializeWadauRequest() throws IOException {
        InputStream stream = WadauRequestTest.class.getClassLoader().getResourceAsStream("request.json");

        Assert.assertNotNull(stream);

        String data = IOUtils.toString(stream);

        Assert.assertNotNull(data);

        JsonSerializer serializer = new JsonSerializer();

        List<WadauRequest> requests = Arrays.asList(serializer.deserialize(data, WadauRequest[].class));

        Assert.assertEquals(2, requests.size());

        Assert.assertEquals("61ee3f67-992c-432b-8536-2b89aa3165a8", requests.get(0).getUuid());
        Assert.assertEquals("Rasello team", requests.get(0).getPartnerIdentification());
        Assert.assertEquals("#00fa92", requests.get(0).getColour());
        Assert.assertEquals("this is the final demo", requests.get(0).getScope());
        Assert.assertEquals("Ilala", requests.get(0).getLocations().get(0).getDistrict());
        Assert.assertEquals(-6.91034, requests.get(0).getLocations().get(0).getLatitude(), DELTA);
        Assert.assertEquals(39.26977, requests.get(0).getLocations().get(0).getLongitude(), DELTA);
        Assert.assertEquals("Kinondoni", requests.get(0).getLocations().get(1).getDistrict());
        Assert.assertEquals(-6.172144, requests.get(0).getLocations().get(1).getLatitude(), DELTA);
        Assert.assertEquals(35.640203, requests.get(0).getLocations().get(1).getLongitude(), DELTA);

        Assert.assertEquals("18d00370-dab6-41b3-ba73-f98f73c533c6", requests.get(1).getUuid());
        Assert.assertEquals("Test 2", requests.get(1).getPartnerIdentification());
        Assert.assertEquals("#FFFFFF", requests.get(1).getColour());
        Assert.assertEquals("Scope 2", requests.get(1).getScope());
        Assert.assertEquals("Test District 1", requests.get(1).getLocations().get(0).getDistrict());
        Assert.assertEquals(-5.91034, requests.get(1).getLocations().get(0).getLatitude(), DELTA);
        Assert.assertEquals(40.26977, requests.get(1).getLocations().get(0).getLongitude(), DELTA);
        Assert.assertEquals("Test District 2", requests.get(1).getLocations().get(1).getDistrict());
        Assert.assertEquals(-5.172144, requests.get(1).getLocations().get(1).getLatitude(), DELTA);
        Assert.assertEquals(36.640203, requests.get(1).getLocations().get(1).getLongitude(), DELTA);
    }

    /**
     * Tests the serialization of an Wadau request.
     */
    @Test
    public void testSerializeWadauRequest() {

        ArrayList<WadauRequest> requests = new ArrayList<>();

        WadauRequest request = new WadauRequest();

        request.setColour("#000000");
        request.getLocations().add(new Location("Test","Test", -5, 40));
        request.setPartnerIdentification("partner");
        request.setScope("scope");
        request.setUuid(UUID.fromString("2602e885-ed1b-4225-ae0b-9763e0658a2c").toString());

        requests.add(request);

        JsonSerializer serializer = new JsonSerializer();

        String actual = serializer.serializeToString(requests);

        Assert.assertNotNull(actual);

        Assert.assertTrue(actual.contains(request.getColour()));
        Assert.assertTrue(actual.contains(request.getLocations().get(0).getDistrict()));
        Assert.assertTrue(actual.contains(String.valueOf(request.getLocations().get(0).getLatitude())));
        Assert.assertTrue(actual.contains(String.valueOf(request.getLocations().get(0).getLongitude())));
        Assert.assertTrue(actual.contains(request.getPartnerIdentification()));
        Assert.assertTrue(actual.contains(request.getScope()));
        Assert.assertTrue(actual.contains(request.getUuid()));
    }
}
