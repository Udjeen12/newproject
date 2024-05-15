import scala.io.StdIn

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
