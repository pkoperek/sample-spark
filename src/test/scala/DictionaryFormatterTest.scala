import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DictionaryFormatterTest extends FunSuite with ShouldMatchers {

  test("empty map is an empty string") {

    // When
    val formattedMap = format(Map[String, Double]())

    // Then
    formattedMap shouldEqual ""
  }

  test("map with one entry should be one line") {

    // Given
    val singleWordMap = Map[String, Double](("word" -> 0.5))

    // When
    val formattedMap = format(singleWordMap)

    // Then
    formattedMap should include ("word -> 0.5")
  }

  test("map with two entries should format to two lines") {

    // Given
    val singleWordMap = Map[String, Double](("word" -> 0.5), ("nextword" -> 0.5))

    // When
    val formattedMap = format(singleWordMap)

    // Then
    formattedMap should include ("word -> 0.5\n")
    formattedMap should include ("nextword -> 0.5\n")
  }

  test("line should end with newline") {

    // Given
    val singleWordMap = Map[String, Double](("word" -> 0.5))

    // When
    val formattedMap = format(singleWordMap)

    // Then
    formattedMap should endWith ("\n")
  }

  test("formatter should sort words") {

    // Given
    val singleWordMap = Map[String, Double](("word" -> 0.5), ("aaaa" -> 0.25))

    // When
    val formattedMap = format(singleWordMap)

    // Then
    formattedMap shouldEqual ("aaaa -> 0.25\nword -> 0.5\n")
  }

  def format(emptyMap: Map[String, Double]): String = {
    new DictionaryFormatter().format(emptyMap)
  }
}
