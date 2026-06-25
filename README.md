# Introduction
This is a UI automation framework that uses
- playwright for browser-driven tests
  - hooks
    - one-time health check
    - tracing on failure (feature flagged)
- Java specific
  - Guice for DI
  - Config with HOCON support
  - JUnit test runner with tagging
    - Surefire tag profiles
      `mvn test -P smoke`
    - tests configured for
      - sequential JUnit via ide
      - parallel (per fork - default 4) via Surefire cmdline execution

# Getting Started
## Pre-req
1. Launch the Emulsive TS Store locally
   - navigate to the GitHub project [home](https://github.com/projectsbyandy/emulsive-ts-store) and clone the repo
   - set up the project by executing `npm install`
   - start the services (ui, api, image server) executing `npm run all`
   - OPTIONAL - run `npm run tests:playwright` to run regression tests to ensure website is functional.
1. Create a `local-config.conf` and populate with the following:
    ```
    users {
        "testUser" = {
          password = "1234"
        }
    }
    ```
   This will layer the credentials on top of the values found in `common-config.conf`
1. Navigate to the `EmulsiveStoreUi` project and execute to run `mvn test` java playwright tests.

# Test lifecycle

Setup and teardown can be configured to be either at Class level or test level. For example when per class is used, the following resources are created once and re-used across tests in the same class.
- config
- playwright
- browser
- browserContext;
- page;
- ui components (page objects)

## How to configure

Add the attribute before the test class
`@SetupPerClass` or `@SetupPerTest`

## Parallelism

As test execution via commandline uses class level parallelism, with a default of 4 forks, it is important to ensure that test data used across classes is ring-fenced to avoid intermittent clashes.

Tests executed in JUnit via IDE are executed sequentially across classes. This is due to Parallelism configuration clashes between JUnit and Surefire.

