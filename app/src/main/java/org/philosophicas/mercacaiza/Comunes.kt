package org.philosophicas.mercacaiza

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.ByteArrayOutputStream

/** Da tamaño específico, cambia el formato y comprime la imagen */
fun darFormatoImagen(context: Context, uri: Uri, ancho: Int, alto: Int? = null, calidad: Int = 75): Bitmap {

    val stream = context.contentResolver.openInputStream(uri)
    var bm = BitmapFactory.decodeStream(stream)

    val h: Int = bm.height
    val w: Int = bm.width

    //Tomamos el ancho y alto de la imagen desde las especificaciones de la función
    //val escalaAncho: Float = ancho.toFloat() / w.toFloat()
    val escalaAlto = if (alto != null) {
        alto.toFloat() / h.toFloat()
    } else h.toFloat() / w.toFloat()


    bm = Bitmap.createScaledBitmap(bm, ancho, (ancho * escalaAlto).toInt(), false)
    val os = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, calidad, os)
    bm = BitmapFactory.decodeByteArray(os.toByteArray(), 0, os.size())
    return bm

}


fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(source, 0, 0, source.height, source.width,
            matrix, true)
}