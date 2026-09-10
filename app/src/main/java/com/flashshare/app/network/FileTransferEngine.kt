package com.flashshare.app.network

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.google.android.gms.nearby.connection.Payload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class FileTransferEngine(private val context: Context) {
    companion object {
        private const val CHUNK_SIZE = 65536 // 64KB chunks
    }

    suspend fun readFileAsChunks(filePath: String): List<ByteArray> = withContext(Dispatchers.IO) {
        val chunks = mutableListOf<ByteArray>()
        try {
            val file = File(filePath)
            BufferedInputStream(FileInputStream(file)).use { input ->
                val buffer = ByteArray(CHUNK_SIZE)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    chunks.add(buffer.copyOf(bytesRead))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        chunks
    }

    suspend fun writeChunksToFile(chunks: List<ByteArray>, outputPath: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val outputFile = File(outputPath)
                outputFile.parentFile?.mkdirs()
                BufferedOutputStream(FileOutputStream(outputFile)).use { output ->
                    chunks.forEach { chunk ->
                        output.write(chunk)
                    }
                }
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }

    suspend fun getFileSize(filePath: String): Long = withContext(Dispatchers.IO) {
        try {
            File(filePath).length()
        } catch (e: Exception) {
            0L
        }
    }

    suspend fun verifyFileIntegrity(filePath: String, expectedSize: Long): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val file = File(filePath)
                file.exists() && file.length() == expectedSize
            } catch (e: Exception) {
                false
            }
        }

    fun getFileMimeType(uri: Uri): String {
        return context.contentResolver.getType(uri) ?: "application/octet-stream"
    }

    suspend fun getFileChecksum(filePath: String): String = withContext(Dispatchers.IO) {
        try {
            val messageDigest = java.security.MessageDigest.getInstance("SHA-256")
            val buffer = ByteArray(CHUNK_SIZE)
            BufferedInputStream(FileInputStream(FileInputStream(filePath))).use { input ->
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    messageDigest.update(buffer, 0, bytesRead)
                }
            }
            messageDigest.digest().joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            ""
        }
    }
}
