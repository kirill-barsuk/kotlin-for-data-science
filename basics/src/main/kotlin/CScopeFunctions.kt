fun main() {
    val list = listOf("asd", "asdfg", null, "1")
    for (s in list) {
        // called for non-null elements only
        s?.let{
            // "it" is the default variable name for the list element in current iteration
            println(it.length)
        }
    }
}