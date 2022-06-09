package com.pluralsight.courseinfo.cli;

import com.pluralsight.courseinfo.cli.service.CourseRetrievalService;
import com.pluralsight.courseinfo.cli.service.CourseStorageService;
import com.pluralsight.courseinfo.cli.service.PluralsightCourse;
import com.pluralsight.courseinfo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.function.Predicate.not;

public class CourseRetriever {
    private static Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

    public static void main(String... args) {
        try {
            LOG.info("Starting CourseRetriever");
            if (args.length == 0) {
                LOG.error("Please provider an author name as first argument");
                return;
            } else {
                retrieveCourses(args[0]);
            }
        } catch (Exception e) {
            LOG.error("Unexpected error", e);
        }
    }

    private static void retrieveCourses(String author) {
        CourseRetrievalService courseRetrievalService = new CourseRetrievalService();
        CourseRepository courseRepository = CourseRepository.openCourseRepository("./test.db");
        CourseStorageService courseStorageService = new CourseStorageService(courseRepository);

        LOG.info("Retrieving courses for author '{}'", author);
        List<PluralsightCourse> coursesToStore =
                courseRetrievalService.getPluralsightCoursesFor(author)
                        .stream()
                        .filter(not(PluralsightCourse::isRetired))
                        .toList();

        LOG.info("Retrieved the following courses {}", coursesToStore);
        courseStorageService.storePluralsightCourses(coursesToStore);
        LOG.info("{} courses successfully stored", coursesToStore.size());
    }
}
