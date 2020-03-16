package org.uma.jvLink.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.jvLink.server.repository.impl.JvStoredCourseRepository;
import org.uma.jvLink.core.config.option.Option;
import org.uma.platform.common.model.Course;
import org.uma.platform.common.utils.lang.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class JvCommonController {

    /**
     * コース情報
     */
    private final JvStoredCourseRepository courseRepository;


    @GetMapping("/course/{epochSecond}")
    public List<Course> findCourse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return courseRepository.readLines(dateTime, Option.STANDARD);
    }

}
