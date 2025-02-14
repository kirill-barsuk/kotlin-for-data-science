fun main() {
    println(getLength("asd"))
    println(getLength("(asd)"))
}

private fun getLength(s: String): Int {
    // if is an expression
    return if (s.startsWith("(") && s.endsWith(")")) s.length - 2 else s.length
}