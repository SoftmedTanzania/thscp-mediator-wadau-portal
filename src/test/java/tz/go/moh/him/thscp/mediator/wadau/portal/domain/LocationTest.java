package tz.go.moh.him.thscp.mediator.wadau.portal.domain;

import org.junit.Test;
import tz.go.moh.him.mediator.core.exceptions.ArgumentException;
import tz.go.moh.him.mediator.core.exceptions.ArgumentNullException;

/**
 * Contains tests for the {@link Location} class.
 */
public class LocationTest {

    /**
     * Tests the validation of parameters during the instantiation of a location.
     */
    @Test(expected = ArgumentNullException.class)
    public void testLocationExceptionAllParameters() {
        new Location(null,null, 91, 181);
    }

    /**
     * Tests the validation of parameters during the instantiation of a location.
     */
    @Test(expected = ArgumentNullException.class)
    public void testLocationExceptionDistrictNull() {
        new Location(null,null, -5, -40);
    }

    /**
     * Tests the validation of parameters during the instantiation of a location.
     */
    @Test(expected = ArgumentNullException.class)
    public void testLocationExceptionDistrictEmpty() {
        new Location("","", -5, -40);
    }

    /**
     * Tests the validation of parameters during the instantiation of a location.
     */
    @Test(expected = ArgumentNullException.class)
    public void testLocationExceptionDistrictWhitespace() {
        new Location("", "    ", -5, -40);
    }

    /**
     * Tests the validation of parameters during the instantiation of a location.
     */
    @Test(expected = ArgumentException.class)
    public void testLocationExceptionLatitude() {
        new Location("test","test", -91, 180);
    }

    /**
     * Tests the validation of parameters during the instantiation of a location.
     */
    @Test(expected = ArgumentException.class)
    public void testLocationExceptionLongitude() {
        new Location("test","test", 90, -181);
    }
}
