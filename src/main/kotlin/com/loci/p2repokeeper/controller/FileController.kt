package com.loci.p2repokeeper.controller

import com.loci.p2repokeeper.storage.FileStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class FileController @Autowired constructor(private val fileStorage : FileStorage){

    @PostMapping("/{directory}/upload-zip")
    fun handleFileUpload(
            @RequestParam("file") file : MultipartFile,
            @PathVariable("directory") directory : String
    ): ResponseEntity<String> {
        print("File ${file.originalFilename} received")

        fileStorage.storeZip(file, directory)

        println("Returning response entity")
        return ResponseEntity("Received ${file.originalFilename}", HttpStatus.OK)
    }
}