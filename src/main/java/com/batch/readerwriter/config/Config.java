package com.batch.readerwriter.config;


import com.batch.readerwriter.Model.StudentCsv;
import com.batch.readerwriter.Reader.ItemReader1;
import com.batch.readerwriter.Writer.ItemWriter1;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;


@Configuration
public class Config {


    @Autowired
    ItemReader1 itemReader1;

    @Autowired
    ItemWriter1 itemWriter1;
    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager platformTransactionManager;
    @Bean
    public Job firstJob(){
        return new JobBuilder("firstJob",jobRepository).incrementer(new RunIdIncrementer())
                .start(firstStep()).
                build();
    }


    public Step firstStep(){
        return new StepBuilder("firstStep",jobRepository).<StudentCsv,StudentCsv>chunk(2,platformTransactionManager)
                .reader(flatFileItemReader())
                .writer(itemWriter1).build();
    }



    public FlatFileItemReader<StudentCsv> flatFileItemReader(){
        FlatFileItemReader<StudentCsv> flatFileItemReader=new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(new File("/Users/vivekkapa/Desktop/students.csv")));
        flatFileItemReader.setLineMapper(createLineMapper());
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    public LineMapper createLineMapper(){
        DefaultLineMapper defaultLineMapper=new DefaultLineMapper();
        DelimitedLineTokenizer delimitedLineTokenizer=new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames("ID","First Name","Last Name","Email");
        BeanWrapperFieldSetMapper<StudentCsv> beanWrapperFieldSetMapper=new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(StudentCsv.class);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return  defaultLineMapper;
    }

    public Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("First Task");
                return RepeatStatus.FINISHED;
            }
        };
    }
}
