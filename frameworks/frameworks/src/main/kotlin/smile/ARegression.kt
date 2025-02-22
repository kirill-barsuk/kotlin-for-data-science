package smile

import org.apache.commons.csv.CSVFormat
import smile.io.*
import smile.data.formula.Formula
import smile.data.measure.NominalScale
import smile.data.type.DateTimeType
import smile.regression.*
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.*
import java.util.*



fun main() {
    val csvFormat = CSVFormat.DEFAULT.builder().setDelimiter('|').setHeader().build()
    val csv = CSV(csvFormat)
    val trainPath =
        "frameworks\\frameworks\\out\\slurm_mod_train.csv"
    val structType = csv.inferSchema(Input.reader(trainPath, StandardCharsets.UTF_8), 20000)
    val train = Read.csv(trainPath, csvFormat, structType)
    val testPath =
        "frameworks\\frameworks\\out\\slurm_mod_test.csv"
    val test = Read.csv(testPath, csvFormat, structType)

    val elapsedRaw = train.column("ElapsedRaw")
    val formula = Formula.lhs("ElapsedRaw")
    val prop = Properties()
    prop.setProperty("smile.random.forest.trees", "200")

    val forest = RandomForest.fit(formula, train, prop)
    println(forest.metrics())
}