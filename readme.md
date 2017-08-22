# Hadoop part 3
## First MapReduce program

### 2 variants:
* Program output is text file
* Program output is compressed using snappy
Second way you can see now, first variant is in commit history

Command, to create uber jar:
```sh
$ sbt clean compile assembly
```

Command to run jar:
```sh
/usr/local/hadoop/hadoop-2.8.1$bin/hadoop jar IdeaProjects/hadoop_p3/target/scala-2.12/hadoop_p3-assembly.jar /user/ilia/task3/input /user/ilia/task3/output
```

To check cluster and app stats look useful links from Hadoop part 2


### Configuration files
```mapred-site.xml```
```xml
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
    <property>
        <name>mapreduce.job.tracker</name>
        <value>localhost:54311</value>
    </property>
    <property>
        <name>mapreduce.job.tracker.reserved.physicalmemory.mb</name>
        <value></value>
    </property>
    <property>
        <name>mapreduce.map.memory.mb</name>
        <value>1024</value>
    </property>
    <property>
        <name>mapreduce.reduce.memory.mb</name>
        <value>1024</value>
    </property>
    <property>
        <name>yarn.app.mapreduce.am.resource.mb</name>
        <value>1024</value>
    </property>
    <property>
        <name>yarn.app.mapreduce.am.command-opts</name>
        <value>-Xmx983m</value>
    </property>
    <property>
        <name>mapreduce.map.java.opts</name>
        <value>-Xmx983m</value>
    </property>
    <property>
        <name>mapreduce.reduce.java.opts</name>
        <value>-Xmx983m</value>
    </property>
    <property>
        <name>mapreduce.map.output.compress</name>
        <value>true</value>
    </property>
    <property>
        <name>mapred.map.output.compress.codec</name>
        <value>org.apache.hadoop.io.compress.SnappyCodec</value>
    </property>
</configuration>
```
```yarn-site.xml```
```xml
<configuration>
    <property>
        <name>yarn.resourcemanager.address</name>
        <value>127.0.0.1:8032</value>
    </property>
    <property>
        <name>yarn.resourcemanager.scheduler.address</name>
        <value>127.0.0.1:8030</value>
    </property>
    <property>
        <name>yarn.resourcemanager.resource-tracker.address</name>
        <value>127.0.0.1:8031</value>
        </property>
    <property>
        <name>yarn.scheduler.minimum-allocation-mb</name>
        <value>1024</value>
    </property>
    <property>
        <name>yarn.scheduler.maximum-allocation-mb</name>
        <value>8192</value>
    </property>
    <property>
        <name>yarn.nodemanager.resource.memory-mb</name>
        <value>8192</value>
    </property>
    <property>
        <name>yarn.nodemanager.vmem-check-enabled</name>
        <value>false</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
        <value>org.apache.hadoop.mapred.ShuffleHandler</value>
    </property>
</configuration>
```