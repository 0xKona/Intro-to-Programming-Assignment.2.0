# Intro to Programming Assignment - PART ONE

# Overview:
- The code in this branch is for Part One of the assignment, it comprises a CLI user interface and a text file
  based storage system acting as a basic "database".
- The project uses [Gradle](https://gradle.org/) as it's dependency manager and build tool.
- Some common commands for this project are:
- ```./gradle clean build``` - This cleans out the existing builds and builds the project into a .jar file.
- ```./gradle test``` - Runs the JUnit tests for the project (These are also run during builds).

# Getting Started (IntelliJ):

- To download the repository files, Clone the repository from GitHub [here](https://github.com/0xKona/I2PAssignmentv2)
- If you don't know how to clone the files from GitHub, follow [this](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository) guide.
- You'll need to use `git checkout part-1` to go to the PART ONE branch, run this in a terminal at the root of the project. [You will need GIT installed for this to work](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- If you have the files already (I.E When I submit this as a ZIP file etc.) you can skip the above steps
- Open the project in IntelliJ using `file -> open -> navigate the folder location`
- Ensure project is saved inside a folder named `InventoryManagementSystem` if it is not already, failure to do so has been known to cause issues in development mode


# Getting Started (Eclipse):

- This project was written using IntelliJ however attempts to open the project and run it in eclipse were successful.
- Follow first two steps in the IntelliJ guide if needed.
- In eclipse go to `file -> open project from file system`  and set import source to project location and again ensure project is saved inside a folder named `InventoryManagementSystem` if it is not already, failure to do so has been known to cause issues in development mode
- You may notice that eclipse generate some files when it opens the project, this is fine and is normal.




## Running a .jar build:

- Included is an executable jar file, you can run this by opening a terminal in the root of the project and running the following command:
- ```java -jar exampleBuild/InventoryManagementSystem-1.0-SNAPSHOT.jar```
- You may need Java 21 or above to run the jar.
- You can also create a new build by running ./gradlew clean build, the generated build will be located in `build/libs` and can be run with this command:
- ```java -jar build/libs/InventoryManagementSystem-1.0-SNAPSHOT.jar```

## Documentation:
- Each class and public method has a Java Doc comment explaining its function and any arguments required and return values.
- If using IntelliJ (And most modern IDE's) this enables you to hover over a usage, and it will quickly give more information about the class / method.

## Testing:
- Tests a written using [JUnit](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/) for mocking.
- Tests are run during the build process, the build process will not complete unless all tests pass ensuring that 'degredation' does not occur adn that any new changes or features do not break the functionality of the application, however in circumstances the tests may need to be updated if the codebase changes drastically.
- You can run the tests by opening a terminal at the root of the project and running:
- ```./gradlew test```
- After running a test the project uses [Jacoco](https://github.com/jacoco/jacoco) To generate a code coverage report which tells you how much of your code is tested.
- You can open this report by navigating to `build/jacocoHtml` and opening the `index.html` file in your browser (You can usually just right click -> open in browser)

## UML Design:

To create the UML Designs i've used IntelliJ's built-in diagram tool and [PlantUML](https://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa700003)

![UML Diagram](UML-Diagrams/UML-Diagram.png)