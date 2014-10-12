import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

class TermFrequency(input: RDD[String]) extends Serializable {

  private val word = "\\w+".r

  def asMap(): Map[String, Double] = {
    val standarizedWords = standarizeWords()
    val wordsCount = standarizedWords.count()

    val wordFrequencies = countWordOccurrences(standarizedWords)

    wordFrequencies.toMap
  }

  private def diviveBy(number: Double): (String, Int) => (String, Double) = {
    (word: String, count: Int) => (word, count / number)
  }

  private def countWordOccurrences(standarizedWords: RDD[String]): Array[(String, Double)] = {
    val wordsCount = standarizedWords.count()

    standarizedWords
      .map(countWord)
      .reduceByKey(add)
      .map(divideBy(wordsCount))
      .collect()
  }

  private def divideBy(number: Double) = {
    wordWithCount: (String, Int) => (wordWithCount._1, wordWithCount._2 / number)
  }

  private def standarizeWords(): RDD[String] = {
    input.flatMap(findWords)
  }

  private def add(x: Int, y: Int): Int = {
    x + y
  }

  private def countWord(word: String): (String, Int) = {
    (word, 1)
  }

  private def findWords(line: String): Array[String] = {
    word.findAllIn(line.toLowerCase).toArray
  }

}
