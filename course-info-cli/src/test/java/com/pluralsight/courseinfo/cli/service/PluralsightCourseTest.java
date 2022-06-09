package com.pluralsight.courseinfo.cli.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PluralsightCourseTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            01:08:54.9613330, 68
            00:05:37, 5
            00:00:00.0, 0
            """)
    void durationInMinutes(String input, long expected) {
        PluralsightCourse course =
                new PluralsightCourse("id", "Test course", input, "url", false);
        assertEquals(expected, course.durationInMinutes());
    }
}