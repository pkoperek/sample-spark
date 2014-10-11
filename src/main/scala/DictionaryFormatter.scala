class DictionaryFormatter {

  def format(toFormat: Map[String, Double]): String = {
    val formattedString = new StringBuilder()

    for ((key, value) <- toFormat) {
      formattedString ++= key.toString + " -> " + value.toString + "\n"
    }

    formattedString.toString()
  }

}
