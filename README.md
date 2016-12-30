# stream-utils

This is a utility for some stream manipulation that I have found useful so far. All methods are available through the singleton class StreamUtils. 

copy takes in an inputstream, and an outputstream and copies all the information inside the inputstream into an outputstream.

makeZipFile takes a path to a folder as a parameter, maps all the files in the folder into a zipoutputstream and outputs the zip file into the output path. It can also take in a Map of filenames and their corresponding inputstreams, make a zipfile out of those files and save it into the passed in output stream. 

mergePDF takes in a list of PDF input streams and concatanates the PDFs together and then saves the resulting single PDF into the passed in output stream.

This uses maven and Spring tools suite. If you do not have maven, it can be installed here: https://maven.apache.org/download.cgi
I suggest also getting java jdk 8 if you do not have it; it can be found here: 
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

To use this application in any JVM, just do a quick mvn clean install and put the utils.test.uberjar-{$version}.jar
into the classpath and away you go.

Furthermore, if you want a slick UI to do your manual PDF concatanation or Zipfile Compilation, just java -jar utils.test.uberjar-{$version}.jar, and jump to http://localhost:4567/
select which mode you want using the blue buttons, drop your files into the dropzone, and hit download.
