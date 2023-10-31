package core.utils

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import core.log.Timber
import org.jetbrains.skia.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.imageio.ImageIO

object BarcodeManager {
    suspend fun generateQrCodeBitmap(pairText: String): Image? = try {
        Timber.d("generateQrCode()")
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix = multiFormatWriter.encode(pairText, BarcodeFormat.QR_CODE, 300, 300)
        createQrCode(bitMatrix)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    @Throws(WriterException::class, IOException::class)
    fun createQrCode(matrix: BitMatrix): Image {
        val bufferedImage: BufferedImage = MatrixToImageWriter.toBufferedImage(matrix)
        val baos = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "bmp", baos)
        baos.flush()
        val bytes = baos.toByteArray()
        baos.close()
        return Image.makeFromEncoded(bytes)
    }
}