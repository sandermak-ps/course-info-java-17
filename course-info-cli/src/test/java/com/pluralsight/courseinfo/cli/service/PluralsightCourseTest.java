package com.pluralsight.courseinfo.cli.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PluralsightCourseTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
             01:08:54.9613330, 68
             00:05:37, 5
             00:00:00.0, 0
            """)
    void timeToDurationInMinutes(String input, int expected) {
        PluralsightCourse course = new PluralsightCourse("1", "Test course", input, "url", false);
        assertEquals(expected, course.durationInMinutes());
    }
}
