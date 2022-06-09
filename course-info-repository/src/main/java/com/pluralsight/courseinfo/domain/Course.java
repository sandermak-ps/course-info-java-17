package com.pluralsight.courseinfo.domain;

import java.util.Optional;

public record Course(String id, String name, long length, String url, Optional<String> notes) {

    public Course {
        filled(id);
        filled(name);
        filled(url);
        notes.ifPresent(Course::filled);
    }

    private static void filled(String s) {
        if(s == null || s.isBlank()) {
            throw new IllegalArgumentException("No value present!");
        }
    }

}
