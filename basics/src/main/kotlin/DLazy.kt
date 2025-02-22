class DLazy {
    val list:List<String> by lazy {
        println("Initialized lazily")
        listOf("qwe", "kfhgs", "1")
    }
}

fun main() {
    val l = DLazy()  // list is not initialized
    println("Instance created")
    val list = l.list  // list is initialized lazily
    list.forEach{println(it)}
}