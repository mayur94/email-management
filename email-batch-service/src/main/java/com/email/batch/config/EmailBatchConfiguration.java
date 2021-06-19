package com.email.batch.config;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.email.batch.model.EmailRequest;
import com.email.batch.publisher.EmailWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableBatchProcessing
@EnableScheduling
@EnableAsync
@Configuration
public class EmailBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Autowired
    public DataSource dataSource;


    @Bean
    public JdbcCursorItemReader<EmailRequest> reader(){

        JdbcCursorItemReader<EmailRequest> reader= new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT email FROM emailaddressbatch.newuser");
        reader.setRowMapper(new UserRowMapper());
        return reader;
    }


    public class UserRowMapper implements RowMapper<EmailRequest> {

        @Override
        public EmailRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmailRequest user = new EmailRequest();
            user.setEmail(rs.getString("email"));
            System.out.println("EMail List"+user.getEmail());

            return user;
        }

    }

    @Bean
    public EmailRequestItemProcesser processer() {

        return new EmailRequestItemProcesser();
    }

    @Bean
    public EmailWriter publishToQueue() {

        return new EmailWriter();
    }

    @Bean
    public Step step1() {

        return stepBuilderFactory.get("step1").<EmailRequest,EmailRequest> chunk(1)
                .reader(reader())
                .processor(processer())
                .writer(publishToQueue())
                .build();
    }

    @Bean
    public Job exportUserJob() {
        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();


    }


}
