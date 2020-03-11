package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.Course;

public interface CourseRepository extends JpaRepository<Course, RaceCourseCode> {
}
