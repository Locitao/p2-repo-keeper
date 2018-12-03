package com.loci.p2repokeeper.storage

import kotlinx.coroutines.*
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.exception.ZipException
import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Component
class FileStorageImpl : FileStorage {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun storeZip(zipFile: MultipartFile, directory: String) {
        if (!zipFile.originalFilename!!.endsWith(".zip", true)) {
            return //TODO: throw some error?
        }

        ioScope.launch { handleZip(zipFile, directory) }
    }

    /**
     * Stores the given [MultipartFile] (zip file) in the given [directory] by first unzipping
     * the zip file then saving the contents.
     */
    private suspend fun handleZip(zipFile: MultipartFile, directory: String) {
        val zip = getTempFileFromMultipartFile(zipFile)

        try {
            val actualZip = ZipFile(zip)
            actualZip.extractAll(directory)
        } catch (zipException : ZipException) {
            zipException.printStackTrace() //TODO: log?
        } finally {
            zip.delete() //remove temp file
            return
        }
    }

    private fun getTempFileFromMultipartFile(zipFile: MultipartFile): File {
        val zip : File = File.createTempFile(UUID.randomUUID().toString(), "temp")
        val fio = FileOutputStream(zip)
        IOUtils.copy(zipFile.inputStream, fio)
        fio.close()

        return zip
    }
}