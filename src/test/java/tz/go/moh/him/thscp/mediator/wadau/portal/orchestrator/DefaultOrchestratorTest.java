package tz.go.moh.him.thscp.mediator.wadau.portal.orchestrator;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.testing.MockLauncher;
import org.openhim.mediator.engine.testing.TestingUtils;
import tz.go.moh.him.mediator.core.domain.ResultDetail;
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;
import tz.go.moh.him.thscp.mediator.wadau.portal.DefaultOrchestrator;
import tz.go.moh.him.thscp.mediator.wadau.portal.mock.MockDestination;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * Contains tests for the {@link DefaultOrchestrator} class.
 */
public class DefaultOrchestratorTest {

    /**
     * Represents the configuration.
     */
    private static MediatorConfig configuration;

    /**
     * Represents the system actor.
     */
    private static ActorSystem system;

    /**
     * Represents the orchestrator.
     */
    private final ActorRef orchestrator = system.actorOf(Props.create(DefaultOrchestrator.class, configuration));

    /**
     * Runs cleanup after class execution.
     */
    @AfterClass
    public static void afterClass() {
        TestingUtils.clearRootContext(system, configuration.getName());
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    /**
     * Runs initialization before each class execution.
     */
    @BeforeClass
    public static void beforeClass() {
        try {
            configuration = loadConfig(null);
            system = ActorSystem.create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the mediator configuration.
     *
     * @param configPath The configuration path.
     * @return Returns the mediator configuration.
     */
    public static MediatorConfig loadConfig(String configPath) {
        MediatorConfig config = new MediatorConfig();

        try {
            if (configPath != null) {
                Properties props = new Properties();
                File conf = new File(configPath);
                InputStream in = FileUtils.openInputStream(conf);
                props.load(in);
                IOUtils.closeQuietly(in);

                config.setProperties(props);
            } else {
                config.setProperties("mediator.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        config.setName(config.getProperty("mediator.name"));
        config.setServerHost(config.getProperty("mediator.host"));
        config.setServerPort(Integer.parseInt(config.getProperty("mediator.port")));
        config.setRootTimeout(Integer.parseInt(config.getProperty("mediator.timeout")));

        config.setCoreHost(config.getProperty("core.host"));
        config.setCoreAPIUsername(config.getProperty("core.api.user"));
        config.setCoreAPIPassword(config.getProperty("core.api.password"));

        config.setCoreAPIPort(Integer.parseInt(config.getProperty("core.api.port")));
        config.setHeartbeatsEnabled(true);

        return config;
    }

    /**
     * Runs cleanup after each test execution.
     */
    @After
    public void after() {
        system = ActorSystem.create();
    }

    /**
     * Runs initialization before each test execution.
     */
    @Before
    public void before() {
        List<MockLauncher.ActorToLaunch> actorsToLaunch = new LinkedList<>();

        actorsToLaunch.add(new MockLauncher.ActorToLaunch("http-connector", MockDestination.class));

        TestingUtils.launchActors(system, configuration.getName(), actorsToLaunch);
    }

    /**
     * Tests the mediator.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testWadauRequest() throws Exception {
        new JavaTestKit(system) {{

            InputStream stream = DefaultOrchestratorTest.class.getClassLoader().getResourceAsStream("request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/thscp",
                    IOUtils.toString(stream),
                    Collections.singletonMap("Content-Type", "application/json"),
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out = new ReceiveWhile<Object>(Object.class, duration("3 seconds")) {
                @Override
                protected Object match(Object msg) {
                    if (msg instanceof FinishRequest) {
                        return msg;
                    }
                    throw noMatch();
                }
            }.get();

            InputStream responseStream = DefaultOrchestratorTest.class.getClassLoader().getResourceAsStream("success_response.json");

            Assert.assertNotNull(responseStream);

            String expectedResponse = IOUtils.toString(responseStream);

            Assert.assertNotNull(expectedResponse);

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            Assert.assertTrue(Arrays.stream(out).allMatch(c -> (c instanceof FinishRequest) && JsonParser.parseString(expectedResponse).equals(JsonParser.parseString(((FinishRequest) c).getResponse()))));
        }};
    }

    /**
     * Tests the mediator.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testWadauRequestBadRequest() throws Exception {
        new JavaTestKit(system) {{

            InputStream stream = DefaultOrchestratorTest.class.getClassLoader().getResourceAsStream("bad_request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/thscp",
                    IOUtils.toString(stream),
                    Collections.singletonMap("Content-Type", "application/json"),
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out = new ReceiveWhile<Object>(Object.class, duration("3 seconds")) {
                @Override
                protected Object match(Object msg) {
                    if (msg instanceof FinishRequest) {
                        return msg;
                    }
                    throw noMatch();
                }
            }.get();

            Assert.assertNotNull(out);
            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            Assert.assertTrue(Arrays.stream(out).allMatch(c -> (c instanceof FinishRequest) && ((FinishRequest) c).getResponse().contains("ERROR") && ((FinishRequest) c).getResponseStatus() == 400));

            Assert.assertEquals(1, out.length);

            FinishRequest finishRequest = (FinishRequest) out[0];

            JsonSerializer serializer = new JsonSerializer();

            List<ResultDetail> resultDetails = Arrays.asList(serializer.deserialize(finishRequest.getResponse(), ResultDetail[].class));

            Assert.assertEquals(23, resultDetails.size());
        }};
    }
}