fun main() {
    var s = "asd"
    s = "sdf" // reassignment is possible for "var" variables only, not "val
    val list = listOf("asd", "12345", "1")  // immutable list
    val mutableList1 = mutableListOf("asd", "12345", "1")  // mutable list
    val mutableList2 = list.toMutableList()  // mutable list
    mutableList2.add("sdfg")
    mutableList2.forEach{ println(it)}
}