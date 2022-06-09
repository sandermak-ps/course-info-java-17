package com.pluralsight.courseinfo.server;

import com.pluralsight.courseinfo.repository.CourseRepository;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.net.URI;
import java.util.Properties;
import java.util.logging.LogManager;

public class CourseServer {

    // Ensure calls to j.u.logging API are redirected to SLF4J.
    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }

    private static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args) throws Exception {
        Properties serverProperties = new Properties();
        serverProperties.load(CourseServer.class.getResourceAsStream("/server.properties"));
        String databaseFile = serverProperties.getProperty("course-info.database");

        LOG.info("Starting HTTP server with database {}", databaseFile);
        CourseRepository courseRepository = CourseRepository.openCourseRepository(databaseFile);
        startServer(courseRepository);
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @param courseRepository repository to be injected
     * @return Grizzly HTTP server.
     */
    private static HttpServer startServer(CourseRepository courseRepository) {
        final ResourceConfig rc = new ResourceConfig().register(new CourseResource(courseRepository));

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

}

