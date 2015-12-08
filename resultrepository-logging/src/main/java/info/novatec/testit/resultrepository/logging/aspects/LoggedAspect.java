package info.novatec.testit.resultrepository.logging.aspects;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;


/**
 * This {@linkplain Aspect aspect} is used to log the execution of public
 * methods. This includes a basic measurement of the time it took to execute the
 * method.
 * <p>
 * To use this aspect simply annotate a public method using the
 * {@linkplain Logged logged execution} annotation. But be aware that only
 * method calls from outside the class will actually be using the aspect. All
 * internal calls to annotated methods will not result in the aspect being
 * triggered!
 */
@Aspect
@Component
public class LoggedAspect {

    private static final String START_MESSAGE = "[ID{0}] started execution of {1}{2}";
    private static final String FINISH_MESSAGE = "[ID{0}] finished execution of {1}{2} after {3} ms";

    private static final String ARGUMENTS_NONE = "(...)";
    private static final String ARGUMENTS_PREFIX = "(";
    private static final String ARGUMENTS_SPLITTER = ", ";
    private static final String ARGUMENTS_SUFFIX = ")";

    @Around("@annotation(annotation)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, Logged annotation) throws Throwable {

        Signature signature = proceedingJoinPoint.getSignature();

        Logger logger = getLoggerFor(signature);
        if (!callShouldBeLogged(annotation, logger)) {
            return proceedingJoinPoint.proceed();
        }

        String methodName = getMethodNameFrom(signature);
        String methodArguments = getMethodArgumentsFrom(annotation, proceedingJoinPoint);

        String callId = generateIdentifier();

        logMessageWithArgs(logger, annotation, START_MESSAGE, callId, methodName, methodArguments);

        long start = System.currentTimeMillis();
        Object value = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

        logMessageWithArgs(logger, annotation, FINISH_MESSAGE, callId, methodName, methodArguments, (end - start));

        return value;

    }

    private boolean callShouldBeLogged(Logged annotation, Logger logger) {
        return annotation.logLevel().shouldLog(logger);
    }

    private Logger getLoggerFor(Signature signature) {
        String className = signature.getDeclaringTypeName();
        return LoggerFactory.getLogger(className);
    }

    private String getMethodNameFrom(Signature signature) {
        return signature.getName();
    }

    private String getMethodArgumentsFrom(Logged annotation, ProceedingJoinPoint proceedingJoinPoint) {
        if (!annotation.printArguments()) {
            return ARGUMENTS_NONE;
        }
        Object[] args = proceedingJoinPoint.getArgs();
        return args.length > 0 ? ARGUMENTS_PREFIX + StringUtils.join(args, ARGUMENTS_SPLITTER) + ARGUMENTS_SUFFIX :
               StringUtils.EMPTY;
    }

    private String generateIdentifier() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void logMessageWithArgs(Logger logger, Logged annotation, String message, Object... args) {
        LogLevel logLevel = annotation.logLevel();
        logLevel.log(logger, message, args);
    }

}
