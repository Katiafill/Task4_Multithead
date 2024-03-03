package ru.katiafill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.UnaryOperator;

public class MultithreadingSumOperation {
    private static final Logger logger = LoggerFactory.getLogger(MultithreadingSumOperation.class);
    private static final int INTERVAL_STEP = 10_000;
    private static final int NUMBER_OF_THREAD = 10;

    private final UnaryOperator<Double> operation;
    private final int operationCount;

    public MultithreadingSumOperation(UnaryOperator<Double> operation, int operationCount) {
        this.operation = operation;
        this.operationCount = operationCount;
    }

    public int execute() {
        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
        List<OperationTask> tasks = createTasks(operationCount, operation);

        double result = 0;
        try {
            List<Future<Double>> futures = service.invokeAll(tasks);
            for (Future<Double> future : futures) {
                result += future.get();
            }
            service.shutdown();
        } catch (InterruptedException | ExecutionException ex) {
            logger.error("Exception running tasks", ex);
        }

        return (int) result;
    }

    private List<OperationTask> createTasks(int n, UnaryOperator<Double> operation) {
        List<OperationTask> tasks = new ArrayList<>();
        for (int start = 0; start < n; start += INTERVAL_STEP) {
            int end = Math.min(n, start + INTERVAL_STEP);
            tasks.add(new OperationTask(start, end, operation));
        }
        return tasks;
    }
}
