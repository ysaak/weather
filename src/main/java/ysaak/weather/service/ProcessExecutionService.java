package ysaak.weather.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ysaak.weather.data.ProcessExecutionResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProcessExecutionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionService.class);

    private final ExecutorService executorService;
    private final AtomicLong processExecutionCount;

    public ProcessExecutionService() {
        this.executorService = Executors.newCachedThreadPool();
        this.processExecutionCount = new AtomicLong(0);
    }

    public ProcessExecutionResult execute(List<String> command, Duration timeout, boolean traceExecution) throws IOException, InterruptedException, ExecutionException {

        final ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);

        long processId = processExecutionCount.incrementAndGet();

        if (traceExecution) {
            LOGGER.debug("{} > Start execution of command {}", processId, String.join(" ", command));
        }

        Process process = processBuilder.start();

        Future<String> outputFuture = executorService.submit(() -> readInputStream(process, traceExecution, processId));
        boolean processExited = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
        int exitVal;

        if (processExited) {
            exitVal = process.exitValue();
        }
        else {
            exitVal = Integer.MIN_VALUE;
            killProcess(process);
        }

        String output = outputFuture.get();

        if (traceExecution) {
            LOGGER.debug("{} > End of execution ; process exited: {} ; exit val: {}", processId, processExited, exitVal);
        }

        return new ProcessExecutionResult(processExited, exitVal, output);
    }

    private String readInputStream(Process process, boolean traceExecution, long processId) {
        StringBuilder output = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        try {
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");

                if (traceExecution) {
                    LOGGER.debug("{} > {}", processId, line);
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("Error while reading output stream of process " + process.toString(), e);
        }

        return output.toString();
    }

    private void killProcess(Process process) throws InterruptedException {
        boolean killSuccess = process.destroyForcibly().waitFor(1, TimeUnit.SECONDS);
        if (!killSuccess) {
            LOGGER.error("Process {} not killed after 1 second", process);
        }
    }
}
