import org.jetbrains.kotlinx.dl.api.core.SavingFormat
import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.activation.Activations
import org.jetbrains.kotlinx.dl.api.core.initializer.Constant
import org.jetbrains.kotlinx.dl.api.core.initializer.GlorotNormal
import org.jetbrains.kotlinx.dl.api.core.layer.core.Dense
import org.jetbrains.kotlinx.dl.api.core.layer.core.Input
import org.jetbrains.kotlinx.dl.api.core.layer.reshaping.Flatten
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.dataset.embedded.mnist
import java.io.File

fun main() {
    // 1. Load the built-in MNIST dataset
    val (train, test) = mnist()

    // 2. Build the model structure using the Sequential API
    val model = Sequential.of(
        Input(28, 28, 1),              // Input layer for 28x28 grayscale images
        Flatten(),
        Dense(
            outputSize = 128,
            activation = Activations.Relu,
            kernelInitializer = GlorotNormal(),
            biasInitializer = Constant(0.1f)
        ),
        Dense(
            outputSize = 10,
            activation = Activations.Linear, // Softmax is typically handled by the CrossEntropy loss function
            kernelInitializer = GlorotNormal(),
            biasInitializer = Constant(0.1f)
        )
    )

    // 3. Compile the model with an optimizer, loss function, and metric
    model.use {
        it.compile(
            optimizer = Adam(),
            loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
            metric = Metrics.ACCURACY
        )

        // 4. Train the model on the training dataset
        println("Starting training...")
        it.fit(dataset = train, epochs = 3, batchSize = 32)

        // 5. Evaluate the model performance on the test dataset
        val accuracy = it.evaluate(dataset = test, batchSize = 32).metrics[Metrics.ACCURACY]
        println("Test Accuracy: $accuracy")

        // 6. Save the trained model weights and architecture
        val modelDirectory = File("model_output")
        it.save(modelDirectory, savingFormat = SavingFormat.JSON_CONFIG_CUSTOM_VARIABLES, writingMode = org.jetbrains.kotlinx.dl.api.core.WritingMode.OVERRIDE)
        println("Model successfully saved to ${modelDirectory.absolutePath}")
    }
}
