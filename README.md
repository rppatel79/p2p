# p2p
A standalone java application to invest in [P2P Lending](http://www.shareable.net/blog/an-introduction-to-peer-to-peer-lending) websites.  The framework has been setup multiple P2P originators including  [Lending Club](http://www.lendingclub.com) and [Prosper](http://www.prosper.com).  The [Lending Club](http://www.lendingclub.com) implementation is actively used, while the  [Prosper](http://www.prosper.com) implementation is beta.

###Project Setup
This project leverages various external libraries including:
* Spring Framework
* codesnippets4all
* hibernate
<p>The deployment (and dependancies) are maintained via [Maven](https://maven.apache.org/).

Build the project using the mvn command:
<p>mvn clean compile assembly:single
<p>This builds a single jar named *p2p-java-client-1.0-SNAPSHOT-jar-with-dependencies.jar*

###Deployment
The only external setup that is required is:
* [Java 1.7 (or greater)](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* p2p.properties - All the properties for the project.  The location of the file can be overriden with the JVM property *-Dproperties*

It should be noted, this project code can easily be deployed using [AWS](http://aws.amazon.com).

When new loans are issued, the jar is executed via crontab (with the parameters *true*) and loans are purchased.  For lending club, I use the following crontab schedule.  
<p>1 10,14,18,22 * * *
<p>*NOTE:* Servers are in UTC time.

###Framework
Major application components:
* *LoansSelector* - The interface to filter loans.  Use this interface to add additional filters for your P2P model.
* *OriginatorApi* - The interface for the P2P originators.  I have integrated with [Lending Club](http://www.lendingclub.com) and [Prosper](http://www.prosper.com).  When producing this document, Lending Club was heavy tested (and actively used).  Use this interface to integrate with other P2P originators.
* com.rp.p2p.model.* - A *sloppy* data model.  This is common data that is used for all originators.  This data model is heavly based upon Lending Club's data model.
