fun main() {
    println(concat("e", "r", "t"))
    println(concat(s3 = "t"))
    println(concat())
}
fun concat(s1: String = "a", s2: String = "b", s3: String = "c"):String {
    return s1 + s2 + s3
}