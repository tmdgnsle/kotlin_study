// 1번
val A : Int = 10
val B : Int = 10
val C : Boolean = if(A==B) true else false
println(C)
// 자료형이 다르면 비교연산자를 사용할 수 없다

// 2번
val AA:Int = 10
val BB: Int = AA * 2

// 3번
fun checkGrade(score: Int): String{
    when(score){
        in 90..100 -> return "A학점"
        in 80..89 -> return "B학점"
        in 70..79 -> return "C학점"
        else -> return "F학점"
    }
}

// 4번
fun gradeTest(correctCount:Int): Int{
    return correctCount * 5
}

// 5번
fun plusTwoNumbers(firstNum: Int?, secondNum: Int?): Int{
    val first: Int = if(firstNum == null) 0 else firstNum
    val second: Int = if(secondNum == null) 0 else secondNum
    return first + second
}