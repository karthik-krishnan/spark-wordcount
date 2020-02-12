import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.{SparkConf, SparkContext}
import spark.jobserver.{NamedRddSupport, SparkJob, SparkJobInvalid, SparkJobValid, SparkJobValidation}

import scala.util.Try

object SparkWordCountJob extends SparkJob with NamedRddSupport{
   def main(args: Array[String]) {
      val conf = new SparkConf().setMaster("local[4]").setAppName("WordCountExample-New")
      val sc = new SparkContext(conf)
      val config = ConfigFactory.parseString("")
      val results = runJob(sc, config)
      println("Result is " + results)
   }

   override def validate(sc: SparkContext, config: Config): SparkJobValidation = {
      Try(config.getString("input.string"))
        .map(x => SparkJobValid)
        .getOrElse(SparkJobInvalid("No input.string config param"))
   }

   override def runJob(sc: SparkContext, config: Config): Any = {
      val input = sc.textFile("/data/bible.txt")
      val wordList = this.namedRdds.get("word-list-with-count").getOrElse(
         input.flatMap(line ⇒ line.split(" "))
           .map(word ⇒ (word, 1))
           .reduceByKey(_ + _)
      )
      this.namedRdds.update("word-list-with-count", wordList)
      val result = wordList.filter { case (k, _) => k == config.getString("input.string") }
      Try(result.first()).map(x => x._2).getOrElse(0)
   }
}