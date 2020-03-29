package org.uma.cloud.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class JvLinkStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // no ops
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        summary(stepExecution);
        return stepExecution.getExitStatus();
    }

    private void summary(StepExecution stepExecution) {
        log.info(String.format("summary\n" +
                        "name=%s\n" +
                        "status=%s\n" +
                        "readCount=%d\n" +
                        "readSkipCount=%d\n" +
                        "filterCount=%d\n" +
                        "processSkipCount=%d\n" +
                        "writeCount=%d\n" +
                        "writeSkipCount=%d\n" +
                        "commitCount=%d\n" +
                        "rollbackCount=%d",
                stepExecution.getStepName(),
                stepExecution.getStatus(),
                stepExecution.getReadCount(),
                stepExecution.getReadSkipCount(),
                stepExecution.getFilterCount(),
                stepExecution.getProcessSkipCount(),
                stepExecution.getWriteCount(),
                stepExecution.getWriteSkipCount(),
                stepExecution.getCommitCount(),
                stepExecution.getRollbackCount()));
    }

}
