import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val list = listOf("asd", "sdg", "sdfgh", "1", "@")
    runBlocking{
        for(s in list) {
            launch {
                println("Start $s")
                delay(2000)
                println("End $s")
            }
        }
    }
}