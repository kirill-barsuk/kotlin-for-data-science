package smile

import smile.classification.RandomForest
import smile.data.formula.Formula
import smile.io.Read
import smile.validation.CrossValidation


fun main() {
    val iris = Read.arff("frameworks\\frameworks\\src\\main\\resources\\iris.arff")
    println(iris)

    val f = Formula.lhs("class")
    //val rf = RandomForest.fit(formula, iris)
    val res = CrossValidation.classification(
        10, f, iris,
        { formula, data -> RandomForest.fit(formula, data) })
    println(res)
}