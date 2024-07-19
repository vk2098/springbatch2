package com.batch.readerwriter.Service;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BatchService {
    @Autowired
    private JobLauncher jobLauncher;
    @Qualifier("firstJob")
    @Autowired
    Job firstjob;

    JobExecution jobExecution;


    @Async
    public void job(String params) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Job job=null;
        if (params.equals("firstJob")) {
            job=firstjob;
        }

        Map< String, JobParameter<?>> map=new HashMap<>();
        map.put("time", new JobParameter<>(LocalDateTime.now().toString(),String.class));
        JobParameters jobParameters=new JobParameters(map);
        jobExecution=jobLauncher.run(job, jobParameters);
//        System.out.println("job id"+jobExecution.getJobId());
    }
}
