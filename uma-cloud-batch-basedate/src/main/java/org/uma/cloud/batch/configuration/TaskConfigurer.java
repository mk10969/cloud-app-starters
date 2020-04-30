package org.uma.cloud.batch.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableTask
public class TaskConfigurer extends DefaultTaskConfigurer {

    @Autowired
    public TaskConfigurer(@Qualifier("taskDataSource") DataSource dataSource) {
        super(dataSource);
    }

}
