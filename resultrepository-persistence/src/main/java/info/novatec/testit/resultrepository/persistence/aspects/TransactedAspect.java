package info.novatec.testit.resultrepository.persistence.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;


/**
 * This {@link Aspect aspect} is used to execute public methods within a
 * {@link Transaction Neo4j transaction} and removing the boiler plate
 * transaction handling code like creation, committing and handling errors.
 * <p>
 * To use this aspect simply annotate a public method using the
 * {@link Transacted transacted} annotation. But be aware that only method calls
 * from outside the class will actually be using the aspect. All internal calls
 * to annotated methods will not result in the aspect being triggered!
 * <p>
 * <b>Notes:</b>
 * <ul>
 * <li>If no exception or error is thrown, the transaction will be committed.
 * </li>
 * <li>If any exception or error is thrown, the transaction will be rolled back
 * </li>
 * <li>Any occurring exception or error will not be catched but re thrown!</li>
 * </ul>
 */
@Aspect
@Component
public class TransactedAspect {

    @Autowired
    private GraphDatabaseService graph;

    @Around("@annotation(annotation)")
    @SuppressWarnings("unused")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, Transacted annotation) throws Throwable {
        Object value = null;
        try (Transaction tx = graph.beginTx()) {
            value = proceedingJoinPoint.proceed();
            tx.success();
        }
        return value;
    }

}
