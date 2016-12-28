# stream-utils
A util for streams.

This uses maven and Spring tools suite. If you do not have maven, it can be installed here: https://maven.apache.org/download.cgi
I suggest also getting java jdk 8 if you do not have it; it can be found here: 
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

To use this application in any JVM, just do a quick mvn clean install and put the utils.test.uberjar-{$version}.jar
into the classpath and away you go. All methods are available through the singleton class StreamUtils.

Furthermore, if you want a slick UI to do your manual PDF concatanation or Zipfile Concatanation, just java -jar utils.test.uberjar-{$version}.jar, and jump to http://localhost:4567/
select which mode you want using the blue buttons, drop your files into the dropzone, and hit download.
