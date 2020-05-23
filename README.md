# Dummy
The dummy will contain the connectors for hive and Hbase.
For hive there two approaches , file based and jdbc .
For hive the data will be generated in memory called fake objects ,and on the based of the apporach these object will be persisted into hive .
To Run the Hive job i have used the oozie workflow but without oozie you can also run this job , with shell script .
java -Xms4096m -Xmx4096m -cp Dummy-0.0.1-SNAPSHOT.jar:./conf/:./lib:$HADOOP_CLASSPATH Main_Class 10000000 true 10000 
here you will see i am passing the argument while running the jar ,first param represents that no of records to be saved in batches and 2nd param choose the approach , file based or jdbc ,true will run jdbc approach ,
3rd param will reperesent the size of records saved into one go.
java -Djavax.security.auth.useSubjectCredsOnly=false   -cp /etc/hadoop/conf:/etc/hadoop/conf/log4j.properties:./conf:Dummy-0.0.1-SNAPSHOT.jar com.dummy.job.HBaseMain
So in the case of Hbase the job will pull the data from the hbase and hive and put the entries and insert into Hbase .
