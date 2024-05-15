// Класс Студент
class Student(val name: String, val groupName: String) {
  private var _marks = Map[Subject, Any]()
  private var _courseworkMarks = Map[Subject, Int]()

  def addMark(subject: Subject, mark: Any): Unit = {
    _marks += (subject -> mark)
  }

  def addCourseworkMark(subject: Subject, mark: Int): Unit = {
    _courseworkMarks += (subject -> mark)
  }

  def marks: Map[Subject, Any] = _marks
  def courseworkMarks: Map[Subject, Int] = _courseworkMarks

  def averageMark: Double = {
    val examMarks = _marks.values.collect { case mark: Int => mark }
    val examCount = examMarks.size
    val examTotal = examMarks.sum

    val courseworkExamMarks = _courseworkMarks.values.toList ::: examMarks.toList
    val totalMarks = courseworkExamMarks.size
    if (totalMarks > 0) {
      val total = courseworkExamMarks.sum
      total.toDouble / totalMarks
    } else {
      0.0
    }
  }
}