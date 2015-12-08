package info.novatec.testit.resultrepository.logging.api;

import java.text.MessageFormat;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.slf4j.Logger;


public enum LogLevel {

    TRACE(logger -> {
        return logger.isTraceEnabled();
    }, (logger, message) -> {
        if (logger.isTraceEnabled()) {
            logger.trace(message);
        }
    }),

    DEBUG(logger -> {
        return logger.isDebugEnabled();
    }, (logger, message) -> {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }),

    INFO(logger -> {
        return logger.isInfoEnabled();
    }, (logger, message) -> {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }),

    WARNING(logger -> {
        return logger.isWarnEnabled();
    }, (logger, message) -> {
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }),

    ERROR(logger -> {
        return logger.isErrorEnabled();
    }, (logger, message) -> {
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    });

    private Function<Logger, Boolean> shouldLogFunction;
    private BiConsumer<Logger, String> logConsumer;

    private LogLevel(Function<Logger, Boolean> shouldLogFunction, BiConsumer<Logger, String> logConsumer) {
        this.shouldLogFunction = shouldLogFunction;
        this.logConsumer = logConsumer;
    }

    public boolean shouldLog(Logger logger) {
        return shouldLogFunction.apply(logger);
    }

    /**
     * Logs the given message with optional arguments using the given
     * {@linkplain Logger}. The message String is formatted using
     * {@linkplain MessageFormat#format(String, Object...)}.
     *
     * @param logger the {@linkplain Logger} to use
     * @param message the message to log - can reference arguments like {0}, {1}
     * @param args the arguments to use when formatting the message
     */
    public void log(Logger logger, String message, Object... args) {
        log(logger, MessageFormat.format(message, args));
    }

    public void log(Logger logger, String message) {
        logConsumer.accept(logger, message);
    }

}
