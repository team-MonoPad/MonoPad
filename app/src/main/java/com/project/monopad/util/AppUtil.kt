package com.project.monopad.util

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

object AppUtil {
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    const val API_KEY  = "84301bd818cef2f63643e7dffa8998ab"
    const val KR_LANGUAGE = "ko-KR"
    const val KR_REGION = "kr"

    val THUMBNAIL_URL = {key: String? -> "https://img.youtube.com/vi/$key/hqdefault.jpg"}

    fun saveImage(
        image: Bitmap,
        storageDir: File,
        imageFileName: String
    ) : String {
        var savedImagePath = ""
        var successDirCreated = false

        if (!storageDir.exists()) {
            successDirCreated = storageDir.mkdir()
        }

        if (successDirCreated) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath =
                try {
                    val fOut: OutputStream = FileOutputStream(imageFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                    fOut.close()
                    imageFile.absolutePath
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
        } else {
            return "already_exist"
        }
        return savedImagePath
    }
}