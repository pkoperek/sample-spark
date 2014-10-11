class DictionaryFormatter {

  def format(toFormat: Map[String, Double]): String = {
    val formattedString = new StringBuilder()

    for ((key, value) <- sorted(toFormat)) {
      formattedString ++= key.toString + " -> " + value.toString + "\n"
    }

    formattedString.toString()
  }

  def sorted(toFormat: Map[String, Double]): List[(String, Double)] = {
    toFormat.toList.sorted
  }
}
