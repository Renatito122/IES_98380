# Maven

## Installing maven
   I tried using the web tutorial given in the guidelines, but it didn't work for me, so what I did was:

	 1. sudo apt update
	 2. sudo apt install maven
	 3. mvn -version (installied maven 3.6.0 successfully -> the command work)

   To update to maven 3.6.2: 

	 1. wget https://www-us.apache.org/dist/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.tar.gz -P /tmp
	 2. sudo tar xf /tmp/apache-maven-*.tar.gz -C /opt
	 3. sudo ln -s /opt/apache-maven-3.6.2 /opt/maven
	 4. sudo nano /etc/profile.d/maven.sh
	 5. cat /etc/profile.d/maven.sh
	 6. sudo chmod +x /etc/profile.d/maven.sh
	 7. source /etc/profile.d/maven.sh
	 8. mvn -version (installed maven 3.6.2 successfully -> the command work)


## Maven in 5 Minutes

### Creating 'my_app' project:
`$ mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4  -DinteractiveMode=false`

**Description:**
 - The `src/main/java` directory contains the project source code.
 -  The `src/test/java` directory contains the test source.
 -  The `pom.xml` file is the project's Project Object Model, or POM.

**The POM:**

The `pom.xml` file is the core of a project's configuration in Maven. It is a single configuration file that contains the majority of information required to build a project.

### Quick commands:

 - Check Maven Version (and check if everything is ok): `$ mvn --version`
 - Create Project (It will be created a directory 'my-app'): `mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false`
 - Build the project: `$ mvn package`
 - Test and execute project: `$ java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App`

