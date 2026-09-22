package dataframe

import org.apache.commons.csv.CSVFormat
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readCSV
import org.jetbrains.kotlinx.dataframe.io.writeCSV
import org.jetbrains.kotlinx.dataframe.size

fun main() {
    var df = DataFrame.readCSV("frameworks\\frameworks\\src\\main\\resources\\slurm_jobinfo_acct.csv", '|')
//    df.print()

//    println(df.columnsCount())
//    println(df.rowsCount())
//    println(df.columns().map { it.name() })
//    println(df.get(0))

//    println(df["State"].distinct().toList())
//    println(df.filter { it["State"] == "COMPLETED" }.size())

    df = df.filter { it["State"] == "COMPLETED" }
    println(df.rowsCount())
    df = df.filter { it["ElapsedRaw"] != null }
    df = df.filter { it["TimelimitRaw"] != null }
    println(df.rowsCount())

    df = df.add("UtilFactor"){it["ElapsedRaw"].toString().toFloat() / it["TimelimitRaw"].toString().toFloat()}
    val groupByUser = df.groupBy("UserID").mean("UtilFactor")
    groupByUser.print()
    df = df.remove("UtilFactor")
    df = df.join(groupByUser, JoinType.Left)
//
    val csvFormat = CSVFormat.DEFAULT.builder().setDelimiter('|').build()
    df.writeCSV("frameworks\\frameworks\\out\\slurm_mod.csv", csvFormat)

    df = df.remove("Start", "Sumbit", "Eligible", "End")
    val split = 0.8
    val size = df.rowsCount()
    println(size)
    val train = df[0..(size * split).toInt()]
    val test = df[(size * split).toInt() + 1..<df.rowsCount()]
    println(train.rowsCount())
    println(test.rowsCount())
    train.writeCSV("frameworks\\frameworks\\out\\slurm_mod_train.csv", csvFormat)
    test.writeCSV("frameworks\\frameworks\\out\\slurm_mod_test.csv", csvFormat)
}