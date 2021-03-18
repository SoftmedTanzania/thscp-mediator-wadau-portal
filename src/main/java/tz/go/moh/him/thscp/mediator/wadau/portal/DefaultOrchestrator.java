package tz.go.moh.him.thscp.mediator.wadau.portal;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPResponse;
import tz.go.moh.him.mediator.core.domain.ResultDetail;
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;
import tz.go.moh.him.thscp.mediator.wadau.portal.domain.Location;
import tz.go.moh.him.thscp.mediator.wadau.portal.domain.WadauRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Represents a default orchestrator.
 */
public class DefaultOrchestrator extends UntypedActor {

    /**
     * The serializer.
     */
    private static final JsonSerializer serializer = new JsonSerializer();
    /**
     * The mediator config.
     */
    private final MediatorConfig config;
    /**
     * The error message resource.
     */
    private final JSONObject errorMessageResource;
    /**
     * The logger instance.
     */
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    /**
     * The working request.
     */
    private MediatorHTTPRequest workingRequest;

    /**
     * Initializes a new instance of the {@link DefaultOrchestrator} class.
     *
     * @param config The configuration.
     */
    public DefaultOrchestrator(MediatorConfig config) {
        this.config = config;

        try {
            InputStream stream = DefaultOrchestrator.class.getClassLoader().getResourceAsStream("error-messages.json");
            this.errorMessageResource = new JSONObject(IOUtils.toString(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Handles the received message.
     *
     * @param msg The received message.
     */
    @Override
    public void onReceive(Object msg) {
        if (msg instanceof MediatorHTTPRequest) {

            workingRequest = (MediatorHTTPRequest) msg;

            log.info("Received request: " + workingRequest.getHost() + " " + workingRequest.getMethod() + " " + workingRequest.getPath());

            Map<String, String> headers = new HashMap<>();

            headers.put(HttpHeaders.CONTENT_TYPE, "application/json");

            List<Pair<String, String>> parameters = new ArrayList<>();

            String host;
            int port;
            String path;
            String scheme;
            String username;
            String password;

            if (config.getDynamicConfig().isEmpty()) {
                log.debug("Dynamic config is empty, using config from mediator.properties");

                host = config.getProperty("destination.host");
                port = Integer.parseInt(config.getProperty("destination.port"));
                path = config.getProperty("destination.path");
                scheme = config.getProperty("destination.scheme");
            } else {
                log.debug("Using dynamic config");

                JSONObject destinationProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("destinationConnectionProperties");

                host = destinationProperties.getString("destinationHost");
                port = destinationProperties.getInt("destinationPort");
                path = destinationProperties.getString("destinationPath");
                scheme = destinationProperties.getString("destinationScheme");

                if (destinationProperties.has("destinationUsername") && destinationProperties.has("destinationPassword")) {
                    username = destinationProperties.getString("destinationUsername");
                    password = destinationProperties.getString("destinationPassword");

                    // if we have a username and a password
                    // we want to add the username and password as the Basic Auth header in the HTTP request
                    if (username != null && !"".equals(username) && password != null && !"".equals(password)) {
                        String auth = username + ":" + password;
                        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
                        String authHeader = "Basic " + new String(encodedAuth);
                        headers.put(HttpHeaders.AUTHORIZATION, authHeader);
                    }
                }
            }

            List<WadauRequest> wadauRequests = Arrays.asList(serializer.deserialize(workingRequest.getBody(), WadauRequest[].class));
            List<ResultDetail> results = this.validateMessage(wadauRequests);

            // if there are any errors
            // we need to serialize the results and return
            if (results.stream().anyMatch(c -> c.getType() == ResultDetail.ResultsDetailsType.ERROR)) {
                FinishRequest finishRequest = new FinishRequest(serializer.serializeToString(results), "application/json", HttpStatus.SC_BAD_REQUEST);
                ((MediatorHTTPRequest) msg).getRequestHandler().tell(finishRequest, getSelf());
                return;
            }

            host = scheme + "://" + host + ":" + port + path;

            MediatorHTTPRequest request = new MediatorHTTPRequest(workingRequest.getRequestHandler(), getSelf(), host, "POST",
                    host, serializer.serializeToString(wadauRequests), headers, parameters);

            ActorSelection httpConnector = getContext().actorSelection(config.userPathFor("http-connector"));
            httpConnector.tell(request, getSelf());

        } else if (msg instanceof MediatorHTTPResponse) {
            workingRequest.getRequestHandler().tell(((MediatorHTTPResponse) msg).toFinishRequest(), getSelf());
        } else {
            unhandled(msg);
        }
    }

    /**
     * Validates a Wadau request.
     *
     * @param requests The requests.
     * @return Returns a list of result details.
     */
    private List<ResultDetail> validateMessage(List<WadauRequest> requests) {
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