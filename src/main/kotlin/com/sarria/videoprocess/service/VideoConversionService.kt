package com.sarria.videoprocess.service

import com.github.kokorin.jaffree.ffmpeg.FFmpeg
import com.github.kokorin.jaffree.ffmpeg.UrlInput.fromPath
import com.github.kokorin.jaffree.ffmpeg.UrlOutput
import org.springframework.stereotype.Service
import kotlin.io.path.Path

@Service
class VideoConversionService {
    val resolutions = arrayOf("240x360", "480x720", "1080x2048")

    fun convertToHls(inputFilePath: String, outputDir: String) {
        resolutions.forEach { resolution ->
            val outputPath = "$outputDir/${resolution}_output.m3u8"
            FFmpeg.atPath()
                .addInput(fromPath(Path(inputFilePath)))
                .addOutput(
                    UrlOutput.toUrl(outputPath)
                        .addArguments("-profile:v", "baseline")
                        .addArguments("-level", "3.0")
                        .addArguments("-s", resolution)
                        .addArguments("-start_number", "0")
                        .addArguments("-hls_time", "10")
                        .addArguments("-hls_list_size", "0")
                        .setFormat("hls")
                )
                .execute()
            // use native command
//            val command =
//                "ffmpeg -i $inputFilePath -profile:v baseline -level 3.0 -s $resolution -start_number 0 -hls_time 10 -hls_list_size 0 -f hls $outputDir/${resolution}_output.m3u8"
//            Runtime.getRuntime().exec(command).apply {
//                waitFor()
//                BufferedReader(InputStreamReader(inputStream)).forEachLine { println(it) }
//            }
        }
    }

}