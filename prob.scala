import scala.io.StdIn
object prob {
  def main(args: Array[String]): Unit = {
    val inputValues = new InputValues()
    val printValues = new PrintValues()

    var continue = true
    while (continue) {
      println("\nМеню:")
      println("1. Добавить Предмет")
      println("2. Добавить Группу")
      println("3. Добавить Студента")
      println("4. Добавить Оценку Студента по предмету")
      println("5. Добавить Оценку за курсовую работу по предмету")
      println("6. Вывод информации на экран")
      println("7. Выход")

      val choice = StdIn.readInt()
      //StdIn.readLine() // Считываем символ новой строки
      choice match {
        case 1 => inputValues.addSubject()
        case 2 => inputValues.addGroup()
        case 3 => inputValues.readStudent()
        case 4 => inputValues.addMark()
        case 5 => inputValues.addMarkCoursework()
        case 6 => printValues.printInfo(inputValues.students, inputValues.subjects)
        case 7 => continue = false
        case _ => println("Неверный выбор. Повторите ввод.")
      }
    }
  }
}
