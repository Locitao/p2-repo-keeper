package com.loci.p2repokeeper.storage

import org.springframework.web.multipart.MultipartFile

interface FileStorage {

    /**
     * Stores the zip file in the directory passed.
     */
    fun storeZip(zipFile : MultipartFile, directory : String)
}