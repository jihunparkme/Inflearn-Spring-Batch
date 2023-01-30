package io.springbatch.springbatchlecture.job.parameter.validator;

import org.springframework.batch.core.*;

public class CustomJobParametersValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {

        if (jobParameters.getString("name") == null || jobParameters.getString("date") == null) {
            throw new JobParametersInvalidException("name parameter is not found.");
        }
    }
}