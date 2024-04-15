package com.example.bluromatic.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_IMAGE_URI
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Date

private const val TAG = "saveFileWoker"

class SaveImageToFileWorker(

    private val context: Context, private val param: WorkerParameters
) : CoroutineWorker(context, param) {
    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )

    override suspend fun doWork(): Result {
        makeStatusNotification(
            applicationContext.resources.getString(R.string.saving_image),
            applicationContext
        )


        return withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)
            val reseolver = applicationContext.contentResolver

            return@withContext try {
                val uri = inputData.getString(KEY_IMAGE_URI)
                val bitmap = BitmapFactory.decodeStream(reseolver.openInputStream(Uri.parse(uri)))

                val imgUrl = MediaStore.Images.Media.insertImage(
                    reseolver,
                    bitmap,
                    title,
                    dateFormatter.format(Date())
                )

                if (!imgUrl.isNullOrEmpty()) {
                    val output = workDataOf(KEY_IMAGE_URI to uri)

                    Result.success(output)
                } else {
                    Log.e(
                        TAG,
                        applicationContext.resources.getString(R.string.writing_to_mediaStore_failed)
                    )
                    Result.failure()
                }


            } catch (e: Exception) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.writing_to_mediaStore_failed)
                )
                Result.failure()

            }
        }
    }
}