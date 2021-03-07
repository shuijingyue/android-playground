package play.io

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class MainTest {
    @Test
    fun time() {
        val simpleDataFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val start = simpleDataFormat.parse("20210304000000")
        val end = simpleDataFormat.parse("20210305000000")
        val calendar = Calendar.getInstance()
        calendar.time = start!!
        println(calendar.timeInMillis)
        calendar.time = end!!
        println(calendar.timeInMillis)
    }
}