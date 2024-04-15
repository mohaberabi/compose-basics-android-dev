package com.example.bluromatic.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.OUTPUT_PATH
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

private const val TAG = "CleanupWorker"

class CleanupWorker(
    private val context: Context, private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        makeStatusNotification(
            applicationContext.resources.getString(R.string.cleaning_up_files),
            applicationContext
        )


        return withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)
            return@withContext try {
                val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
                if (outputDir.exists()) {
                    val enteries = outputDir.listFiles()
                    if (enteries != null) {
                        for (entery in enteries) {
                            val name = entery.name
                            if (name.isNotEmpty() && name.endsWith("png")) {
                                val deleted = entery.delete()
                                Log.i(TAG, "DELETED ${name} ${deleted}")

                            }
                        }
                    }
                }
                Result.success()

            } catch (throwable: Throwable) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_cleaning_file),
                    throwable
                )
                Result.failure()

            }
        }
    }
}
