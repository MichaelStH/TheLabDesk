package core.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import core.log.Timber
import data.remote.dto.Config
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import utils.Constants
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.channels.FileLock
import java.nio.channels.OverlappingFileLockException

object FileManager {

    private var isOperationRunning: Boolean = false

    val APP_FOLDER_PATH = "${SystemManager.getUserDirectory()}TheLabDesk"
    const val CONFIG_FILE_NAME = "config.json"

    val getConfigFilePath: () -> String = { APP_FOLDER_PATH + File.separator + CONFIG_FILE_NAME }

    /*
     * Source https://www.digitalocean.com/community/tutorials/java-create-new-file
     */
    fun createFile(path: String): Boolean {
        Timber.d("createFile()")
        return false
    }

    /*
     * Source https://www.digitalocean.com/community/tutorials/java-create-new-file
     */
    fun createFile(directoryPath: String, filename: String): Boolean {
        Timber.d("createFile()")

        val appFolder = File(directoryPath)
        if (!appFolder.exists()) {
            appFolder.mkdirs()
        }

        val configFile = File(appFolder.absolutePath + File.separator + filename)

        // If file already exists returns true
        if (configFile.exists()) {
            Timber.d("file already exists returns true")
            return true
        }

        val created = runCatching {
            if (configFile.createNewFile()) {
                Timber.d("Config file with path: ${configFile.absolutePath} created")
            } else {
                Timber.e("Config file with path: ${configFile.absolutePath} already exists")
            }

            writeToFile(configFile)

            true
        }
            .onFailure {
                Timber.e("createFile | runCatching | onFailure: ${it.message}")
            }
            .onSuccess {
                Timber.d("createFile | runCatching | onSuccess")
            }
            .getOrElse {
                Timber.e("createFile | runCatching | failure: ${it.message}")
                false
            }
        // Returns if file successfully created or not
        return created
    }

    fun getFileByDirectoryAndName(directoryPath: String, filename: String) {
        Timber.d("fetchFile() | directoryPath: $directoryPath, filename: $filename")

    }

    @Throws(IOException::class)
    fun getFileByName(filename: String): File {
        Timber.d("fetchFile() | filename: $filename")
        return File(APP_FOLDER_PATH, filename)
    }

    fun createConfigFile() {
        createFile(APP_FOLDER_PATH, CONFIG_FILE_NAME)
    }


    fun writeToFile(file: File) {
        Timber.d("writeToFile()")

        if (file.readBytes().toString().trim().isEmpty()) {
            Timber.d("Write to file...")
            val bytes = "{}".toByteArray(charset("UTF-8"))
            file.writeBytes(bytes)
        }
    }

    fun writeToFileWithRandomAccessFileAndFileLock(file: File) {
        Timber.d("writeToFileWithRandomAccessFileAndFileLock()")

        // Write to file
        val stream = RandomAccessFile(file, "rw")
        val channel: FileChannel = stream.channel

        var lock: FileLock? = null
        try {
            lock = channel.tryLock()
        } catch (e: OverlappingFileLockException) {
            stream.close()
            channel.close()
        }

        if (null == stream.readLine()) {
            stream.writeChars("{}".trim())
        }
        lock!!.release()

        stream.close()
        channel.close()
    }

    /**
     * Fetch config file from user home directory
     * @return [Config] object
     */
    fun getConfigFileToDto(): Config? = runCatching {
        Timber.d("getConfigFileToDto()")

        val jsonDecodedConfig = File(getConfigFilePath())
            .run {
                isOperationRunning = true
                this.readBytes().toString(Charsets.UTF_8)
            }

        val config: Config = Json { ignoreUnknownKeys = true }.decodeFromString(jsonDecodedConfig)
        config
    }
        .onFailure {
            Timber.e("updateConfigFile | runCatching | onFailure: ${it.message}")
            isOperationRunning = false
        }
        .onSuccess {
            Timber.d("updateConfigFile | runCatching | onSuccess")
            isOperationRunning = false
        }
        .getOrNull()


