## Installing Apache Tomcat

1. `mkdir /opt/tomcat`
2. `cd /opt/tomcat`
3. `wget http://apache.spinellicreations.com/tomcat/tomcat-8/v8.5.32/bin/apache-tomcat-8.5.46.tar.gz`
4. `tar xvzf apache-tomcat-8.5.46.tar.gz`
5. `gedit ~/.zshrc`
6. `export CATALINA_HOME=/opt/tomcat/apache-tomcat-8.5.46`
7. Activate the modifications to the file: `. ~/.zshrc`
8. Configure the manage user in the `opt/tomcat/conf/tomcat-users.xml` (using vscode: `code tomcat-users.xml --user-data-dir`):
	```
	<role  rolename="manager-gui"/>
	<role  rolename="manager-script"/>
	<user  username="admin"  password="admin"  roles="manager-gui, manager-script"/>
	```
9. Run server: `$CATALINA_HOME/bin/startup.sh`
10. Navigate to `http://localhost:8080/manager` and when prompted for user name and password enter `admin/admin` 


## Create maven-based web application project from an external archetype

1.
	- archetypeGroupId = `org.codehaus.mojo.archetypes`
	- archetypeArtifactId = `webapp-javaee7`
	- archetypeVersion = `1.1`

		The actual project group_id and artifact_id can be whatever you may want.

