package core.utils

import core.log.Timber

object FileManager {

    fun createFile(path: String) {
        Timber.d("createFile()")

    }

    fun fetchFile(directoryPath: String, filename: String) {
        Timber.d("fetchFile() | directoryPath: $directoryPath, filename: $filename")

    }

    fun fetchFile(filename: String) {
        Timber.d("fetchFile() | filename: $filename")

    }

}