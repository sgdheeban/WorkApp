# WorkApp
Client-Server Codebase for WorkApp, a platform for connecting talents with customers around the globe.  

## Requirements

* Linux (Preferred) or Windows 
* Java 8 Update 40 or higher (8u40+), 64-bit
* Maven 3.2.3+ (for building)
* Python 2.4+ (for running with the launcher script)

## Create MySQL schema

Create the following test database and table in MySQL for successful execution of Examples and JUnit tests (Ensure the MySQL server is up and running). Alternatively, you can use the --createMySQLSchema option through the commandline option args, and generate the schema listed in the mysql_schema.sql under the config folder in the root directory of WorkAppServer project (this includes the test db and table). To understand more about using WorkAppScriptRunnerUtil, refer to WorkAppServer/WorkAppScriptRunnerInstruction.md in the project. To execute ORM related testcases and examples, create the tables using the mysql_schema_orm_test.sql in the WorkAppServer/src/main/resources folder. To understand more about WorkApp's ORM Engine, refer to WorkAppServer/WorkAppOrmInstruction.md.

    CREATE DATABASE testdb IF NOT EXISTS;
    USE testdb;
    CREATE TABLE user (name text, age int(11)); 

## Building WokApp

WorkApp is a standard Maven project. Simply run the following command from the project root directory:

    mvn clean install

On the first build, Maven will download all the dependencies from the internet and cache them in the local repository (`~/.m2/repository`), which can take a considerable amount of time. Subsequent builds will be faster.

WorkApp has a comprehensive set of unit tests that can take several minutes to run. You can disable the tests when building:

    mvn clean install -DskipTests
    
## Running WorkApp in your IDE

### Overview

After building WorkApp for the first time, you can load the project into your IDE and run the server. We recommend using Eclipse IDE Neon for Java Developers (http://www.eclipse.org/downloads/packages/release/Neon/M1), but you should be able to build this on any latest release of Eclipse IDE or IntelliJ IDEA](http://www.jetbrains.com/idea/). Because WorkApp is a standard Maven project, you can import it into your IDE using the root `pom.xml` file. In Eclipse IDE, choose "Import > Existing Maven Project from the File menu" and select the root `pom.xml` file.

After opening the project in Eclipse IDE, double check that the Java SDK is properly configured properly for the project:

* Right click on the Root Project, WorkApp and choose Build Path, and choose Java SDK Installation
* In the SDKs section, ensure that a 1.8 JDK is selected (create one if none exist)
* In the Java Compiler section, ensure the Project language level is set to 8.0 as WorkApp makes use of several Java 8 language features

WorkApp comes with sample configuration that should work out-of-the-box for development. Use the following options to create a run configuration:

* Main Class: `com.workappinc.workapp.server.WorkAppServer`
* VM Options: `-ea -Xmx2G`
* VM Options for enabling Allocation Tracker : `-javaagent:local-maven-repo-path-to-jar/java-allocation-instrumenter-3.0.jar`
* VM Options for enabling Allocation Tracker-Example: `-javaagent:/home/dhgovindaraj/.m2/repository/com/google/code/java-allocation-instrumenter/java-allocation-instrumenter/3.0/java-allocation-instrumenter-3.0.jar`
* Working directory: `$MODULE_DIR$`
* Use classpath of module: `WorkApp`

The working directory should be the `WorkApp` subdirectory. In EclipseIDE, using `$MODULE_DIR$` accomplishes this automatically.
For more info on Allocation Tracker, please check here : `https://github.com/google/allocation-instrumenter`
