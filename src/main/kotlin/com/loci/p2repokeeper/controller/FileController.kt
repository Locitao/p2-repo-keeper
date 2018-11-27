package com.loci.p2repokeeper.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class FileController {

    @PostMapping("/file")
    fun handleFileUpload(@RequestParam("file") file : MultipartFile): ResponseEntity<String> {
        print("File ${file.originalFilename} received")

        return ResponseEntity("Received ${file.originalFilename}", HttpStatus.OK)
    }
}