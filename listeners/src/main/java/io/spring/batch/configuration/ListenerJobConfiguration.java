package io.spring.batch.configuration;

import io.spring.batch.listener.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Arrays.asList;

@Configuration
public class ListenerJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<String> reader() {
        return new ListItemReader<>(asList("one", "two", "three"));
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            for (String item : items) {
                System.out.println("Writing item " + item);
            }
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.
                get("step1").
                <String, String>chunk(2).
                faultTolerant().
                listener(new ChunkListener()).
                reader(reader()).
                writer(writer()).
                build();
    }

    @Bean
    public Job listenerJob(final JobExecutionListener jobListener) {
        return jobBuilderFactory.get("listenerJob").
                start(step1()).
                listener(jobListener).
                build();
    }
}
