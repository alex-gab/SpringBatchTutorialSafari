package io.spring.batch.configuration;

import io.spring.batch.domain.Customer;
import io.spring.batch.domain.CustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Customer> cursorItemReader() {
        final JdbcCursorItemReader<Customer> cursorItemReader = new JdbcCursorItemReader<>();
        cursorItemReader.setSql("select id, firstName, lastName, birthdate from customer order by lastName, firstName");
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setRowMapper(new CustomerRowMapper());
        return cursorItemReader;
    }

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return customers -> {
            for (final Customer customer : customers) {
                System.out.println(customer.toString());
            }
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.
                get("step1").
                <Customer, Customer>chunk(10).
                reader(cursorItemReader()).
                writer(customerItemWriter()).
                build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step1()).build();
    }
}
