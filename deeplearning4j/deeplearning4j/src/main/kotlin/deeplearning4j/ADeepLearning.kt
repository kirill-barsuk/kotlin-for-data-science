package deeplearning4j

import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split.FileSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.evaluation.classification.Evaluation
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize
import org.nd4j.linalg.learning.config.Sgd
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

fun main() {
    val log: Logger = LoggerFactory.getLogger(LossFunctions::class.java)
    //First: get the dataset using the record reader. CSVRecordReader handles loading/parsing
    val numLinesToSkip = 0
    val delimiter = ','
    val recordReader = CSVRecordReader(numLinesToSkip, delimiter)
    recordReader.initialize(FileSplit(File("frameworks\\frameworks\\src\\main\\resources\\iris.txt")))

    //Second: the RecordReaderDataSetIterator handles conversion to DataSet objects, ready for use in neural network
    val labelIndex = 4     //5 values in each row of the iris.txt CSV: 4 input features followed by an integer label (class) index. Labels are the 5th value (index 4) in each row
    val numClasses = 3     //3 classes (types of iris flowers) in the iris data set. Classes have integer values 0, 1 or 2
    val batchSize = 150    //Iris data set: 150 examples total. We are loading all of them into one DataSet (not recommended for large data sets)

    val iterator = RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses)
    val allData = iterator.next()
    allData.shuffle()
    val testAndTrain = allData.splitTestAndTrain(0.65)  //Use 65% of data for training

    val trainingData = testAndTrain.getTrain()
    val testData = testAndTrain.getTest()

    //We need to normalize our data. We'll use NormalizeStandardize (which gives us mean 0, unit variance):
    val normalizer = NormalizerStandardize()
    normalizer.fit(trainingData)           //Collect the statistics (mean/stdev) from the training data. This does not modify the input data
    normalizer.transform(trainingData)     //Apply normalization to the training data
    normalizer.transform(testData)         //Apply normalization to the test data. This is using statistics calculated from the *training* set


    val numInputs = 4
    val outputNum = 3
    val seed = 6


    log.info("Build model....")
    val conf = NeuralNetConfiguration.Builder()
        .seed(seed.toLong())
        .activation(Activation.TANH)
        .weightInit(WeightInit.XAVIER)
        .updater(Sgd(0.1))
        .l2(1e-4)
        .list()
        .layer(DenseLayer.Builder().nIn(numInputs).nOut(3)
            .build())
        .layer(DenseLayer.Builder().nIn(3).nOut(3)
            .build())
        .layer(OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
            .activation(Activation.SOFTMAX) //Override the global TANH activation with softmax for this layer
            .nIn(3).nOut(outputNum).build())
        .build()

    //run the model
    val model = MultiLayerNetwork(conf)
    model.init()
    //record score once every 100 iterations
    model.setListeners(ScoreIterationListener(100))

    for(i in 0..<1000) {
        model.fit(trainingData)
    }

    //evaluate the model on the test set
    val eval = Evaluation(3)
    val output = model.output(testData.getFeatures())
    eval.eval(testData.getLabels(), output)
    log.info(eval.stats())
}