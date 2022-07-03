package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseStorageServiceTest {

    @Test
    void storePluralsightCourseAsCourse() {
        InMemoryCourseRepository courseRepository = new InMemoryCourseRepository();
        CourseStorageService courseStorageService = new CourseStorageService(courseRepository);
        PluralsightCourse ps1 = new PluralsightCourse("1", "Title 1", "01:40:00.123", "/url-1", false);
        courseStorageService.storePluralsightCourses(List.of(ps1));

        Course c1 = new Course("1", "Title 1", 100, "https://app.pluralsight.com/url-1", Optional.empty());
        assertEquals(List.of(c1), courseRepository.getAllCourses());
    }

    static class InMemoryCourseRepository implements CourseRepository {

        private List<Course> courses = new ArrayList<>();

        @Override
        public List<Course> getAllCourses() {
            return courses;
        }

        @Override
        public void saveCourse(Course course) {
            courses.add(course);
        }

        @Override
        public void addNotes(String id, String notes) {
            throw new UnsupportedOperationException();
        }
    }
}
