package com.example.todolist.batch;

import com.example.todolist.consumer.entity.Todolist;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
//@EnableScheduling
public class BatchConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    TodolistItemProcessor todolistItemProcessor;

    @Autowired
    TodolistItemWriter todolistItemWriter;

    @Bean
    public Job todolistJob() {

        return this.jobBuilderFactory.get("todolistJob")
                .start(this.todolistStep())
                .build();
    }

    @Bean
    public Step todolistStep() {

        return this.stepBuilderFactory.get("todolistStep")
                .<Todolist, Todolist>chunk(5)
                .reader(this.todolistFlatFileItemReader())
                .processor(this.todolistItemProcessor)
                .writer(this.todolistItemWriter)
                .faultTolerant()
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .skip(Exception.class)
                .build();

    }

    @Bean
    FlatFileItemReader<Todolist> todolistFlatFileItemReader() {

        return new FlatFileItemReaderBuilder<Todolist>()
                .resource(new ClassPathResource("todolist.csv"))
                .name("todolistFlatFileItemReader")
                .delimited()
                .names(new String[]{"name", "surName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Todolist.class);
                }})
                .build();
    }


}
