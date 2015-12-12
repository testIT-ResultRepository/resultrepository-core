[Home](../README.md)

# REST API

This document provides an overview of all the available and planned REST endpoints. The request and response entities are 
referenced as model classes of the ```resultrepositor-api``` module. All endpoints consume and produce 
```application/json```. There is also a java client implementation (```resultrepository-client```) for this API which 
takes care of the REST overhead.

## Build Jobs

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/buildjobs | POST | BuildJobData | BuildJobData | &#x2713; |
| /v1/buildjobs | GET |  - | Set of BuildJobData | &#x2713; |
| /v1/buildjobs/{id} | GET |  - | BuildJobData | &#x2713; |
| /v1/buildjobs/{id} | PUT | BuildJobData | BuildJobData | planned |
| /v1/buildjobs/{id} | DELETE |  - |  - | planned |
| /v1/buildjobs/{id}/builds | GET |  - | Set of BuildData | &#x2713; |

## Builds

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/builds | POST | BuildData | BuildData | &#x2713; |
| /v1/builds/{id} | GET |  - | BuildData | &#x2713; |
| /v1/builds/{id} | PUT | BuildData | BuildData | planned |
| /v1/builds/{id} | DELETE |  - |  - | planned |
| /v1/builds/{id}/buildjob | GET |  - | BuildJobData | &#x2713; |
| /v1/builds/{id}/tags | GET |  - | Set of TagData | &#x2713; |
| /v1/builds/{id}/tags | PUT | TagData |  - | planned |
| /v1/builds/{id}/testgroupresults | GET |  - | Set of TestGroupResultData | &#x2713; |
| /v1/builds/{id}/statistic | GET |  - | BuildResultStatistic | &#x2713; |

## Test Groups

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/testgroups | POST | TestGroupData | TestGroupData | &#x2713; |
| /v1/testgroups | GET | - | Set of TestGroupData | &#x2713; |
| /v1/testgroups/{id} | GET | - | TestGroupData | &#x2713; |
| /v1/testgroups/{id} | PUT | TestGroupData | TestGroupData | planned |
| /v1/testgroups/{id} | DELETE | - | - | planned |
| /v1/testgroups/{id}/testgroupresults | GET | - | Set of TestGroupResultData | &#x2713; |

## Test Group Results

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/testgroupresults/{id} | GET | - | TestGroupResultData | &#x2713; |
| /v1/testgroupresults/{id} | PUT | TestGroupResultData | TestGroupResultData | planned |
| /v1/testgroupresults/{id} | DELETE | - | - | planned |
| /v1/testgroupresults/{id}/testgroup | GET | - | TestGroupData | &#x2713; |
| /v1/testgroupresults/{id}/testgroup | PUT | TestGroupData | - | planned |
| /v1/testgroupresults/{id}/build | GET | - | BuildData | &#x2713; |
| /v1/testgroupresults/{id}/build | PUT | BuildData | - | planned |
| /v1/testgroupresults/{id}/tags | GET | - | Set of TagData | &#x2713; |
| /v1/testgroupresults/{id}/tags | PUT | TagData | - | planned |
| /v1/testgroupresults/{id}/testresults | GET | - | Set of TestResultData | &#x2713; |
| /v1/testgroupresults/{id}/statistic | GET | - | TestGroupResultStatistic | &#x2713; |

## Tests

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/tests | POST | TestData | TestData | &#x2713; |
| /v1/tests | GET | - | Set of TestData | &#x2713; |
| /v1/tests/{id} | GET | - | TestData | &#x2713; |
| /v1/tests/{id} | PUT | TestData | TestData | planned |
| /v1/tests/{id} | DELETE | - | - | planned |
| /v1/tests/{id}/testresults | GET | - | Set of TestResultData | &#x2713; |

## Test Results

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/testresults/{id} | GET | - | TestResultData | &#x2713; |
| /v1/testresults/{id} | PUT | TestResultData | TestResultData | planned |
| /v1/testresults/{id} | DELETE | - | - | planned |
| /v1/testresults/{id}/test | GET | - | TestData | &#x2713; |
| /v1/testresults/{id}/test | PUT | TestData | - | planned |
| /v1/testresults/{id}/testgroupresult | GET | - | TestGroupResultData | &#x2713; |
| /v1/testresults/{id}/testgroupresult | PUT | TestGroupResultData | - | planned |
| /v1/testresults/{id}/tags | GET | - | Set of TagData | &#x2713; |
| /v1/testresults/{id}/tags | PUT | TagData | - | planned |
| /v1/testresults/{id}/testresultdetails | GET | - | List of TestResultDetailData | &#x2713; |
| /v1/testresults/{id}/statistic | GET | - | TestResultStatistic | &#x2713; |

## Test Result Details

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/testresultdetails/{id} | GET | - | TestResultDetailData | &#x2713; |
| /v1/testresultdetails/{id} | PUT | TestResultDetailData | TestResultDetailData | planned |
| /v1/testresultdetails/{id} | DELETE | - | - | planned |
| /v1/testresultdetails/{id}/testresult | GET | - | TestResultData | &#x2713; |
| /v1/testresultdetails/{id}/testresult | PUT | TestResultData | - | planned |

## Tags

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/tags | POST | TagData | TagData | &#x2713; |
| /v1/tags | GET | - | Set of TagData | &#x2713; |
| /v1/tags/search | POST | TagDescriptor | Set of TagData | &#x2713; |
| /v1/tags/{id} | GET | - | TagData | &#x2713; |
| /v1/tags/{id} | PUT | TagData | TagData | planned |
| /v1/tags/{id} | DELETE | - | - | planned |
| /v1/tags/{id}/builds | GET | - | Set of BuildData | &#x2713; |
| /v1/tags/{id}/testgroupresults | GET | - | Set of TestGroupResultData | &#x2713; |
| /v1/tags/{id}/testresults | GET | - | Set of TestResultData | &#x2713; |

## Import

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/import/testGroupResult | POST | - | TestGroupResultData | &#x2713; |

## Metrics

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /v1/metrics | GET | - | MetricsReport | &#x2713; |
| /v1/metrics/counters | GET | - | List of CounterReport | &#x2713; |
| /v1/metrics/histograms | GET | - | List of HistogramReport | &#x2713; |
| /v1/metrics/meters | GET | - | List of MeterReport | &#x2713; |
| /v1/metrics/timers | GET | - | List of TimerReport | &#x2713; |

# Plugin REST APIs

## JUnit Plugin

| Context Path | Method | Request | Response | Status |
| ------------ | ------ | ------- | -------- | ------ |
| /plugins/junit | POST | Testsuite (JUnit XML Report) | - | &#x2713; |

The build a XML report should be linked to can be set by providing the following porperties:

- buildJob: (String) name of the build job
- buildNumber: (Integer) number of the build
