import org.apache.spark.{SparkConf, SparkContext}

object SparkWordCount {
   def main(args: Array[String]) {
      val sc = new SparkContext( new SparkConf().setAppName("Word Count").setMaster("spark://localhost:7077"))
      val input = sc.textFile("data/bible.txt")
      val count = input.flatMap(line ⇒ line.split(" "))
      .map(word ⇒ (word, 1))
      .reduceByKey(_ + _)

      val value = count.filter { case (k, _) => k == "firmament" }.first()._2
      System.out.println("Value = " + value)
      count.saveAsTextFile("output")
      System.out.println("OK");
   }
}