**Naming Conventions:** [https://maven.apache.org/guides/mini/guide-naming-conventions.html](https://maven.apache.org/guides/mini/guide-naming-conventions.html)


## Creating a new Project
Example with IPMA Weather API ([http://api.ipma.pt/](http://api.ipma.pt/))

 1. Customize POM Metadata: [https://books.sonatype.com/mvnex-book/reference/customizing-sect-customizing-project-info.html](https://books.sonatype.com/mvnex-book/reference/customizing-sect-customizing-project-info.html)
 2.  Setting Java Version: [https://www.baeldung.com/maven-java-version](https://www.baeldung.com/maven-java-version)
 3. Run Project: 
	 ```
	 $ mvn clean package
	 $ mvn exec:java -Dexec.mainClass="com.Weather.Main"
	```


## Running Maven Tools

#### Maven Phases

These are the most common _default_ lifecycle phases executed.

-   **validate**: validate the project is correct and all necessary information is available
-   **compile**: compile the source code of the project
-   **test**: test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
-   **package**: take the compiled code and package it in its distributable format, such as a JAR.
-   **integration-test**: process and deploy the package if necessary into an environment where integration tests can be run
-   **verify**: run any checks to verify the package is valid and meets quality criteria
-   **install**: install the package into the local repository, for use as a dependency in other projects locally
-   **deploy**: done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects.

There are two other Maven lifecycles of note beyond the _default_ list above. They are

-   **clean**: cleans up artifacts created by prior builds (`mvn clean`)

-   **site**: generates site documentation for this project (`mvn site`)

Phases are actually mapped to underlying goals. The specific goals executed per phase is dependant upon the packaging type of the project. For example, _package_ executes _jar:jar_ if the project type is a JAR, and _war:war_ if the project type is - you guessed it - a WAR.

An interesting thing to note is that phases and goals may be executed in sequence.

`1.  mvn clean dependency:copy-dependencies package`

This command will clean the project, copy dependencies, and package the project (executing all phases up to _package_, of course).

#### Generating the Site

`1.  mvn site`

This phase generates a site based upon information on the project's pom. You can look at the documentation generated under `target/site`.


## Final Questions:

### What is a **Maven Goal?**
A goal is the single unit of task which does some real work.
All goals are provided by plugins, either by default plugins or by user defined plugins (configured in pom file).

**Each phase is a sequence of goals, and each goal is responsible for a specific task.**
When we run a phase – all goals bound to this phase are executed in order.

### What are the main "Maven Goals" and what is the respective excecution order?

Here are some of the phases and default goals bound to them:
-   _compiler:compile_ – the _compile_ goal from the _compiler_ plugin is bound to the _compile_ phase
-   _compiler:testCompile_ is bound to the _test-compile_ phase
-   _surefire:test_ is bound to _test_ phase
-   _install:install_ is bound to _install_ phase
-   _jar:jar_ and _war:war_ is bound to _package_ phase





# Docker


## Orientation and Setup

### List Docker CLI commands
```
docker
docker container --help
```

### Display Docker version and info
```
docker --version
docker version
docker info
```

### Execute Docker image
```
docker run hello-world
```

### List Docker images
```
docker image ls
```

### List Docker containers (running, all, all in quiet mode)
```
docker container ls
docker container ls --all
docker container ls -aq
```


## Containers

### Your new development environment

In the past, if you were to start writing a Python app, your first order of business was to install a Python runtime onto your machine. But, that creates a situation where the environment on your machine needs to be perfect for your app to run as expected, and also needs to match your production environment.

With Docker, you can just grab a portable Python runtime as an image, no installation necessary. Then, your build can include the base Python image right alongside your app code, ensuring that your app, its dependencies, and the runtime, all travel together.

These portable images are defined by something called a `Dockerfile`.

## Define a container with  `Dockerfile`

### Dockerfile

`Dockerfile` defines what goes on in the environment inside the container. 

Access to resources like networking interfaces and disk drives is virtualized inside this environment, which is isolated from the rest of your system, so there is a need to map ports to the outside world, and be specific about what files mest be “copyied in” to that environment.

However, after doing that, one can expect that the build of  the app defined in this `Dockerfile` behaves exactly the same wherever it runs.

### Build the app
```
docker build --tag=<name>:<tag> .
```
For example: `docker build --tag=friendlyhello:v0.0.1 .`

This creates a Docker image, which we’re going to name using the `--tag` option. Use `-t` if you want to use the shorter option.

To see where your image is built use: `docker image ls`.

### Run the app

- Run the app, mapping your machine’s port 4000 to the container’s published port 80 using `-p`:
```
docker run -p 4000:80 friendlyhello
```
You should see a message that your app is running at `http://0.0.0.0:80`. But that message is coming from inside the container, which doesn’t know you mapped port 80 of that container to 4000, making the correct URL `http://localhost:4000`.

- To run the app in the background, in detached mode:
```
docker run -d -p 4000:80 friendlyhello
```

 - To end the proccess  use `docker container stop`, using the `CONTAINER ID`:
```
docker container stop <CONTAINER ID>
```

### Share your image

- **Log in with your Docker ID**

If you don’t have a Docker account, sign up for one at [hub.docker.com](https://hub.docker.com). Make note of your username.

Log in to the Docker public registry on your local machine.
```
$ docker login
```

- **Tag the image**

The notation for associating a local image with a repository on a registry is `username/repository:tag`.
The tag is optional, but recommended, since it is the mechanism that registries use to give Docker images a version. Give the repository and tag meaningful names for the context, such as `get-started:part2`. This puts the image in the `get-started` repository and tags it as `part2`.

Now, put it all together to tag the image. Run `docker tag image` with your username, repository, and tag names so that the image uploads to your desired destination. The syntax of the command is:
```
docker tag image username/repository:tag
```
For example: `docker tag friendlyhello gordon/get-started:part2`

Run `docker image ls` to see your newly tagged image.
```
$ docker image ls

REPOSITORY               TAG                 IMAGE ID            CREATED             SIZE
friendlyhello            latest              d9e555c53008        3 minutes ago       195MB
gordon/get-started       part2               d9e555c53008        3 minutes ago       195MB
python                   2.7-slim            1c7128a655f6        5 days ago          183MB
...
```

- **Publish the image**

Upload your tagged image to the repository:

```
docker push username/repository:tag
```

Once complete, the results of this upload are publicly available. If you log in to [Docker Hub](https://hub.docker.com/), you see the new image there, with its pull command.

- **Pull and run the image from the remote repository**

From now on, you can use `docker run` and run your app on any machine with this command:
```
docker run -p 4000:80 username/repository:tag
```
If the image isn’t available locally on the machine, Docker pulls it from the repository.
```
$ docker run -p 4000:80 gordon/get-started:part2
Unable to find image 'gordon/get-started:part2' locally
part2: Pulling from gordon/get-started
10a267c67f42: Already exists
f68a39a6a5e4: Already exists
9beaffc0cf19: Already exists
3c1fe835fb6b: Already exists
4c9f1fa8fcb8: Already exists
ee7d8f576a14: Already exists
fbccdcced46e: Already exists
Digest: sha256:0601c866aab2adcc6498200efd0f754037e909e5fd42069adeff72d1e2439068
Status: Downloaded newer image for gordon/get-started:part2
 * Running on http://0.0.0.0:80/ (Press CTRL+C to quit)
```
No matter where `docker run` executes, it pulls your image and runs your code. It all travels together in a neat little package, and you don’t need to install anything on the host machine for Docker to run it.


## Cheat Sheet

```
docker build -t friendlyhello .			      # Create image using this directory's Dockerfile
docker run -p 4000:80 friendlyhello			 # Run "friendlyhello" mapping port 4000 to 80
docker run -d -p 4000:80 friendlyhello				    # Same thing, but in detached mode
docker container ls							 # List all running containers
docker container ls -a					 # List all containers, even those not running
docker container stop <hash>           			     # Gracefully stop the specified container
docker container kill <hash>        	      		   # Force shutdown of the specified container
docker container rm <hash>        		        # Remove specified container from this machine
docker container rm $(docker container ls -a -q)         	    	       # Remove all containers
docker image ls -a                             			     # List all images on this machine
docker image rm <image id>            			    # Remove specified image from this machine
docker image rm $(docker image ls -a -q)   		         # Remove all images from this machine
docker login             		       # Log in this CLI session using your Docker credentials
docker tag <image> username/repository:tag  			  # Tag <image> for upload to registry
docker push username/repository:tag            			     # Upload tagged image to registry
docker run username/repository:tag                   			   # Run image from a registry
```


## Portainer

- **Create portainer volume**
 ```
docker volume create portainer_data
```
- **Run portainer**
```
docker run -d -p 8000:8000 -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer
```


## References and Resources

- [Get Started | Docker Documentation](https://docs.docker.com/get-started/)
- [Portainer Installation](https://www.portainer.io/installation/)




## Review Questions

A) The main phases in the default lifecycle are:
    **validate**: validate the project is correct and all necessary information is available
-   **compile**: compile the source code of the project
-   **test**: test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
-   **package**: take the compiled code and package it in its distributable format, such as a JAR.
-   **integration-test**: process and deploy the package if necessary into an environment where integration tests can be run
-   **verify**: run any checks to verify the package is valid and meets quality criteria
-   **install**: install the package into the local repository, for use as a dependency in other projects locally
-   **deploy**: done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects.


B) Not really, Maven has plugins that enable it to perform manual tests, however there are no built-in systems to enable this type of testing, meaning that Maven is not properly set up to run projects.


C) Assuming that you have already cloned the repository:
	git pull
   ...increment development...
   	git add <name of altered files>
   	git commit
   	git push
   	
   	
D) Good commit messages should be concise, informative and explicit


E) A production database needs to store a very large amount of data, larger than most programs that Docker is prepared to run.
   As such, it's important to configure the volumes in this case, as the fact the amount of stored data is much larger than expected means that the volume space automatically prepared by Docker is some times not enough.
