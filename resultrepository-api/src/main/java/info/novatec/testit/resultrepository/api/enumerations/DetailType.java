package info.novatec.testit.resultrepository.api.enumerations;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;


/**
 * Represents the type information of a {@link TestResultDetail test result
 * detail}. This information can be used to classify and filter certain details.
 * I.e. show only details of a certain type.
 *
 * @since 2.0.0
 */
public enum DetailType implements Serializable {

    /**
     * The {@link TestResultDetail test result detail} contains textual
     * information without any positive or negative rating.
     *
     * @since 2.0.0
     */
    INFORMATION,

    /**
     * The {@link TestResultDetail test result detail} contains positive
     * information. I.e. the passing of a checkpoint or test.
     *
     * @since 2.0.0
     */
    SUCCESS,

    /**
     * The {@link TestResultDetail test result detail} contains slightly
     * negative information. I.e. something was within the margin of error, but
     * barely.
     *
     * @since 2.0.0
     */
    WARNING,

    /**
     * The {@link TestResultDetail test result detail} contains negative
     * information. I.e. the failing of a checkpoint or test.
     *
     * @since 2.0.0
     */
    ERROR,

    /**
     * The {@link TestResultDetail test result detail} contains very negative
     * information. I.e. the occurrence of an unexpected exception.
     *
     * @since 2.0.0
     */
    EXCEPTION;

}
