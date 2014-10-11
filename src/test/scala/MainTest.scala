import java.io.{ByteArrayOutputStream, File, PrintStream}
import java.nio.file.{Files, Paths}

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MainTest extends FunSuite with ShouldMatchers with BeforeAndAfterEach {

  private val catchStdOut = new ByteArrayOutputStream()

  override def beforeEach() {
    catchStdOut.reset()
  }

  test("exits when no arguments passed") {

    // When
    Console.withOut(catchStdOut) {
      new Main().main(Array())
    }

    // Then
    standardOutput should startWith("Please specify master and file urls!")
  }

  test("exits when one argument passed") {

    // When
    Console.withOut(catchStdOut) {
      new Main().main(Array("local"))
    }

    // Then
    standardOutput should startWith("Please specify master and file urls!")
  }

  test("reads local empty file through local spark cluster") {

    // Given
    val inputFile = temporaryFile()
    fill(inputFile, "")

    // When
    Console.withOut(catchStdOut) {
      wordFrequency(inputFile.getAbsolutePath)
    }

    // Then
    standardOutput shouldEqual ""
  }

  def fill(inputFile: File, inputContent: String) {
    Files.write(Paths.get(inputFile.getAbsolutePath), inputContent.getBytes)
  }

  test("reads local file and counts words") {

    // Given
    val inputFile = temporaryFile()
    fill(inputFile, "Lorem ipsum.")

    // When
    Console.withOut(catchStdOut) {
      wordFrequency(inputFile.getAbsolutePath)
    }

    // Then
    standardOutput should include("ipsum -> 0.5\nlorem -> 0.5")
  }

  def standardOutput: String = {
    catchStdOut.toString
  }

  def temporaryFile(): File = {
    val inputFile = File.createTempFile("tmp", "tmp")
    inputFile.deleteOnExit()
    inputFile
  }

  def wordFrequency(path: String) {
    new Main().main(Array("local", path))
  }
}
