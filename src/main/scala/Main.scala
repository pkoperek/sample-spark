import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

object Main extends Serializable {

  def main(args: Array[String]) = {

    if (args.length < 2) {
      println("Please specify master and file urls!")
    } else {
      val masterUrl = args(0)
      val inputFileUrl = args(1)

      var sparkContext: SparkContext = null
      try {
        sparkContext = createSparkContext(masterUrl)

        val wordsFrequencies = termFrequencies(sparkContext.textFile(inputFileUrl))

        print(format(wordsFrequencies))
      }
      finally {
        if (sparkContext != null) {
          sparkContext.stop()
        }
      }
    }
  }

  private def format(wordsFrequencies: Map[String, Double]): String = {
    new DictionaryFormatter().format(wordsFrequencies)
  }

  private def termFrequencies(input: RDD[String]): Map[String, Double] = {
    new TermFrequency(input).asMap()
  }

  private def createSparkContext(masterUrl: String) = {
    val sparkConf = new SparkConf().setMaster(masterUrl).setAppName("TermFrequency")
    new SparkContext(sparkConf)
  }

}
