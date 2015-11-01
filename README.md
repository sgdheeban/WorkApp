# WorkApp
Client-Server Codebase for WorkApp, a platform for connecting talents with customers around the globe  

## Requirements

* Linux (Preferred) or Windows 
* Java 8 Update 40 or higher (8u40+), 64-bit
* Maven 3.2.3+ (for building)
* Python 2.4+ (for running with the launcher script)

## Building WokApp

WorkApp is a standard Maven project. Simply run the following command from the project root directory:

    mvn clean install

On the first build, Maven will download all the dependencies from the internet and cache them in the local repository (`~/.m2/repository`), which can take a considerable amount of time. Subsequent builds will be faster.

WorkApp has a comprehensive set of unit tests that can take several minutes to run. You can disable the tests when building:

    mvn clean install -DskipTests
    
## Running Presto in your IDE

### Overview

After building WorkApp for the first time, you can load the project into your IDE and run the server. We recommend using Eclipse IDE Neon for Java Developers (http://www.eclipse.org/downloads/packages/release/Neon/M1), but you should be able to build this on any latest release of Eclipse IDE or IntelliJ IDEA](http://www.jetbrains.com/idea/). Because WorkApp is a standard Maven project, you can import it into your IDE using the root `pom.xml` file. In Eclipse IDE, choose "Import > Existing Maven Project from the File menu" and select the root `pom.xml` file.

After opening the project in Eclipse IDE, double check that the Java SDK is properly configured properly for the project:

* Right click on the Root Project, WorkApp and choose Build Path, and choose Java SDK Installation
* In the SDKs section, ensure that a 1.8 JDK is selected (create one if none exist)
* In the Java Compiler section, ensure the Project language level is set to 8.0 as WorkApp makes use of several Java 8 language features

WorkApp comes with sample configuration that should work out-of-the-box for development. Use the following options to create a run configuration:

* Main Class: `com.workappinc.workapp.server.WorkAppServer`
* VM Options: `-ea -Xmx2G -Dconfig=etc/config.properties -Dlog.levels-file=etc/log.properties`
* Working directory: `$MODULE_DIR$`
* Use classpath of module: `workapp-main`

The working directory should be the `workapp-main` subdirectory. In EclipseIDE, using `$MODULE_DIR$` accomplishes this automatically.
