My assumptions
==============

  * The assignment doesn't specify which language to use - therefore I've chosen Scala version of Spark
  * I'd like to implement the `bonus` assignment - however the specification isn't clear to me. The term frequency is
    defined as the frequency of occurrence within the text file. How document frequency (frequency of occurrence within 
    the document) is different from that definition?
  * Unfortunately in the time of writing of the solution I don't have access to actual Spark cluster - code works fine 
    in local mode but I can't verify eg. if there any problems with serialization/deserialization. 

Building and running
====================

  * Building: `$ ./gradlew distZip`
  * To run:
    * Unzip `$ unzip build/distributions/ex4.zip`
    * Run: 

    ```
     $ ./ex4/bin/ex4 local gradlew
    ```
