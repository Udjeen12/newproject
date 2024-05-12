import scala.io.StdIn

// Класс Предмет
class Subject(val name: String, val examType: String)

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
// Класс Группа
class Group(val name: String) {
  private var _students = List[Student]()

  def readStudent(student: Student): Unit = {
    _students ::= student
  }

  def students: List[Student] = _students
}

class InputValues {
  val subjects = scala.collection.mutable.ListBuffer[Subject]()
  val students = scala.collection.mutable.ListBuffer[Student]()
  val groups = scala.collection.mutable.ListBuffer[Group]()

  def addSubject(): Unit = {
    println("Введите название предмета:")
    val name = StdIn.readLine()
    println("Выберите тип экзамена (1 - экзамен, 2 - зачет с оценкой, 3 - зачет):")
    val examTypeChoice = StdIn.readInt()
    val examType = examTypeChoice match {
      case 1 => "экзамен"
      case 2 => "зачет с оценкой"
      case 3 => "зачет"
      case _ => "неверный тип"
    }
    subjects += new Subject(name, examType)
  }

  def readStudent(): Unit = {
    println("Введите ФИО студента:")
    val name = StdIn.readLine()

    // Выводим список доступных групп с их номерами
    println("Доступные группы:")
    groups.zipWithIndex.foreach { case (group, idx) =>
      println(s"${idx + 1}. ${group.name}")
    }

    println("Выберите номер группы:")
    val groupIdx = StdIn.readInt() - 1
    if (groupIdx >= 0 && groupIdx < groups.length) {
      val groupName = groups(groupIdx).name
      students += new Student(name, groupName)
    } else {
      println("Некорректный номер группы.")
    }
  }

  def addGroup(): Unit = {
    println("Введите название группы:")
    val name = StdIn.readLine()
    groups += new Group(name)
  }

  def addMark(): Unit = {
    println("Выберите студента:")
    students.zipWithIndex.foreach { case (student, idx) =>
      println(s"${idx + 1}. ${student.name}")
    }
    val studentIdx = StdIn.readInt() - 1
    val student = students(studentIdx)

    // Ввод оценок для всех предметов
    subjects.foreach { subject =>
      // Выбор оценки в зависимости от типа экзамена
      subject.examType match {
        case "экзамен" | "зачет с оценкой" =>
          println(s"Введите оценку  по предмету ${subject.name}:")
          val mark = StdIn.readInt()
          student.addMark(subject, mark)
        case "зачет" =>
          println(s"Введите оценку (зачет/не зачет)  по предмету ${subject.name}:")
          val mark = StdIn.readLine()
          if (mark == "зачет" || mark == "не зачет") {
            student.addMark(subject, mark)
          } else {
            println("Некорректная оценка.")
          }
        case _ => println("Неверный тип экзамена.")
      }
    }
  }

  def addMarkCoursework(): Unit = {
    println("Выберите студента:")
    students.zipWithIndex.foreach { case (student, idx) =>
      println(s"${idx + 1}. ${student.name}")
    }
    val studentIdx = StdIn.readInt() - 1
    val student = students(studentIdx)

    // Выбор предмета для ввода оценки за курсовую работу
    println("Выберите предмет:")
    subjects.zipWithIndex.foreach { case (subject, idx) =>
      println(s"${idx + 1}. ${subject.name} (${subject.examType})")
    }
    val subjectIdx = StdIn.readInt() - 1
    val subject = subjects(subjectIdx)

    // Ввод оценки за курсовую работу для предмета
    println(s"Введите оценку за курсовую работу по предмету ${subject.name}:")
    val mark = StdIn.readInt()
    student.addCourseworkMark(subject, mark)
  }
}

class PrintValues {
  def printInfo(students: scala.collection.mutable.ListBuffer[Student], subjects: scala.collection.mutable.ListBuffer[Subject]): Unit = {
    println("\t\t\tПредмет\t\t\tТип экзамена\t\t\tГруппа\t\t\t\tСтудент\t\t\t\tОценка")
    for {
      student <- students
      (subject, mark) <- student.marks
    } {
      println(f"${subject.name}%20s\t\t${subject.examType}%20s\t\t${student.groupName}\t\t${student.name}%20s\t\t$mark")
    }

    // Вывод оценок за курсовую работу
    println("\nКурсовая работа\t\t\tГруппа\t\t\tСтудент\t\t\tОценка")
    for {
      student <- students
      (subject, mark) <- student.courseworkMarks
    } {
      println(f"${subject.name}%10s\t\t\t${student.groupName}%10s\t\t\t${student.name}%10s\t\t$mark")
    }

    println("\nСредние Баллы:")
    students.foreach { student =>
      println(f"${student.name}%20s\t\t\t\t${student.averageMark}%.1f")
    }
  }
}
