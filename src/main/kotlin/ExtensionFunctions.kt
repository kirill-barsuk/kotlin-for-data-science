fun String.addBrackets():String {
    // extension function for String class
    return "($this)"
}

fun main() {
    // call the extension function as a normal class function
    println("abc".addBrackets())
}