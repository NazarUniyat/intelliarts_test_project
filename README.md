# intelliarts test application
This application gives you an opportunity to manage purchases in A small store. You can calculate income for a year in any currency. 
Using this service you can input information about purchase in format 'date/price/currency/name' and save it to MySql db. Also, you will have information about all purchases with pagination and opportunity to delete purchases by selected date.
## Getting Started

### Prerequisites

In order to run this application you will need

```
Git
JDK 8 or later
Maven 3.6.0 or later
MySql server
```

### Clone

At first you should clone project using URL

```
https://github.com/NazarUniyat/intelliarts_test_project
```

### Configuration

In `application.properties` file you should change application host for pagination 

```
characters.pagination.link=http://{app.host}.{port}/all?page=
```
default app host in URL:
```
results.pagination.link=http://localhost:8080/all?page=
```
Also you need to input your unique fixer key
```
http://data.fixer.io/api/latest?access_key={input your key}&format=1
```
### Database(MySQL) configuration
At first you should run MySql server and create database(default name in following properties - shop)
After that, change connection properties in `application.properties` and set your connection string. 
By default configuration is :
```
spring.datasource.url=jdbc:mysql://localhost:3306/shop?serverTimezone=UTC
```
And set your username and password
```
spring.datasource.username={username}
spring.datasource.password={password}
```
### Running the tests

Then, run all tests
```
mvn test
```

### Build an executable JAR

You can run the application from the command line using:

```
mvn spring-boot:run
```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:
```
mvn clean package
```
Then you can run the JAR file with:
```
java -jar target/*.jar
```

## Author

* **Nazar Uniyat** - [GitHub link](https://github.com/NazarUniyat)