2. Execute `mvn install`.
3. To deploy packed application into the application server there are 2 alternatives:
	- Use the Tomcat management interface (Tomcat manager application) to deploy a .war file [http://localhost:8080/manager](http://localhost:8080/manager).
	- The productive alternative is to use the IDE integrated deployment support. [IntelliJ configuration](https://www.mkyong.com/intellij/intellij-idea-run-debug-web-application-on-tomcat/).


## Add a basic servlet to the project
Follow these [instructions](https://howtodoinjava.com/servlets/complete-java-servlets-tutorial/#webservlet_annotation) (from section “Develop Servlet with @WebServlet Annotation” to section “Handling Servlet Request and Response”).

#### Here's an excerpt of the server log messages in a successfull deployment:
```
12-Oct-2019 12:28:28.389 INFO [main] org.apache.catalina.core.StandardEngine.startInternal Starting Servlet Engine: Apache Tomcat/8.5.46
12-Oct-2019 12:28:28.428 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8081"]
12-Oct-2019 12:28:28.476 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
12-Oct-2019 12:28:28.493 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in 173 ms
Connected to server
[2019-10-12 12:28:28,564] Artifact ies21:war exploded: Artifact is being deployed, please wait...
[2019-10-12 12:28:29,161] Artifact ies21:war exploded: Artifact is deployed successfully
[2019-10-12 12:28:29,161] Artifact ies21:war exploded: Deploy took 597 milliseconds
12-Oct-2019 12:28:38.433 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory [/opt/tomcat/apache-tomcat-8.5.46/webapps/manager]
12-Oct-2019 12:28:38.551 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/opt/tomcat/apache-tomcat-8.5.46/webapps/manager] has finished in [117] ms
```

#### Responsibilities/services of a “servlet container”

**1. What is a Java Servlet?**
> The basic idea of Servlet container is using Java to dynamically generate the web page on the server side. So servlet container is essentially a part of a web server that interacts with the servlets.
> 
> -- <cite>[What is a Servlet Container?](https://dzone.com/articles/what-servlet-container)</cite>

**2. How Servlet container and web server process a request?**
-   Web server receives HTTP request.
-   Web server forwards the request to servlet container.
-   The servlet is dynamically retrieved and loaded into the address space of the container, if it is not in the container.
-   The container invokes the `init()` method of the servlet for initialization(invoked once when the servlet is loaded first time).
-   The container invokes the `service()` method of the servlet to process the HTTP request, i.e., read data in the request and formulate a response.

	The servlet remains in the container’s address space and can process other HTTP requests.
-   Web server return the dynamically generated results to the correct location.


## Deploy your web app (Tomcat) in a docker container
1.  `docker image pull tomcat:8.0`
2. `docker container create --publish 8082:8080 --name my-tomcat-container tomcat:8.0`
3. `docker container start my-tomcat-container`
4. Create a file with name `Dockerfile` in **_root directory of your_** application without any extension and add following lines in it.
	```
	# we are extending everything from tomcat:8.0 image ...  
	FROM tomcat:8.0
	MAINTAINER your_name
	
	# COPY _path-to-your-application-war_ _path-to-webapps-in-docker-tomcat
	COPY some-app/target/some-app.war /usr/local/tomcat/webapps/
	```
5. `docker image build -t your_name/some-app-image ./`
6. `docker container run -it --publish 8081:8080 your_name/some-app-image`

Your application can be accessed in _**http://localhost:8081**_.





# Introduction to web apps with Spring Boot

## Demo app using Spring Boot io
Using [Spring Initializr](https://start.spring.io/) and adding the “Spring web” dependency, we get a demo zip with all the content of the web app.

After we unzip the file, execute these commands:
1. `mvn package`
2. `java -jar target/demo-0.0.1-SNAPSHOT.jar`

After this, we acces the page _[localhost:8080](http://localhost:8080/)_ and get the following output:
```
# Whitelabel Error Page

This application has no explicit mapping for /error, so you are seeing this as a fallback.

Sat Oct 12 22:18:23 WEST 2019

There was an unexpected error (type=Not Found, status=404).

No message available
```

## Getting Started: Serving Web Content with Spring MVC

The following steps are the result of following the tutorial https://spring.io/guides/gs/serving-web-content/.
 
### 1. Build the Maven Project

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Maven](https://maven.apache.org) is included here. If you’re not familiar with Maven, refer to [Building Java Projects with Maven](https://spring.io/guides/gs/maven).

### 2. Create the directory structure

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

└── src
    └── main
        └── java
            └── hello

`pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.springframework</groupId>
    <artifactId>gs-serving-web-content</artifactId>
    <version>0.1.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.6.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

The [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin) provides many convenient features:

-   It collects all the jars on the classpath and builds a single, runnable "über-jar", which makes it more convenient to execute and transport your service.
    
-   It searches for the `public static void main()` method to flag as a runnable class.
    
-   It provides a built-in dependency resolver that sets the version number to match [Spring Boot dependencies](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-dependencies/pom.xml). You can override any version you wish, but it will default to Boot’s chosen set of versions.

### 3. Create a web controller

In Spring’s approach to building web sites, HTTP requests are handled by a controller. You can easily identify these requests by the [`@Controller`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/stereotype/Controller.html) annotation. In the following example, the GreetingController handles GET requests for /greeting by returning the name of a [`View`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/View.html), in this case, "greeting". A `View` is responsible for rendering the HTML content:

`src/main/java/hello/GreetingController.java`

```java
package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
```

This controller is concise and simple, but there’s plenty going on. Let’s break it down step by step.

The `@GetMapping` annotation ensures that HTTP GET requests to `/greeting` are mapped to the `greeting()` method.

[`@RequestParam`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html) binds the value of the query String parameter `name` into the `name` parameter of the `greeting()` method. This query String parameter is not `required`; if it is absent in the request, the `defaultValue` of "World" is used. The value of the `name` parameter is added to a [`Model`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/ui/Model.html) object, ultimately making it accessible to the view template.

The implementation of the method body relies on a [view technology](https://spring.io/understanding/view-templates), in this case [Thymeleaf](http://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html), to perform server-side rendering of the HTML. Thymeleaf parses the `greeting.html` template below and evaluates the `th:text` expression to render the value of the `${name}` parameter that was set in the controller.

`src/main/resources/templates/greeting.html`

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
    <p th:text="'Hello, ' + ${name} + '!'" />
</body>
</html>
```

### 4. Developing web apps

A common feature of developing web apps is coding a change, restarting your app, and refreshing the browser to view the change. This entire process can eat up a lot of time. To speed up the cycle of things, Spring Boot comes with a handy module known as [spring-boot-devtools](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools).

-   Enable [hot swapping](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-hotswapping)
    
-   Switches template engines to disable caching
    
-   Enables LiveReload to refresh browser automatically
    
-   Other reasonable defaults based on development instead of production
    

### 5. Make the application executable

Although it is possible to package this service as a traditional [WAR](https://spring.io/understanding/WAR) file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring’s support for embedding the [Tomcat](https://spring.io/understanding/Tomcat) servlet container as the HTTP runtime, instead of deploying to an external instance.

`src/main/java/hello/Application.java`

```java
package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

`@SpringBootApplication` is a convenience annotation that adds all of the following:

-   `@Configuration`: Tags the class as a source of bean definitions for the application context.
    
-   `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if `spring-webmvc` is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a `DispatcherServlet`.
    
-   `@ComponentScan`: Tells Spring to look for other components, configurations, and services in the `hello` package, letting it find the controllers.
    

The `main()` method uses Spring Boot’s `SpringApplication.run()` method to launch an application. Did you notice that there was not a single line of XML? There is no `web.xml` file, either. This web application is 100% pure Java and you did not have to deal with configuring any plumbing or infrastructure.

### 6. Build an executable JAR

You can run the application from the command line with Gradle or Maven. You can also build a single executable JAR file that contains all the necessary dependencies, classes, and resources and run that. Building an executable jar so makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

If you use Gradle, you can run the application by using `./gradlew bootRun`. Alternatively, you can build the JAR file by using `./gradlew build` and then run the JAR file, as follows:

java -jar build/libs/gs-serving-web-content-0.1.0.jar

If you use Maven, you can run the application by using `./mvnw spring-boot:run`. Alternatively, you can build the JAR file with `./mvnw clean package` and then run the JAR file, as follows:

java -jar target/gs-serving-web-content-0.1.0.jar

The steps described here create a runnable JAR. You can also [build a classic WAR file](https://spring.io/guides/gs/convert-jar-to-war/).

Logging output is displayed. The app should be up and running within a few seconds.

### 7. Test the App

Now that the web site is running, visit [http://localhost:8080/greeting](http://localhost:8090/greeting), where you see:

"Hello, World!"

Provide a `name` query string parameter with [http://localhost:8080/greeting?name=Renato](http://localhost:8080/greeting?name=Renato). Notice how the message changes from "Hello, World!" to "Hello, User!":

"Hello, User!"

This change demonstrates that the [`@RequestParam`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html) arrangement in `GreetingController` is working as expected. The `name` parameter has been given a default value of "World", but can always be explicitly overridden through the query string.

### 8. Add a Home Page

Static resources, like HTML or JavaScript or CSS, can easily be served from your Spring Boot application just by dropping them into the right place in the source code. By default Spring Boot serves static content from resources in the classpath at "/static" (or "/public"). The `index.html` resource is special because it is used as a "welcome page" if it exists, which means it will be served up as the root resource, i.e. at `http://localhost:8080/` in our example. So create this file:

`src/main/resources/static/index.html`

```html
<!DOCTYPE HTML>
<html>
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
    <p>Get your greeting <a href="/greeting">here</a></p>
</body>
</html>
```
and when you restart the app you will see the HTML at [http://localhost:8080](http://localhost:8080/).


### Rest Aproach
Follow the tutorial [Getting Started | Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/).

To view the json returned by our recenlty created rest-api endpoint, we can either visit the url as usual (which we shouldn't do since we're now working with a rest-api that's supposed to handle requests and give responses in the backend and not in an html frontend kind of way), or with the app **Postman**.


### Elaborate on the differences between deploying a Web Application to standalone applications servers and embedded servers

Firstly, we should distinguish **Standalone servers** from **Embedded servers**.

**Embedded application servers** are HTTP servers that run inside the same process space as your application. Your application is responsible for starting the server and is also responsible for configuring the server, often programmatically. 

In contrast, a **Standalone application server** is an HTTP server that runs separately from your application. The server  is configured using separate config files, forwards requests to your application and might be responsible for loading your application.

Now, I will present some pros and cons for each model.

#### Embedded Application Servers

**Pros:**
- More self-contained applications. This helps a lot during development.
- As a dependency of your application, you can test against server versions just like any other dependency.
- More control over how the web server behaves (custom filters, headers, caching).
- Single object to be deployed.

**Cons:**
 - Your application has to be designed around the API of whatever server you are using, making it harder to change servers later. (Java doesn't really have this problem, as you can still use the servlet API when embedding.)
 - Dependency bloat, as you have to include all the dependencies of the web server.
- More effort to deploy hotfixes to security exploits in the server.
- You can't group multiple applications behind one server without a proxy.
- A single uncaught exception is enough to take down the entire application server.

#### Standalone servers

**Pros:**
- Potentially more flexible application architecture.
- Really easy to switch servers later.
- Application errors can't harm the server.
- Easy to deploy app updates without restarting the server.
- Performance and correctness: servers like [nginx](https://nginx.org/en/) are _highly_ optimized and tested for complete HTTP correctness, which your app then gets for free.

**Cons:**
- Extra performance overhead: there could be anything from an extra layer of method abstraction up to CGI-level overhead for your app and the server to communicate.
- Deployment complexity: you have to maintain the web server and the application, deploy them individually, ad hoc version testing, etc.
- Trickier development environment.


### Give specific examples of annotations in Spring Boot that implement the principle of convention-over-configuration

> For a lot of projects, **sticking to established conventions and having reasonable defaults is just what they (the projects) need**... this theme of convention-over-configuration now has explicit support in Spring Web MVC. What this means is that **if you establish a set of naming conventions and suchlike, you can _substantially_ cut down on the amount of configuration that is required to set up handler mappings, view resolvers, `ModelAndView` instances, etc.** This is a great boon with regards to rapid prototyping, and can also lend a degree of (always good-to-have) consistency across a codebase should you choose to move forward with it into production._ _This convention over configuration support address the three core areas of MVC - namely, the models, views, and controllers._
> 
> [Spring Docs](https://docs.spring.io/spring/docs/3.0.0.M3/spring-framework-reference/html/ch16s10.html)

Thus, these are some examples of Spring Boot annotations:
-   @Bean - indicates that a method produces a bean to be managed by Spring.
-   @Repository - indicates that an annotated class is a repository, which is an abstraction of data access and storage.
-   @Configuration - indicates that a class is a configuration class that may contain bean definitions.
-   @Controller - marks the class as web controller, capable of handling the requests.
-   @RequestMapping - maps HTTP request with a path to a controller method.
-   @SpringBootApplication - enables Spring Boot autoconfiguration and component scanning.





## Review Questions


A) What are the responsibilities/services of a “servlet container”?

   Read the explicit data sent by the clients (browsers). This includes an HTML form on a Web page or it could also come from an applet or a custom HTTP client program.
   Read the implicit HTTP request data sent by the clients (browsers). This includes cookies, media types and compression schemes the browser understands, and so forth.
   Process the data and generate the results. This process may require talking to a database, executing an RMI or CORBA call, invoking a Web service, or computing the response directly.
   Send the explicit data (i.e., the document) to the clients (browsers). This document can be sent in a variety of formats, including text (HTML or XML), binary (GIF images), Excel, etc.
   Send the implicit HTTP response to the clients (browsers). This includes telling the browsers or other clients what type of document is being returned (e.g., HTML), setting cookies and caching parameters, and other such tasks.
  
      
B) Explain, in brief, the “dynamics” of Model-View-Controller approach used in Spring Boot to serve web content.

   The Model-View-Controller (MVC) architecture for building web applications is one of the oldest paradigms in computer science. The MVC architecture for web applications is supported by the Spring MVC component of the Spring framework.
   An HTTP request is sent to the Java Servlet container. The HTTP request is first intercepted by the controller written in Java. The controller returns a Java Server Page (JSP) which is rendered on the server and returned to the web browser as static HTML.


C) Inspect the POM.xml for the previous SpringBoot projects. What is the role of the “starters” dependencies?

   The starter dependencies allow the simplification of the system building, by padronizing certain aspects of it.
  
D) Which annotations are transitively included in the @SpringBootApplication?

   A single @SpringBootApplication annotation can be used to enable those three features, that is:

      @EnableAutoConfiguration: enable Spring Boot’s auto-configuration mechanism

      @ComponentScan: enable @Component scan on the package where the application is located (see the best practices)

      @SpringBootConfiguration: enable registration of extra beans in the context or the import of additional configuration classes. An alternative to Spring’s standard @Configuration that aids configuration detection in your integration tests.
      
E) Search online for the topic “Best practices for REST APIdesign”. From what you could learn, select your “top 5” practices, and briefly explain them in you own words.

  *1. REST API should Accept and Respond with JSON*
    JSON is a known language and very widely used so accept and responding with it is advantageous.
  
  *2. Well compiled documentation*
    Documentation is one of the important but highly ignored aspects of a REST API structure. The documentation is the first point in the hands of customers to understand the product and critical deciding factor whether to use it or not. One good documentation is neatly presented in a proper flow to make an API development process quicker.
    
  *3. Return Error Details in the Response Body*
    It is convenient for the API endpoint to return error details in the JSON or response body to help a user with debugging. If you can explicitly include the affected field in error, this will be special kudos to you.
  
  *7. Use Resource Nesting*
    Resource objectives always contain some sort of functional hierarchy or are interlinked to one another. However, it is still ideal to limit the nesting to one level in the REST API, as it overcomplicates the the distribution of work through the mappings. 

  *9. Secure your API*
  The API needs to follow proactive security measures to run operations while safeguarding sensitive data smoothly. Relevant security standards for the security of the API.
