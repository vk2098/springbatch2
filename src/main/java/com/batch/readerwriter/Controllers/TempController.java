package com.batch.readerwriter.Controllers;


import com.batch.readerwriter.Service.BatchService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempController {

    @Autowired
    BatchService batchService;

    @GetMapping("/{firstjob}")
    public ResponseEntity<String> firstJob(@PathVariable String firstjob) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        batchService.job(firstjob);
        return new ResponseEntity<>("1stjob", HttpStatus.OK);

    }
}
