# Spark Word Count Examples
Spark Word Count Example (Standalone Job as well as Spark Job Server Job)

### Examples
1. SparkWordCount.scala - This is a basic "hello world" for running a simple word count job in Spark.
1. SparkWordCountJob.scala - This is a [Spark Job Server](https://github.com/spark-jobserver/spark-jobserver) Job.

### Quick Steps to demo the job in Spark Job Server

#### 1. Run the Docker Container that comes pre-installed with Spark Job Server and Spark Cluster
*Note that I have mounted the data folder to the container to be referenced by our sample job.*
```
docker run -d -p 8090:8090 -v ./data:/data sparkjobserver/spark-jobserver
```
Check to confirm that Spark Job Server is running by going to http://localhost:8090

#### 2. Build the Package Jar file
*Assuming you have Scala Build Tool(sbt) already installed*
```
sbt package
```

#### 3. Upload the Package Jar to Spark Job Server
```
curl -X POST localhost:8090/binaries/wordcount -H "Content-Type: application/java-archive" --data-binary @target/scala-2.11/spark-wordcount_2.11-1.0.jar
```

#### 4. Create a Spark Context
```
curl -X POST "localhost:8090/contexts/test-context?num-cpu-cores=4&memory-per-node=512m"
```

#### 5. Trigger a Job
```
curl -d "input.string=Amorite" "localhost:8090/jobs?appName=wordcount&classPath=SparkWordCountJob&context=test-context&timeout=30&sync=true"
```
Note : You can remove the sync=true parameter if you want to trigger the job and then query for the job status

#### 6. Query for Job Status
```
curl "localhost:8090/jobs/<jobid>"
```
