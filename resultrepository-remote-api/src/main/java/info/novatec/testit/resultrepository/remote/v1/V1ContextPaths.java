package info.novatec.testit.resultrepository.remote.v1;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class V1ContextPaths {

    private static final String VERSION = "/v1";
    private static final String ID = "/{id}";

    public static final String BUILDJOBS = VERSION + "/buildjobs";
    public static final String BUILDJOB_FOR_ID = BUILDJOBS + ID;
    public static final String BUILDS_OF_BUILDJOB_FOR_ID = BUILDJOB_FOR_ID + "/builds";

    public static final String BUILDS = VERSION + "/builds";
    public static final String BUILD_FOR_ID = BUILDS + ID;
    public static final String BUILDJOB_OF_BUILD_FOR_ID = BUILD_FOR_ID + "/buildjob";
    public static final String TAGS_OF_BUILD_FOR_ID = BUILD_FOR_ID + "/tags";
    public static final String TESTGROUPRESULTS_OF_BUILD_FOR_ID = BUILD_FOR_ID + "/testgroupresults";
    public static final String STATISTIC_OF_BUILD_FOR_ID = BUILD_FOR_ID + "/statistic";

    public static final String IMPORT_TEST_GROUP_RESULT = VERSION + "/import/testGroupResult";

    public static final String METRICS = VERSION + "/metrics";
    public static final String COUNTER_METRICS = METRICS + "/counters";
    public static final String HISTOGRAM_METRICS = METRICS + "/histograms";
    public static final String METER_METRICS = METRICS + "/meters";
    public static final String TIMER_METRICS = METRICS + "/timers";

    public static final String TAGS = VERSION + "/tags";
    public static final String TAGS_SEARCH = TAGS + "/search";
    public static final String TAG_FOR_ID = TAGS + ID;
    public static final String BUILDS_OF_TAG_FOR_ID = TAG_FOR_ID + "/builds";
    public static final String TESTGROUPRESULTS_OF_TAG_FOR_ID = TAG_FOR_ID + "/testgroupresults";
    public static final String TESTRESULTS_OF_TAG_FOR_ID = TAG_FOR_ID + "/testresults";

    public static final String TESTGROUPRESULT_FOR_ID = VERSION + "/testgroupresults/{id}";
    public static final String TESTGROUP_OF_TESTGROUPRESULT_FOR_ID = TESTGROUPRESULT_FOR_ID + "/testgroup";
    public static final String BUILD_OF_TESTGROUPRESULT_FOR_ID = TESTGROUPRESULT_FOR_ID + "/build";
    public static final String TAGS_OF_TESTGROUPRESULT_FOR_ID = TESTGROUPRESULT_FOR_ID + "/tags";
    public static final String TESTRESULTS_OF_TESTGROUPRESULT_FOR_ID = TESTGROUPRESULT_FOR_ID + "/testresults";
    public static final String STATISTIC_OF_TESTGROUPRESULT_FOR_ID = TESTGROUPRESULT_FOR_ID + "/statistic";

    public static final String TESTGROUPS = VERSION + "/testgroups";
    public static final String TESTGROUP_FOR_ID = TESTGROUPS + ID;
    public static final String TESTGROUPRESULTS_OF_TESTGROUP_FOR_ID = TESTGROUP_FOR_ID + "/testgroupresults";

    public static final String TESTRESULTDETAIL_FOR_ID = VERSION + "/testresultdetails/{id}";
    public static final String TESTRESULT_OF_TESTRESULTDETAIL_FOR_ID = TESTRESULTDETAIL_FOR_ID + "/testresult";

    public static final String TESTRESULT_FOR_ID = VERSION + "/testresults/{id}";
    public static final String TEST_OF_TESTRESULT_FOR_ID = TESTRESULT_FOR_ID + "/test";
    public static final String TESTGROUPRESULT_OF_TESTRESULT_FOR_ID = TESTRESULT_FOR_ID + "/testgroupresult";
    public static final String TAGS_OF_TESTRESULT_FOR_ID = TESTRESULT_FOR_ID + "/tags";
    public static final String TESTRESULTDETAILS_OF_TESTRESULT_FOR_ID = TESTRESULT_FOR_ID + "/testresultdetails";
    public static final String STATISTIC_OF_TESTRESULT_FOR_ID = TESTRESULT_FOR_ID + "/statistic";

    public static final String TESTS = VERSION + "/tests";
    public static final String TEST_FOR_ID = TESTS + ID;
    public static final String TESTRESULTS_OF_TEST_FOR_ID = TEST_FOR_ID + "/testresults";

    private V1ContextPaths() {
        // utility constructor
    }

}
