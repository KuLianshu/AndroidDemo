package com.example.flowpractice.download

import java.io.File

/**
 * 下载状态
 */
sealed class DownloadStatus {

    object None: DownloadStatus()
    data class Progress(val values: Int): DownloadStatus()
    data class Error(val throwable: Throwable): DownloadStatus()
    data class Done(val file: File): DownloadStatus()

}


