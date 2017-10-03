# Spock DCOS Code Samples
## purpose
This project demonstrates the how to perform integration tests of [DC/OS](https://dcos.io/) using
Groovy and Spock.  Presented at JavaOne by Ken Sipe.

## getting started
The code is provided using gradle.  So after you clone the repo, just type:

	> ./gradlew test
or

	> gradlw.bat test

This will download the internet... I mean download and bootstrap gradle + all the dependencies for the project including spock.

## dependencies
This project requires the set up of the DC/OS cli configured with an authorized dcos cluster.
To check the following `dcos marathon app list` should provided no error.  The path of the dcos command must be accessible to the spock test.

#### setting up your IDE
The gradle build file is configured to setup either an eclipse or IDEA project.  just type:

	> ./gradlew idea
or

	> ./gradlew eclipse

**I have only tested this on IDEA**

#### reviewing failures at the cmd-line
If a gradle build has a failed test, there isn't much information provided on the cmd-line for what is going on.  The information can be view from stdio if you use the -i switch with gradle such as:

	> ./gradlew test -i


## sharing
you are free to use this as a reference and to share with others... remember where you got it from and share the love!  that includes the awesome guys working spock... namely Peter Niederwieser (@pniederw)
and Rob Fletcher (@rfletcherEW)
