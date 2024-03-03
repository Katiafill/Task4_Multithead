package ru.katiafill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.function.UnaryOperator;

public class OperationTask implements Callable<Double> {
    private static final Logger logger = LoggerFactory.getLogger(OperationTask.class);

    private final int start;
    private final int end;
    private final UnaryOperator<Double> operation;

    OperationTask(int start, int end, UnaryOperator<Double> operation) {
        this.start = start;
        this.end = end;
        this.operation = operation;
    }

    @Override
    public Double call() throws Exception {
        logger.info("Start task for (" + start + ", " + end + ").");
        double result = 0;

        for (double i = start; i < end; i++) {
            result += operation.apply(i);
        }

        logger.info("Finish task for (" + start + ", " + end + "). Result = " + result);
        return result;
    }
}
