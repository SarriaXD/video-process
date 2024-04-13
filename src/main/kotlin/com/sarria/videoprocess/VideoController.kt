package com.sarria.videoprocess

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import kotlin.time.measureTime

@RestController
@RequestMapping("/video")
class VideoController(private val videoConversionService: VideoConversionService) {

    @PostMapping("/convert")
    fun convertVideo(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val time = measureTime {
            val tempFile = Files.createTempFile(null, ".mp4").toFile()
            file.transferTo(tempFile)
            // change to your output directory
            val outputDir =
                "/Users/sarria/WebServerProjects/video-process/video-output/${tempFile.nameWithoutExtension}"
            if (!File(outputDir).exists()) File(outputDir).mkdirs()
            videoConversionService.convertToHls(tempFile.absolutePath, outputDir)
        }
        println("Time Consumed: $time")
        return ResponseEntity.ok("Video is being processed.")
    }
}