fun main() {
    val arr = arrayOf(1, 5, 3, 15, 3, 2, 4, 5)
    var sum = 0
    var i = 0
    while (sum < 25) {
        sum += arr[i++]
    }
    println(sum) // sum of first five elements
}
