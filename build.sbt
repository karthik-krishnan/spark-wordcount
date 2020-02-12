name := "Spark-WordCount"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "bintray-spark-jobserver-maven" at "https://dl.bintray.com/spark-jobserver/maven"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.0"
libraryDependencies += "spark.jobserver" % "job-server-api_2.11" % "0.8.0"
