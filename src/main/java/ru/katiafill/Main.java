package ru.katiafill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        int n = 0;
        if (args.length == 1) {
            n = Integer.parseInt(args[0]);
        }
        if (n < 1) {
            return;
        }
        logger.info("N = " + n);
        int result = new MultithreadingSumOperation(x -> 1 / Math.pow(2, x), n).execute();
        logger.info("Result: " + result);
    }


}