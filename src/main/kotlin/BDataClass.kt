data class BDataClass(val id: Int, val name: String, val description: String) {
    // id, name, description are properties of the data class
}

fun main() {
    val dc = BDataClass(123, "DataClass", "Пример Data-класса")
    println(dc.description)  // getDescription() is implicitly called
}