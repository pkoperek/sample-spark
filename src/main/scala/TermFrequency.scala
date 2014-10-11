import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

class TermFrequency(input: RDD[String]) extends Serializable {

  def asMap(): Map[String, Int] = {
    val wordFrequencies: Array[String] = input.collect()

    val retVal = mutable.Map[String, Int]()

    for (word <- wordFrequencies) {
      retVal += (word -> 1)
    }

    retVal.toMap
  }


}