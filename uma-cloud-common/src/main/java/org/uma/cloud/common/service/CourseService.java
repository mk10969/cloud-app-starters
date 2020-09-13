package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;


    public Course findOne(Course.CompositeId compositeId) {
        return this.repository.findById(compositeId).orElseThrow();
    }

    public boolean exists(Course.CompositeId compositeId) {
        return this.repository.existsById(compositeId);
    }

    @Transactional
    public Course save(Course course) {
        return this.repository.save(course);
    }

    @Transactional
    public List<Course> saveAll(List<Course> courses) {
        return this.repository.saveAll(courses);
    }

    @Transactional
    public void delete(Course course) {
        this.repository.delete(course);
    }
}
