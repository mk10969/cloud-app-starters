package org.uma.cloud.batch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.Course;


@Repository
public interface CourseRepository extends CrudRepository<Course, Course.CourseId> {
}
