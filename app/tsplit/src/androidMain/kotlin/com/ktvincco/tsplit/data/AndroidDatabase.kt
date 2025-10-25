package com.ktvincco.tsplit.data

import android.app.Activity
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException


class AndroidDatabase (private val activity: Activity?, private val appName: String): Database {

    companion object {
        const val LOG_TAG = "AndroidDatabase"
    }


    // Program folder path (in the public storage)
    private fun getSoundFileDirectory(): File {
        val dir = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_MUSIC), "${appName.replace(" ", "")}/")
        if (!dir.exists()) { dir.mkdirs() }
        return dir
    }


    override fun getSoundFileDirectoryPath(): String {
        return getSoundFileDirectory().absolutePath
    }


    override fun getAllSoundFilesInThePublicStorage(): List<String> {

        val directory = getSoundFileDirectory()
        var files: List<String> = emptyList();

        if (directory.exists() && directory.isDirectory) {
            files = directory.listFiles { file -> file.isFile }?.map { it.name } ?: emptyList()
        }

        return files
    }


    override fun moveFile(oldFilePath: String, newFilePath: String) {
        try {
            val oldFile = File(oldFilePath)
            val newFile = File(newFilePath)

            if (!oldFile.exists()) {
                throw IOException("File not found at path: $oldFilePath")
            }

            val newParentDir = newFile.parentFile
            if (newParentDir != null && !newParentDir.exists()) {
                newParentDir.mkdirs()
            }

            if (!oldFile.renameTo(newFile)) {
                oldFile.copyTo(newFile, overwrite = true)
                oldFile.delete()
            }
        } catch (e: Exception) {
            throw IOException(
                "Failed to move file from $oldFilePath to $newFilePath: ${e.message}", e)
        }
    }


    override fun deleteFile(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
    }


    private fun getAppPrivateDirectory(): File {
        val dir = File(activity?.filesDir, appName.replace(" ", "").lowercase())
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }


    override fun saveString(key: String, string: String) {
        try {
            val file = File(getAppPrivateDirectory(), "$key.txt")
            file.writeText(string)
            Log.d(LOG_TAG, "Saved string to file '${file.absolutePath}'.")
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Failed to save string to file: ${e.message}")
        }
    }


    override fun loadString(key: String): String? {
        return try {
            val file = File(getAppPrivateDirectory(), "$key.txt")
            if (file.exists()) {
                file.readText()
            } else {
                Log.w(LOG_TAG, "File for key '$key' does not exist.")
                null
            }
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Failed to load string from file: ${e.message}")
            null
        }
    }
}

