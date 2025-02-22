object IFunctionAsAParameter {
    fun addBrackets(s: String): String {
        return "($s)"
    }

    fun addSquareBrackets(s: String): String {
        return "[$s]"
    }
}

fun main() {
    val s = "jghf"
    process(s, IFunctionAsAParameter::addBrackets)
    process(s, IFunctionAsAParameter::addSquareBrackets)
}

fun process(input: String, unit: (String) -> String) {
    println(unit(input))
}