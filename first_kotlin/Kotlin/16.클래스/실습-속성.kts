// getter/setter
class Book() {
    var title: String = "모름"
        get() {
//            println("getter")
            return field
        }
        set(value) {
//            println("setter")
            field = value
        }

}

//val book = Book()
////println(book.title)
//println(book.title)
//book.title = "제목 변경"
//println(book.title)

//lateinit
class MaterBook() {
    lateinit var title: String

    fun nextPage(){
        if(::title.isInitialized){
            println("페이지가 넘어간다")
        } else{
            println("초기화 필요")
        }
    }
}
val book = MaterBook() // MasterBook -> 거푸집 book -> 개체, 찍어낸거
book.title = "책이름"
println(book.nextPage())

val book2 = MaterBook() // MasterBook -> 거푸집 book2 -> 개체, 찍어낸거
println(book2.nextPage())

// Lazy
// - 호출시점에 by lazy 정의에 의해서 초기화 수행한다
// - val 에서만 사용이 가능하다
class Book2(){
    val title : String by lazy {
        //본문 -> 다른 작업도 할 수 있지만 반드시 프로퍼티를 초기화 시켜주는 작업을 해야한다
        println("lazy 초기화") // 다른
        // 엄청 복잡한 작업이 있다
//        title = "책 제목" -> X
//        return "책 제목" -> X
        "책 제목"
    }
}
val book3 = Book2()
println(book3.title)