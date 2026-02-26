To Run program you need two terminals open. 

In terminal 1 run. 
1. cd /Users/joe/Desktop/CSC/364/CSC_364/src                                                                                                                                     
2. mvn clean compile
3. mvn exec:java -Dexec.mainClass="Program1.Main"

In terminal 2 run. 
1. cd /Users/joe/Desktop/CSC/364/CSC_364/src                                                                                                                                     
2. mvn clean compile
3. mvn exec:java -Dexec.mainClass="Program2.ExternalWorker"

Program Should Display both jobs and their results in each terminal.
