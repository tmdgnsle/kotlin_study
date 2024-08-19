// break
for (i in 1..3) { // i반복문
    println("i: " + i)
    for (j in 1..3) { // j반복문
        if (j == 2) break // 구문 1
        else println("j: " + j) // 구문 2
    }
}
println("--------------------")
loop@for (i in 1..3) { // i반복문
    println("i: " + i)
    for (j in 1..3) { // j반복문
        if (j == 2) break@loop // 구문 1
        else println("j: " + j) // 구문 2
    }
}
println("--------------------")
// continue
for (i in 1..3) { // i반복문
    println("i: " + i)
    for (j in 1..3) { // j반복문
        if (j == 2) continue // 구문 1
        else println("j: " + j) // 구문 2
    }
}
loop@for (i in 1..3) { // i반복문
    println("i: " + i)
    for (j in 1..3) { // j반복문
        if (j == 2) continue@loop // 구문 1
        else println("j: " + j) // 구문 2
    }
}

//return
fun returnFunction(): Unit{
    for (i in 1..3) { // i반복문
        println("i: " + i)
        for (j in 1..3) { // j반복문
            if (j == 2) return // 구문 1
            else println("j: " + j) // 구문 2
        }
    }
}
returnFunction()