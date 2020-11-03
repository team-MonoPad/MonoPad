package com.project.monopad.util

import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

object DownloadUtil {
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