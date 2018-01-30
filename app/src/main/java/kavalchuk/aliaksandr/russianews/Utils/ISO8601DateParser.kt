package kavalchuk.aliaksandr.russianews.Utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by user on 30.01.2018.
 */

class ISO8601DateParser {

    companion object {
        @Throws(java.text.ParseException::class)
        fun parse(input: String): Date {
            var input = input

            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")

            input = if (input.endsWith("Z")) {
                input.substring(0, input.length - 1) + "GMT-00:00"
            } else {
                val inset = 6

                val s0 = input.substring(0, input.length - inset)
                val s1 = input.substring(input.length - inset, input.length)

                s0 + "GMT" + s1
            }

            return df.parse(input)

        }

        fun toString(date: Date): String {

            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")

            val tz = TimeZone.getTimeZone("UTC")

            df.timeZone = tz

            val output = df.format(date)

            val inset0 = 9
            val inset1 = 6

            val s0 = output.substring(0, output.length - inset0)
            val s1 = output.substring(output.length - inset1, output.length)

            var result = s0 + s1

            result = result.replace("UTC".toRegex(), "+00:00")

            return result

        }
    }
}
