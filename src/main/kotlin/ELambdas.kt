fun main() {
    val list = listOf("asd", "12", "jdg^%#3", "c8")
    list.filter{ it.length > 2}.map { "($it)" }.forEach { println(it) }
}