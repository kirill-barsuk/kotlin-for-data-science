import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.impl.preprocessing.image.ColorMode
import org.jetbrains.kotlinx.dl.impl.preprocessing.image.ImageConverter
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val modelConfigPath = File("model_output/modelConfig.json")
    val digitImageFile = File("kotlin-dl/src/main/resources/asd.png")

    val model = Sequential.loadModelConfiguration(modelConfigPath)

    model.use {
        it.compile(
            optimizer = Adam(),
            loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
            metric = Metrics.ACCURACY
        )

        it.loadWeights(File("model_output"))

        // 3. Preprocess the image to fit the 28x28 grayscale model input
        // It reads, resizes, and normalizes the pixel data into a flat array
        val floatArrayImage = ImageConverter.toNormalizedFloatArray(
            resizeToMnistSize(loadRgbImage(digitImageFile)),
            null
        )

        // 4. Run the prediction
        val predictedDigit = it.predict(floatArrayImage)

        println("=========================================")
        println("🎯 Predicted Digit: $predictedDigit")
        println("=========================================")
    }
}

private fun loadRgbImage(file: File): BufferedImage {
    val originalImage = ImageIO.read(file)

    // Check if it has an alpha channel
    if (originalImage.type == BufferedImage.TYPE_INT_ARGB || originalImage.type == BufferedImage.TYPE_4BYTE_ABGR) {
        // Create a new blank image without alpha
        val rgbImage = BufferedImage(
            originalImage.width,
            originalImage.height,
            BufferedImage.TYPE_INT_RGB
        )
        // Copy the graphics over (this strips transparency, replacing it with black)
        val graphics = rgbImage.createGraphics()
        graphics.drawImage(originalImage, 0, 0, null)
        graphics.dispose()

        return rgbImage
    }
    return originalImage
}

fun resizeToMnistSize(originalImage: BufferedImage): BufferedImage {
    // Scale the image smoothly to 28x28
    val scaledImage = originalImage.getScaledInstance(28, 28, Image.SCALE_SMOOTH)

    // Create a new Grayscale BufferedImage
    val grayscaleImage = BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY)

    // Draw the scaled image onto it
    val g2d = grayscaleImage.createGraphics()
    g2d.drawImage(scaledImage, 0, 0, null)
    g2d.dispose()

    return grayscaleImage
}