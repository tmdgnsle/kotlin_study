import 실습.Student as Student

interface Tiger {
    fun bite()
    fun goToBox()
}

class BackDoSanTiger : Tiger {
    override fun bite() {
// 구체적인 기능
    }

    override fun goToBox() {
// 구체적인 기능
    }
}

val backDoSanTiger: BackDoSanTiger = BackDoSanTiger()
backDoSanTiger.bite()
backDoSanTiger.goToBox()


interface Person {
    //  멤버들의 구현부가 없다
    var dress: String
    fun eat()
    fun sleep() {
        println("잠을 잔다")
    }
}

class Student : Person {
    override var dress: String
        get() = "옷"
        set(value) {}

    final override fun eat() {
//         final -> 이 클래스를 상속하는 클래스에서 override를 못하게 한다
        println("밥을 먹는다")
    }

    open fun study() {
        println("공부를 한다")
    }
}

//class GoodStudent : Student() {
//    override fun sleep() {
//        super.sleep()
//    }
//
//    override var dress: String
//        get() = super.dress
//        set(value) {}
//
//
//}

class Teacher : Person {
    override var dress: String
        get() = "정장"
        set(value) {}

    override fun eat() {
        println("밥을 먹는다")
    }

}

val student: Student = Student()
student.eat()
student.sleep()