object Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        for (a in nums.indices) {
            for (b in a + 1 until nums.size) {
                if(nums[a] + nums[b] == target) return intArrayOf(a, b)
            }
        }
        return intArrayOf()
    }
}

fun main() {
    println(Solution.twoSum(intArrayOf(3, 2, 4), 6).contentToString())
}