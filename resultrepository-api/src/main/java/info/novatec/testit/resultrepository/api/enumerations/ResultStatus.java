package info.novatec.testit.resultrepository.api.enumerations;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.interfaces.Test;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;


/**
 * Represents the result status of a {@link TestResult test result}. This
 * information can be used to classify and filter certain results. I.e. show
 * only failed or skipped tests.
 *
 * @since 2.0.0
 */
public enum ResultStatus implements Serializable {

    /**
     * The {@link Test test} passed. The {@link TestResult test result} is
     * positive.
     *
     * @since 2.0.0
     */
    PASSED,

    /**
     * The {@link Test test} failed. The {@link TestResult test result} is
     * negative.
     *
     * @since 2.0.0
     */
    FAILED,

    /**
     * The {@link Test test} threw and unexpected exception. The
     * {@link TestResult test result} is very negative.
     *
     * @since 2.0.0
     */
    EXCEPTION,

    /**
     * The {@link Test test} was skipped. The {@link TestResult test result} is
     * neutral.
     *
     * @since 2.0.0
     */
    SKIPPED,

    /**
     * The {@link Test test} ended in an unknown state. The {@link TestResult
     * test result} is neutral.
     *
     * @since 2.0.0
     */
    UNKNOWN;

}
