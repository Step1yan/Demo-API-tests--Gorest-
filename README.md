# Lebara WEB Project:
A Maven framework in which to build Selenium tests written in Java language using Cucumber. There is also used Lombok which is a java library that automatically plugs into your editor and build tools.

## Prerequisites:
Before running tests make sure that there is Java installed, you can check it by running "java -version" command in CMD .

By default, the project will be run on Chrome browser.
These test are run only in Chrome browser of 104.0.5112.79 version.
So to run them locally, you must make sure, your Chrome browser version is the same.

## Overview:
The framework was used to test lebara.de// and lebara.fr web pages.

## Setup:
* Used Java 1.8 SDK for Maven project
* Run `git clone *********************`
* install `cucumber.js` plugin from IDE settings

## Framework structure:
* Runner files are
  `src/test/java/runnerDE`
  `src/test/java/runnerFR`
* Cucumber scenarios are located in `src/test/resources/features/*.feature` file
* Cucumber step definitions are located in `src/test/java/stepDefinitions/*` file

## Tests:
* All scenarios from independent of each other
* Logs are displayed during the run

## Run tests:
* Install dependencies in pom.xml file
* Opening `PostPaid.java` file you will see that the "feature" property contains the location of the feature file
* The "glue" property contains the location of the "glue code"
* The "tags" property will help to run a specific scenario
* Replace the tag in the tags property with the tag of the scenario number you wish to run
* If you right click on this file and `Run PostPaid.java`, it will execute all scenarios in the feature file with that tag
* Right-click on the file and `Run PostPaid.java` – the scenario will execute the gherkin steps in the order they are written

## Run all tests using maven commands
* Run all tests using `mvn clean install -U -Dmaven.test.failure.ignore=true` command

## Run specific scenario from cmd
* Run `mvn test -Dcucumber.filter.tags="@tag"` command

## Reporting
* Report is generated in `allure-results` file
* `allure serve` is used for generating report

## Frameworks and plugins:
* Maven
* Selenium
* Cucumber
* Junit

