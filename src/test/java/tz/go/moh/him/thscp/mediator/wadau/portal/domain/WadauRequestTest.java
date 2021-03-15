package tz.go.moh.him.thscp.mediator.wadau.portal.domain;

import com.fasterxml.jackson.core.type.TypeReference;
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

/**
 * Contains tests for the {@link WadauRequestTest} class.
 */
public class WadauRequestTest {

    /**
     * Tests the deserialization of an Wadau request.
     */
    @Test
    public void testDeserializeWadauRequest() {
        InputStream stream = WadauRequestTest.class.getClassLoader().getResourceAsStream("request.json");

        Assert.assertNotNull(stream);

        String data;

        try {
            data = IOUtils.toString(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertNotNull(data);

        JsonSerializer serializer = new JsonSerializer();

        List<WadauRequest> requests = Arrays.asList(serializer.deserialize(data, WadauRequest[].class));

        Assert.assertEquals(2, requests.size());

        Assert.assertEquals("61ee3f67-992c-432b-8536-2b89aa3165a8", requests.get(0).getUuid());
        Assert.assertEquals("Rasello team", requests.get(0).getPartnerIdentification());
        Assert.assertEquals("#00fa92", requests.get(0).getColour());
        Assert.assertEquals("this is the final demo", requests.get(0).getScope());
        Assert.assertEquals("[-6.91034,-6.172144,-8.51382,-4.7721]", requests.get(0).getLatitude());
        Assert.assertEquals("[39.26977,35.640203,35.7468,38.42585]", requests.get(0).getLongitude());
        Assert.assertEquals("['Ilala','Kinondoni', 'Kaliua', 'Maswa']", requests.get(0).getDistricts());

        Assert.assertEquals("12345f67-992c-432b-8536-2b89aa3165a8", requests.get(1).getUuid());
        Assert.assertEquals("Test 2", requests.get(1).getPartnerIdentification());
        Assert.assertEquals("#FFFFFF", requests.get(1).getColour());
        Assert.assertEquals("this is the scope", requests.get(1).getScope());
        Assert.assertEquals("[-6.91034,-6.172144,-8.51382,-4.7721]", requests.get(1).getLatitude());
        Assert.assertEquals("[40.26977,35.640203,35.7468,38.42585]", requests.get(1).getLongitude());
        Assert.assertEquals("['test 1','test 2', 'test 3', 'test 4']", requests.get(1).getDistricts());
    }

    /**
     * Tests the serialization of an Wadau request.
     */
    @Test
    public void testSerializeWadauRequest() {

        ArrayList<WadauRequest> requests = new ArrayList<>();

        WadauRequest request = new WadauRequest();

        request.setColour("#000000");
        request.setDistricts("['Test']");
        request.setLatitude("['-5']");
        request.setLongitude("['40']");
        request.setPartnerIdentification("partner");
        request.setScope("scope");
        request.setUuid(UUID.fromString("2602e885-ed1b-4225-ae0b-9763e0658a2c").toString());

        requests.add(request);

        JsonSerializer serializer = new JsonSerializer();

        String actual = serializer.serializeToString(requests);

        Assert.assertNotNull(actual);

        Assert.assertTrue(actual.contains(request.getColour()));
        Assert.assertTrue(actual.contains(request.getDistricts()));
        Assert.assertTrue(actual.contains(request.getLatitude()));
        Assert.assertTrue(actual.contains(request.getLongitude()));
        Assert.assertTrue(actual.contains(request.getPartnerIdentification()));
        Assert.assertTrue(actual.contains(request.getScope()));
        Assert.assertTrue(actual.contains(request.getUuid()));
    }
}
