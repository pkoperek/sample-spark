import java.io.{ByteArrayOutputStream, File, PrintStream}
import java.nio.file.{Files, Paths}

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MainTest extends FunSuite with ShouldMatchers with BeforeAndAfterEach {

  private val byteArrayOutputStream = new ByteArrayOutputStream()
  private val printStream = new PrintStream(byteArrayOutputStream)
  private var defaultOutputStream: PrintStream = null

  override protected def beforeEach(): Unit = {
    byteArrayOutputStream.reset()
    defaultOutputStream = System.out
    System.setOut(printStream)
  }

  override protected def afterEach(): Unit = {
    System.setOut(defaultOutputStream)
  }

  test("exits when no arguments passed") {

    // When
    new Main().main(Array())

    // Then
    standardOutput should startWith ("Please specify master and file urls!")
  }

  test("reads local empty file through local spark cluster") {

    // Given
    val inputFile = temporaryFile()
    fill(inputFile, "")

    // When
    wordFrequency(inputFile.getAbsolutePath)

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
    wordFrequency(inputFile.getAbsolutePath)

    // Then
    standardOutput should include ("ipsum -> 0.5\nlorem -> 0.5")
  }

  def standardOutput: String = {
    byteArrayOutputStream.toString
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
