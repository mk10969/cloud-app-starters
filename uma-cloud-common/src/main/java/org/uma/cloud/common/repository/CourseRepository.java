package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Course.CompositeId> {
}
