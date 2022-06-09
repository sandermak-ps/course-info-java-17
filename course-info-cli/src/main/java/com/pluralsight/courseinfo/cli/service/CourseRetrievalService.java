package com.pluralsight.courseinfo.cli.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CourseRetrievalService {

    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";
    private static final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<PluralsightCourse> getPluralsightCoursesFor(String authorName) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(PS_URI.formatted(authorName)))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()) {
                case 200 -> {
                    CollectionType resultType = OBJECT_MAPPER.getTypeFactory()
                            .constructCollectionType(List.class, PluralsightCourse.class);
                    yield OBJECT_MAPPER.readValue(response.body(), resultType);
                }
                case 404 -> List.of();
                default -> throw new RuntimeException("Call failed with status code " + response.statusCode());
            };
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not create URI for " + authorName, e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not call Pluralsight API", e);
        }

    }
}
