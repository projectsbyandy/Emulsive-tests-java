# Introduction
This is a UI automation framework that uses
- playwright for browser driven tests
- Java specific
  - Guice for DI
  - Config with HOCON support
  - JUnit test runner with tagging
    - surefire tag profiles
      `mvn test -P smoke`
    - tests configured for ***parallel execution***

# Getting Started
## Pre-req
1. Launch the Emulsive TS Store locally
  - navigate to the github project [home](https://github.com/projectsbyandy/emulsive-ts-store) and clone the repo
  - setup the project by executing `npm install`
  - start the services (ui, api, image server) executing `npm run all`
  - OPTIONAL - run `npm run tests:playwright` to run regression tests to ensure website is functional.
1. Navigate to the `EmulsiveStoreUi` project and execute to run `mvn test` java playwright tests.

# Test lifecycle
Per test
- playwright
- browser
- browserContext;
- page;
- ui components (page objects)

