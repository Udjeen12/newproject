// Класс Группа
class Group(val name: String) {
  private var _students = List[Student]()

  def readStudent(student: Student): Unit = {
    _students ::= student
  }

  def students: List[Student] = _students
}