    fun updateConfigFile(vararg map: Pair<String, Any>) {
        Timber.d("updateConfigFile()")
        Timber.d("check operation running: $isOperationRunning")

        if(isOperationRunning) {
            Timber.e("Operation is already running")
            return
        }

        var json: Json? = null
        // Dark mode content
        val key: String = map.first { it.first == Constants.IS_DARK_MODE }.first //whatever
        val value = map.first { it.first == Constants.IS_DARK_MODE }.second

        runCatching {
            File(getConfigFilePath())
                .apply {
                    isOperationRunning = true

                    this
                        .readBytes().toString(Charsets.UTF_8)
                        .apply {
                            Timber.d("apply() | json content: $this")
                        }
                        .run {
                            if (Constants.EMPTY_JSON_BLOCK.trim() == this.trim()) {
                                Timber.e("JSON file contains an empty block")
                            } else {
                                Timber.e("JSON file already contains some values")
                            }

                            val config = Config(value as Boolean)

                            json = Json { ignoreUnknownKeys = true }
                            val jsonString: String? = json?.let {
                                it.run {
                                    this.encodeToString(config)
                                }
                            }

                            jsonString?.let {
                                Timber.d("encodeToString: $it")

                                FileWriter(this@apply.absolutePath).use { file ->
                                    file.write(it)
                                    println("Successfully updated json object to file...!!")
                                }
                            } ?: run {
                                Timber.e("json value is null")
                            }
                        }
                }
        }
            .onFailure {
                Timber.e("updateConfigFile | runCatching | onFailure: ${it.message}")
                isOperationRunning = false
            }
            .onSuccess {
                Timber.d("updateConfigFile | runCatching | onSuccess")
                isOperationRunning = false
            }
    }

    fun updateConfigFileWithObjectMapper(vararg map: Pair<String, Any>) {
        Timber.e("updateConfigFileWithObjectMapper()")

        runCatching {
            File(getConfigFilePath()).apply {
                isOperationRunning = true

                val mapper = ObjectMapper()

                val jsonString = this.readBytes().toString(Charsets.UTF_8)
                Timber.d("json content: $jsonString")

                if (Constants.EMPTY_JSON_BLOCK.trim() == jsonString.trim()) {
                    Timber.e("JSON file contains an empty block")

                    // Add content
                    val key: String = map.first { it.first == Constants.IS_DARK_MODE }.first //whatever
                    val value = map.first { it.first == Constants.IS_DARK_MODE }.second

                    val jo = JSONObject("{$key:\"$value\"}")

                    //Read from file
                    val root: JSONObject = mapper.readValue(File(getConfigFilePath()), JSONObject::class.java)

                    val val_newer: String = jo.getString(key)

                    //Update value in object
                    root.put(key, val_newer)
                    FileWriter(this.absolutePath).use { file ->
                        file.write(root.toString())
                        println("Successfully updated json object to file...!!")
                    }
                } else {
                    Timber.e("JSON file already contains some values")

                    // Update content
                    val key: String = map.first { it.first == Constants.IS_DARK_MODE }.first //whatever
                    val value = map.first { it.first == Constants.IS_DARK_MODE }.second

                    val jo = JSONObject("{$key:\"$value\"}")

                    //Read from file
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    val root: Config = mapper.readValue(File(getConfigFilePath()), Config::class.java)

                    Timber.d("config: ${root.toString()}")

                    val val_newer: String = jo.getString(key)
//                    val val_older: String = root.getString(key)
                    val val_older: String = root.isDarkMode.toString()

                    //Compare values
                    if (val_newer != val_older) {
                        //Update value in object
                        //root.put(key, val_newer)
                        //root.isDarkMode = val_newer
                        FileWriter(this.absolutePath).use { file ->
                            file.write(root.toString())
                            println("Successfully updated json object to file...!!")
                        }
                    }
                }

                /*val key: String = map.first { it.first == Constants.IS_DARK_MODE }.first //whatever
                val value = map.first { it.first == Constants.IS_DARK_MODE }.second

                val jo = JSONObject("{$key:\"$value\"}")

                //Read from file
                val root: JSONObject = mapper.readValue(File(getConfigFilePath()), JSONObject::class.java)

                val val_newer: String = jo.getString(key)
                val val_older: String = root.getString(key)

                //Compare values
                if (val_newer != val_older) {
                    //Update value in object
                    root.put(key, val_newer)
                    FileWriter(this.absolutePath).use { file ->
                        file.write(root.toString())
                        println("Successfully updated json object to file...!!")
                    }
                }*/
            }
        }
            .onFailure {
                Timber.e("updateConfigFile | runCatching | onFailure: ${it.message}")
                isOperationRunning = false
            }
            .onSuccess {
                Timber.d("updateConfigFile | runCatching | onSuccess")
                isOperationRunning = false
            }
    }
}