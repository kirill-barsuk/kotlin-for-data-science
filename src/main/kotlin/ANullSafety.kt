fun main() {
    process(null)
}

fun process(s: String?) {
    // ?. - elvis operator
    // ?: - runs only when the object is null
    // !! - cast to non-null type
    println(s?.length ?: throw Exception("Empty string!"))
}
