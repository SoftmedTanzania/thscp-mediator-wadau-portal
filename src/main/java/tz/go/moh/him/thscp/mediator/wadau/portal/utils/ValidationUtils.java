package tz.go.moh.him.thscp.mediator.wadau.portal.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import tz.go.moh.him.mediator.core.domain.ResultDetail;
import tz.go.moh.him.thscp.mediator.wadau.portal.domain.Location;
import tz.go.moh.him.thscp.mediator.wadau.portal.domain.WadauRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ValidationUtils {

    /**
     * The error message resource.
     */
    private static JSONObject errorMessageResource;

    /*
      Initializes static members of the {@link ValidationUtils} class.
     */
    static {
        InputStream stream = ValidationUtils.class.getClassLoader().getResourceAsStream("error-messages.json");
        try {
            errorMessageResource = new JSONObject(IOUtils.toString(stream));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Validates a location.
     *
     * @param request The request.
     * @return Returns a list of result details.
     */
    public static List<ResultDetail> validateLocation(WadauRequest request) {
        List<ResultDetail> results = new ArrayList<>();

        for (Location location : request.getLocations()) {
            if (StringUtils.isEmpty(location.getDistrict()) || StringUtils.isWhitespace(location.getDistrict())) {
                results.add(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, String.format(errorMessageResource.getString("NN_ERR01"), "district"), null));
            }

            if (location.getLatitude() < -90 || location.getLatitude() > 90) {
                results.add(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, String.format(errorMessageResource.getString("RANGE_ERR01"), "latitude", location.getLatitude(), -90.0, 90.0), null));
            }

            if (location.getLongitude() < -180 || location.getLongitude() > 180) {
                results.add(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, String.format(errorMessageResource.getString("RANGE_ERR01"), "longitude", location.getLongitude(), -180.0, 180.0), null));
            }
        }

        return results;
    }

    /**
     * Validates a Wadau request.
     *
     * @param requests The requests.
     * @return Returns a list of result details.
     */
    public static List<ResultDetail> validateMessage(List<WadauRequest> requests) {
        ArrayList<ResultDetail> results = new ArrayList<>();

        for (WadauRequest request : requests) {
            if (StringUtils.isEmpty(request.getUuid()) || StringUtils.isWhitespace(request.getUuid())) {
                results.add(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, String.format(errorMessageResource.getString("NN_ERR01"), "uuid"), null));
            }

            if (StringUtils.isEmpty(request.getPartnerIdentification()) || StringUtils.isWhitespace(request.getPartnerIdentification())) {
                results.add(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, String.format(errorMessageResource.getString("NN_ERR01"), "partnerIdentification"), null));
            }

            if (StringUtils.isEmpty(request.getScope()) || StringUtils.isWhitespace(request.getScope())) {
                results.add(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, String.format(errorMessageResource.getString("NN_ERR01"), "scope"), null));
            }

            results.addAll(validateLocation(request));

            // HACK: java has no native way to validate is a string is a valid UUID
            try {
                UUID.fromString(request.getUuid());
            } catch (NullPointerException | IllegalArgumentException e) {
                results.add(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, String.format(errorMessageResource.getString("PARSE_ERR01"), request.getUuid(), UUID.class.getName()), e.getMessage()));
            }
        }

        return results;
    }
}
