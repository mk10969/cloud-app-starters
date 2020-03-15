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
        log.info("afterStep: read count {}", stepExecution.getReadCount());
        log.info("afterStep: write count {}", stepExecution.getWriteCount());
        log.info("afterStep: commit count {}", stepExecution.getCommitCount());
        log.info(stepExecution.getSummary());

        return stepExecution.getExitStatus();
    }

}
