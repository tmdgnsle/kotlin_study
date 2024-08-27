// ?
val number: Int = 10 // Non-Nullable Int
val number: Int? = null // Nullable Int

// !!
val nullableNumberList: List<Int?> = listOf<Int?>(1, 2, 3, 4, 5)
var result: Int = 0
nullableNumberList.forEach {
    result += it!!
}
// -> 정말 필요한 경우에만 사용을 해야한다

// ?. (safe call)
val text: String? = "text"
println(text?.length)

// !!.
println(text!!.length)

// as?
open class Warrior1(var name: String, var power: Int, var type: String) {
    fun attack() {
        println("복잡한 코드 + 공격")
    }
}

class DefenseWarrior1 constructor(name: String, power: Int) {
    fun defense() {
        println("방어")
    }
}

val defenseWarrior = DefenseWarrior1("", 100)
val warrior = defenseWarrior as? Warrior1
println(warrior)

// ?: 엘비스 연산
val text2: String? = "123"
val nullText: String? = null

var len1: Int = if (text2 != null) text2.length else 0
var len2: Int = text2?.length ?: 0