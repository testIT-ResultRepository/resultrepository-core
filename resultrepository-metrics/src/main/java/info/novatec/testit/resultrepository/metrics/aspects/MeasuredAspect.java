package info.novatec.testit.resultrepository.metrics.aspects;

import java.lang.reflect.UndeclaredThrowableException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.metrics.api.metrics.Metrics;
import info.novatec.testit.resultrepository.metrics.api.metrics.TimerMetric;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;


/**
 * This {@linkplain Aspect aspect} is used to measure the execution of public
 * methods.
 * <p>
 * To use this aspect simply annotate a public method using the
 * {@linkplain Measured measured} annotation. But be aware that only method
 * calls from outside the class will actually be using the aspect. All internal
 * calls to annotated methods will not result in the aspect being triggered!
 */
@Aspect
@Component
public class MeasuredAspect {

    @Autowired
    private MetricsManager metrics;

    @Around("@annotation(info.novatec.testit.resultrepository.metrics.api.annotations.Measured)")
    @SuppressWarnings({ "PMD.ExceptionAsFlowControl", "PMD.AvoidCatchingThrowable" })
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return metrics.time(getTimerMetricFor(proceedingJoinPoint), () -> {
                try {
                    return proceedingJoinPoint.proceed();
                } catch (Throwable t) {
                    throw new UndeclaredThrowableException(t);
                }
            });
        } catch (UndeclaredThrowableException e) {
            throw e.getCause();
        }
    }

    private TimerMetric getTimerMetricFor(ProceedingJoinPoint proceedingJoinPoint) {
        Signature signature = proceedingJoinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        return Metrics.timerMetric(className, methodName);
    }

}
