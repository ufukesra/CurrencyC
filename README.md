Full Name : Ufuk Sahinduran
Project Title : CurrencyCloud QA Engineer-API Task
Installation guide: This is a maven project. so It's assumed that the host running this code have Java and maven installed and JAVA_HOME already set. Here is the guide for maven installation just in link : https://maven.apache.org/install.html
Project get all dependencies from maven repository. So no additional installation needed.
Below are the dependencies are being used for this project :
Cucumber, Junit, RestAssured,
Credentials : CurrencyCloud API use api_key, login_id and then create a token to authorize the request. to externalize the data a configuration reader utility are created to read configuration.properties. Due to sensitive nature of the keys , user is expected to provide his/her own key while running the tests.
Build and run your project : This is maven project and tests are written in Cucumber and JUnit. so we use cucumber runner class to run the project as below to pick up test: mvn clean test
This is a BDD framework because Cucumber natively supports BDD. It is created basic Page Object Model (POM) design pattern.

All the tests executed first with JUnit and @Test annotation then converted to cucumber with a scenario in feature file then step definition methods created based on scenario
then called all the methods by creating CurrencyClass object in step definition.
Then executed the test and all the tests PASSED.
Also each json body stored in  the files agains to lost authorization for going on coding.
