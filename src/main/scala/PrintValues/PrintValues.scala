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
      // Форматирование среднего балла с двумя знаками после запятой
      val averageMarkFormatted = "%.1f".format(student.averageMark).replace('.', ',')
      println(f"${student.name}%20s\t\t\t\t$averageMarkFormatted")
    }

    // Вывод отличников
    println("\nОтличники:")
    val excellentStudents = students.filter(student =>
      student.averageMark == 5.0 && student.marks.values.forall(_ != "не зачет")
    )
    excellentStudents.foreach { student =>
      val averageMarkFormatted = "%.1f".format(student.averageMark).replace('.', ',')
      println(s"${student.name}, Группа: ${student.groupName}, Средний балл: ${averageMarkFormatted}")
    }

    // Вывод претендентов на отчисление
    println("\nПретенденты на отчисление:")
    val underThree = students.filter(_.averageMark < 3.0)
    val withFailures = students.filter(_.marks.values.exists(_ == "не зачет"))
    val candidatesForExpulsion = (underThree ++ withFailures).distinct // Избегаем дубликатов
    candidatesForExpulsion.foreach { student =>
      val averageMarkFormatted = "%.1f".format(student.averageMark).replace('.', ',')
      val reason =
        if (student.averageMark < 3.0 && student.marks.values.exists(_ == "не зачет"))
          "низкий средний балл, не зачет по предмету"
        else if (student.averageMark < 3.0)
          "низкий средний балл"
        else
          "не зачет по предмету"
      println(s"${student.name}, Группа: ${student.groupName}, Средний балл: ${averageMarkFormatted} ($reason)")
    }
  }
}

